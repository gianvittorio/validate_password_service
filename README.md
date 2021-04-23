# Password Validation Service

## Assumptions
- Data encryption, both in transit, or at rest is/will be taken care of within application (web security filter chain) or web layer (SSL termination within load balancers and reverse proxies)
- Microservices patterns such as circuit break (liveness/readiness), distributed tracing, configuration service, edge server, observability, authorization service are left out of the equation - we still have to discuss wether the microservices architecture will be more beneficial over the monolith   
- The overall userbase, considering an online banking application is quite large ~ 50 million users
- People do not change password quite often (perhaps a handful of times per year), however, there could be a spike of new users on daily basis. Let us be conservative and assume every the whole userbase will be issued a new password once a month - 50m requests per month * (1 / 2.5m request per month)  ~ 20 requests/second (1 month = 2.5m seconds)
- The overall latency shouldn't be greater than a few milliseconds ~ 10ms. Network latency will be neglected, since we still have to evaluate wether we want to take the single service per host approach or a monolith
- The validation service will be an API, as a part of a larger landscape, providing for other services within it. Ex: user details service for account creation/update operations
- Static code analysis, code coverage, profiling and benchmarking will only be taken care of later on, along with the very CI/CD pipeline
- The following service will be spinned as a standalone docker container, on a local machine

## Data Model
From the functional requirements, we can assume the password validation service not to require any persistence data store, if not only a memory cache for further improving read performance, which we will discuss later on. The data model is limited to a standalone entity, which we are going to refer to as <strong>Result</strong>, containing two fields: <strong>password</strong> (String) and <strong>isValid</strong> (Boolean)
```json
{ "password": "AbTp9!fok", "isValid": true }
```

## The API
As a part of a much larger and more complex landscape of services, such as: account/balance, transfers, credit analysis, loans, anti-fraud, and so on, we take a smaller/simpler snapshot of it (Obs: all the services stand behind a set of load balancers, but they have been omitted to avoiding clutter):

