package com.service;


import java.time.LocalDate;
import java.util.Map;

import com.exception.VaccineManagementExceptions;
import com.model.User;

public interface UserService {
    void validateName(String name) throws VaccineManagementExceptions;
    void validateAge(int age) throws VaccineManagementExceptions;
    void validateAadhar(Long aadhar) throws VaccineManagementExceptions;
    boolean checkUser(Long aadhar);
    boolean addFirstDose(String name, LocalDate firstDose);
    boolean isEligibleSecondDose(String name);
    boolean hasAlreadyTakenFirstDose(String name);
    boolean hasAlreadyTakenSecondDose(String name);
    boolean addSecondDose(String name, LocalDate secondDose);
    void viewReports(String name) throws VaccineManagementExceptions;
 }

