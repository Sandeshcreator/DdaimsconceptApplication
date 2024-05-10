package com.learn.dams.contollers;

import com.learn.dams.dto.ConceptDto;
import com.learn.dams.services.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConceptController {
    @Autowired
    private ConceptService conceptService;

    @PostMapping("/")
    public ResponseEntity<ConceptDto> createCategory(@RequestBody ConceptDto conceptDto){
        ConceptDto createConcept = this.conceptService.createConcept(conceptDto);
        return new  ResponseEntity<ConceptDto> (createConcept, HttpStatus.CREATED);

    }





}