![Microservices Landscape](https://lucid.app/publicSegments/view/4f0e8810-4b34-440b-9596-c1344379d612/image.png)

The Password Validation Service, would essentially serve to the Write API, involving create/update operation to the user account database, to checking wether the user has come up with a suitable password. The API will expose the <strong>api/v1/validate</strong> endpoint for <strong>POST</strong> requests. The request body will only provide the <strong>password</strong> (String) field, in <strong>json format</strong>:
```json
{ "password": "AbTp9!fok"}
```
The response, also in json format, will be pretty much alike the domain data model, returning <strong>200 OK</strong>:
```json
{ "password": "AbTp9!fok", "isValid": true }
```
If the request body happens to either have a null/blank password field, the response status code will be <strong>400 BAD REQUEST</strong> and a field <strong>errorMessage</strong> will be present, instead of <strong>isValid</strong>.
For null:
```json
{ "password": "", "errorMessage": "Password is null" }
```
and blank:
```json
{ "password": "", "errorMessage": "Password is malformed" }
```

## The Hexagonal Architecture

We are going to structure our project following through the <strong>Hexagonal Architecture</strong>, on a bottom-up approach:
- The domain/entity, along with the very core business logic, wrapped up as a library, will be POJOS, totally independent from any web framework we might use
- The service will be a mere implementation of our use cases, which will instance and share the entity, according to our underlying business logc
- The API itself, will then be responsible for handling the requests/responses and delegate the computation to the service underneath 

![Hexagonal Architecture](https://lucid.app/publicSegments/view/98f07e52-1840-45b8-b1bc-29d4a19fa4bd/image.png)
We can benefit from the above design as it decouples our system into smaller, simpler bits, separating concerns and making both development and testing each component independently. It entirely relies on the <strong>Dependency Injection Principle</strong>.
Moreover, it allows us to find a nice middleground between <strong>Domain Driven Design</strong> and <strong>Test Driven Development</strong> as we can easily write tests for our domain as we model it.

Bottom line, we can have our system as a <strong>3-Tier Stack</strong> where, each and every layer knows nothing about the one lying below itself.
![3-Tier Stack](https://lucid.app/publicSegments/view/e3602ef6-9243-4726-8cc5-807114cff42b/image.png)

Therefore, our project tree will basically be structured as 3 main packages:
- <strong>Controller</strong>
- <strong>Service</strong>
- <strong>Password Validator Library</strong>

The tests will follow up to the same above structure, according to the following granularity:
- Unit Tests:
  - Password Validator Library: core business logic for password validation
  - ValidatePassword Service: not much to do, aside of making sure the underlying core methods are called, therefore, mocking the PasswordValidator
  - ValidatePasswordController: verifies the requestDTO is valid, wrapping up a suitable error message as an APIError response when it does not; it asserts the ValidatePasswordService validation method (also mocked) is called; the web container is also mocked
 - Integration Test:
   - The web container is actually instanced as we test the interaction between the 3 components of the stack altogether

## The Business Logic
According to the problem requirements:
- 9 or more characters long: O(1) in both time and memory
- At least one digit: lower bound is O(n) in time, since we need to traverse, worst case, the entire word; O(1) in memory;
- At least one lower case letter: same as the above
- At least one upper case letter: same as the above
- At least one special character: same as the above
- No repeated character: is the word was lexicographically sorted, we would also achieve it in constant time, however, since it is not the case, we need to apply memoization (caching), which leads to O(n) in time and O(n) in memory

Summaring the above time and memory analysis, we can infer that the whole problem is a O(n) in time and O(n) in memory. However, embedding all of the above conditions during the password traversal would turn out not be be very maintainable, as new requirements might come up as the current ones might also change. Therefore, we are in a need to decoupling the process of checking all the desired conditions as satisfied. Thus, the time complexity would degrade to O(m * n), where "m" stands for the number of conditions we have to assert.

A handy design pattern for solving the problem, keeping the codebase clean and maintainable would be the <strong>Composite Pattern</strong>, which basically consists in dealing the an entity and a collection of it the same way.

![Class Diagram](https://lucid.app/publicSegments/view/f969374c-72b6-4fd8-b648-e8a947f11953/image.png)

We then design a PasswordValidatorChain, abstract, which makes it possible to recursively, concatenate multiple implementations of PasswordValidator and asserting each and every respective implementation os "isValid", also recursively, as the traversal of a linked list. By doing so, we follow up to most of the <strong>SOLID Principles</strong>:
- <strong>Single Responsibility</strong>: separation of concerns, code is cohesive, being every entity responsible exclusively for the purpose it is designed for
- <strong>Open Closed</strong> We can easily come up with different implementations of PasswordValidator, as new conditions come up
- <strong>Liskov Substitution</strong>: The API will seamlessly work for any underlying implementation for the PasswordValidator
- <strong>Dependency Injection<strong> Higher level modules are completely decoupled from the lower level library

We might ask ourselves why we do not simply use regular expression processing instead. The reason is: it would be an overkill. Regular expression processing is O(n!) in time, improving to O(n^2), through caching. The above solution, considering the password length outnumbers the conditions, we have amortized O(n) complexity in time.

## The Implementation
As the programming language, we go with Java 11. We are picking the Spring WebFlux framework, an event-drive, reactive architecture. As opposed to Spring Web MVC, which makes use of the blocking servlet architecture (one thread per request), it will only require a number of threads equal to 2 the number of available cores, which will also lead to less memory being used by the JVM. For slow services it always runs faster than its blocking counterpart. The downside is: the client must also be non-blocking, making use of asynchronous calls/drivers.

## Running The Project
First of all, we compile build the project using <strong>Apache Maven</strong> build tool:
```console
cd validate_password_api && \
mvn clean package && \
cd -
```
The above command will also run each and every test in the project, at any granularity. As soon as the tests are ran and successful, maven launches the web application with <strong>Netty</strong> running as an embedded server.

Then, assuming we have have both <strong>Docker</strong> and <strong>Docker-Compose</strong> installed, we run:
```console
$ docker-compose up -d --build
```
The test suite will run before lifting up the web container.

We can check the logs being written on the fly, as soon as the <strong>validate_password_service</strong> with:
```console
$ docker-compose logs --tail 1 --follow validate_password_service
```

We can then manually test the API with the command, by only replacing the password field for the one we want test for:
```console
curl -X 'POST' \
  'http://localhost:8080/api/v1/validate' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "password": "AbTp9!fok""
}'
```

Since we are exposing the API documentation with Swagger (OpenAPI 3) on <strong>http://localhost:8080/swagger-ui.html</strong>, we can also run manual tests by pressing the <strong>Try it out</strong> button on the top right corner of the page.

## Final Remarks
- We might consider introducing a memory cache such as MemCache or Redis, caching the passwords we have along the way as a <strong>Trie</strong> data structure or sharded hashmaps
- We might also consider delegating the whole logic to a reverse proxy / load balancer, such as NGinx or HaProxy, which can easly process, decorate and cache requests; we can also easily embed custom processing scripts to extending their functionality, therefore, relieving the computational load on our services

