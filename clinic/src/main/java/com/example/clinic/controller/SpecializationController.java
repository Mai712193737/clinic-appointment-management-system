
package com.example.clinic.controller;

import com.example.clinic.dto.ApiResponse;
import com.example.clinic.dto.request.SpecializationRequest;
import com.example.clinic.dto.response.SpecializationResponse;
import com.example.clinic.model.Specialization;
import com.example.clinic.service.SpecializationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping
    public ApiResponse<SpecializationResponse> addSpecialization(
            @RequestBody SpecializationRequest request) {

        Specialization specialization = new Specialization();
        specialization.setName(request.getSpecializationName());
        specialization.setDescription(request.getSpecializationDescription());

        Specialization savedSpecialization =
                specializationService.addSpecialization(specialization);

        SpecializationResponse response = new SpecializationResponse(
                savedSpecialization.getName(),
                savedSpecialization.getDescription()
        );

        return new ApiResponse<>(
                true,
                "Specialization added successfully",
                response
        );
    }

    @PutMapping("/{specializationId}")
    public ApiResponse<SpecializationResponse> updateSpecialization(
            @PathVariable Long specializationId,
            @RequestBody SpecializationRequest request) {

        Specialization updatedSpecialization =
                specializationService.updateSpecialization(
                        request.getSpecializationName(),
                        specializationId,
                        request.getSpecializationDescription()
                );

        SpecializationResponse response = new SpecializationResponse(
                updatedSpecialization.getName(),
                updatedSpecialization.getDescription()
        );

        return new ApiResponse<>(
                true,
                "Specialization updated successfully",
                response
        );
    }

    @DeleteMapping("/{specializationId}")
    public ApiResponse<Void> deleteSpecialization(
            @PathVariable Long specializationId) {

        specializationService.deleteSpecialization(specializationId);

        return new ApiResponse<>(
                true,
                "Specialization deleted successfully",
                null
        );
    }

    @GetMapping("/{specializationId}")
    public ApiResponse<SpecializationResponse> getSpecializationById(
            @PathVariable Long specializationId) {

        Specialization specialization =
                specializationService.getSpecializationById(specializationId);

        SpecializationResponse response = new SpecializationResponse(
                specialization.getName(),
                specialization.getDescription()
        );

        return new ApiResponse<>(
                true,
                "Specialization retrieved successfully",
                response
        );
    }

    @GetMapping
    public ApiResponse<List<SpecializationResponse>> getAllSpecializations() {

        List<Specialization> specializations =
                specializationService.getAllSpecializations();

        List<SpecializationResponse> responses = specializations.stream()
                .map(specialization ->
                        new SpecializationResponse(
                                specialization.getName(),
                                specialization.getDescription()
                        )
                )
                .toList();

        return new ApiResponse<>(
                true,
                "Specializations retrieved successfully",
                responses
        );
    }
}
