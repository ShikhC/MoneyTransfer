package com.moneytransfer.demo.data.mapper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface ObjectMapper<S, T> {
    T mapRequest(S s);

    default T mapRequest(S s, @Nonnull T t) {
        return t;
    }

    Optional<S> mapResponse(T t);

    List<T> mapRequestList(List<S> s);

    List<S> mapResponseList(List<T> t);
}