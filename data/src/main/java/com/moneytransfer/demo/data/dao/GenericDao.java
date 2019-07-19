package com.moneytransfer.demo.data.dao;

import com.moneytransfer.demo.data.repository.IRepositoryService;

public abstract class GenericDao<T> {

    protected IRepositoryService repositoryService;

    public GenericDao(IRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public abstract T save(T t);

    public abstract T get(String id);
}
