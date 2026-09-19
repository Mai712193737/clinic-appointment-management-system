package com.example.clinic.service;

import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.model.Specialization;
import com.example.clinic.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization addSpecialization(Specialization specialization){
            return specializationRepository.save(specialization);
    }

    public Specialization updateSpecialization(String specializationName, Long specializationId, String description) {
        Optional<Specialization> specialization = specializationRepository.findById(specializationId);
        if (!specialization.isPresent()) {
            throw new ResourceNotFoundException("Specialization not found");
        }
        specialization.get().setName(specializationName);
        specialization.get().setDescription(description);
        return specializationRepository.save(specialization.get());
    }

    public Specialization getSpecializationById(Long specializationId) {
        Optional <Specialization> specialization = specializationRepository.findById(specializationId);
        if(!specialization.isPresent()){
            throw new ResourceNotFoundException("Specialization not found");
        }else{
            return specialization.get();
        }
    }

     public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    public void deleteSpecialization(Long specializationId) {
        Optional<Specialization> specialization = specializationRepository.findById(specializationId);
        if(!specialization.isPresent()){
            throw new ResourceNotFoundException("Specialization not found");
        }
        specializationRepository.delete(specialization.get());
    }

    public Specialization searchSpecializationByName(String specializationName) {
        Optional<Specialization> specialization = specializationRepository.findByName(specializationName);
        if (!specialization.isPresent()){
            throw new ResourceNotFoundException("Specialization not found");
        }
        return specialization.get();
    }
}
