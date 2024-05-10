package com.learn.dams.dto;

import com.learn.dams.entities.Concept;
import jakarta.persistence.*;

public class ClosureDto {
    private long ID;
    private Concept parent;
    private Concept  child;
    private int level;
}
