package com.learn.dams.services;


import org.springframework.data.crossstore.ChangeSetPersister;

public interface ClosureService {
    void createClosure( Integer conceptId) throws ChangeSetPersister.NotFoundException;
}
