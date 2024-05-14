package com.learn.dams.services;


import com.learn.dams.exception.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface ClosureService {
    void createClosure( Long conceptId , String identifier) throws ChangeSetPersister.NotFoundException, ObjectNotFoundException;
}
