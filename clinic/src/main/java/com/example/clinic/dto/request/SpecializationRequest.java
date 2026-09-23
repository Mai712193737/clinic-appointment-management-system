package com.example.clinic.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class SpecializationRequest {
    long specializationId;
    String specializationName;
    String specializationDescription;

    public SpecializationRequest(long specializationId, String specializationName, String specializationDescription) {
        this.specializationId= specializationId;
        this.specializationName = specializationName;
        this.specializationDescription = specializationDescription;
    }
    public SpecializationRequest() {
    }

}
