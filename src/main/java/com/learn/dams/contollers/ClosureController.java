package com.learn.dams.contollers;

import com.learn.dams.dto.ClosureDto;
import com.learn.dams.services.ClosureService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClosureController {
    private ClosureService closureService;

    @PostMapping("/{conceptId}")
    public ResponseEntity<Void> createPost(@PathVariable Integer conceptId) throws ChangeSetPersister.NotFoundException {
        closureService.createClosure(conceptId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
