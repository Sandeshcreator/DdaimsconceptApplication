package com.learn.dams.contollers;

import com.learn.dams.dto.ClosureDto;
import com.learn.dams.exception.ObjectNotFoundException;
import com.learn.dams.services.ClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClosureController {
    @Autowired
    private ClosureService closureService;

    @PostMapping("/{conceptId}/{identifier}")
    public ResponseEntity<Void> createPost(@PathVariable Long conceptId, @PathVariable String identifier) throws ChangeSetPersister.NotFoundException, ObjectNotFoundException {
        closureService.createClosure(conceptId, identifier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
