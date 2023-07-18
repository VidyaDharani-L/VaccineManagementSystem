package com.service;

import java.util.List;

import com.exception.VaccineManagementExceptions;
import com.model.Vaccine;

public interface VaccineService {
    void validateName(String name) throws VaccineManagementExceptions;
    void validateQuantity(int quantity) throws VaccineManagementExceptions;
    List<Vaccine> viewVaccines();
    List<Vaccine> viewAvailableVaccines();
    void decrementVaccineBalance(String vaccineName);
    boolean checkVaccine(String vaccineName);
    void updateVaccine(String vaccineName, int updatedQuantity);  
}

