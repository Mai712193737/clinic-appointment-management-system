
package com.example.clinic.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationResponse {

    private String name;
    private String description;

    public SpecializationResponse() {
    }

    public SpecializationResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
