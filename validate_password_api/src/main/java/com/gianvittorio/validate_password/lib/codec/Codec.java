package com.gianvittorio.validate_password.lib.codec;

public interface Codec<T, V> {
    V encode(T t);
    T decode(V v);
}
