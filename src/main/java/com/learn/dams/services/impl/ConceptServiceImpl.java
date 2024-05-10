package com.learn.dams.services.impl;

import com.learn.dams.dto.ConceptDto;
import com.learn.dams.entities.Concept;
import com.learn.dams.repository.ConceptRepo;
import com.learn.dams.services.ConceptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConceptServiceImpl implements ConceptService {

    @Autowired
    private ConceptRepo conceptRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ConceptDto createConcept(ConceptDto conceptDto) {

        Concept content = this.modelMapper.map(conceptDto, Concept.class);
        Concept addedContent= this.conceptRepo.save(content);
        return this.modelMapper.map(addedContent,ConceptDto.class);
    }
}
