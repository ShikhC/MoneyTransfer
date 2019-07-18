package com.moneytransfer.demo.data.dao;

import com.moneytransfer.demo.data.repository.RepositoryService;

public abstract class GenericDao<T> {

    protected RepositoryService repositoryService;

    public GenericDao(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public abstract T save(T t);

    public abstract T get(String id);
}
