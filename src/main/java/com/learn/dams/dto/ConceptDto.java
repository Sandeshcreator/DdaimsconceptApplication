package com.learn.dams.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class ConceptDto {
    private long ID;
    private String identifier;
    private String label;
    private String active;

}
