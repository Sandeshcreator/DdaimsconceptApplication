package com.learn.dams.services.impl;

import com.learn.dams.dto.ClosureDto;
import com.learn.dams.dto.ConceptDto;
import com.learn.dams.entities.Closure;
import com.learn.dams.entities.Concept;
import com.learn.dams.repository.ClosureRepo;
import com.learn.dams.repository.ConceptRepo;
import com.learn.dams.services.ClosureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClosureServiceImpl implements ClosureService {
    @Autowired
    private ConceptRepo conceptRepo;
    @Autowired
    private ClosureRepo closureRepo;

    @Override
    public void createClosure(Integer conceptId) throws ChangeSetPersister.NotFoundException {
        Concept concept = conceptRepo.findById(conceptId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        // Create a new Closure object
        Closure closure = new Closure();

        // Set both child and parent to the retrieved concept
        closure.setChild(concept);
        closure.setParent(concept);

        // Save the closure in the repository
        closureRepo.save(closure);


    }
}
