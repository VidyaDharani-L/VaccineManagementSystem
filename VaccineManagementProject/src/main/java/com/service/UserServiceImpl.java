package com.service;

import com.exception.VaccineManagementExceptions;
import com.model.User;
import com.dao.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class); 
    private static UserDaoImpl userDao = new UserDaoImpl();    
    public static void setUserDao(UserDaoImpl userDao) {
        UserServiceImpl.userDao = userDao;
    }    
    public static UserDaoImpl getUserDao() {
        return userDao;
    }    
    public static UserServiceImpl getUserService() {
        return new UserServiceImpl();
    }
  
  
    public void validateName(String name) throws VaccineManagementExceptions {
        if (!name.matches("[a-zA-Z]+")) {
            throw new VaccineManagementExceptions("Name should only contain alphabets!");
        }
    }
    
    public void validateAge(int age) throws VaccineManagementExceptions {
        String ageString = String.valueOf(age);
        if (!ageString.matches("\\d+")) {
            throw new VaccineManagementExceptions("Age should be a positive number!");
        }
    }

    public void validateAadhar(Long aadhar) throws VaccineManagementExceptions {
        String aadharString = String.valueOf(aadhar);
        if (!aadharString.matches("^\\d{12}$")) {
            throw new VaccineManagementExceptions("Aadhar number should be a 12-digit number!");
        }
    }    

    public boolean checkUser(Long aadhar) {
        return userDao.getUser().stream()
                .anyMatch(user -> user.getAadhar() == aadhar);
    }
    
    public boolean addFirstDose(String userName, LocalDate localDate) {
        return userDao.getUser().stream()
                .filter(user -> user.getName().equals(userName) && user.getFirstDose()==null)
                .findFirst()
                .map(user -> {
                    user.setFirstDose(localDate);
                    return true;
                })
                .orElse(false);
    }


    public boolean isEligibleSecondDose(String userName) {
        return userDao.getUser().stream()
                .filter(user -> user.getName().equals(userName))
                .anyMatch(user -> {
                    LocalDate firstDose = user.getFirstDose();
                    LocalDate secondDose = user.getSecondDose();
                    if (firstDose != null && secondDose == null) {
                        long diffInMonths = ChronoUnit.MONTHS.between(firstDose, LocalDate.now());
                        return diffInMonths > 6;
                    }
                    return false;
                });
    }

    public boolean hasAlreadyTakenFirstDose(String name) {
        return userDao.getUser().stream()
                .anyMatch(user -> user.getName().equals(name) && user.getFirstDose() != null);
    }
    
    public boolean hasAlreadyTakenSecondDose(String name) {
        return userDao.getUser().stream()
                .anyMatch(user -> user.getName().equals(name) && user.getSecondDose() != null);
    }

    public boolean addSecondDose(String name, LocalDate localDate) {
        return userDao.getUser().stream()
                .filter(user -> user.getName().equals(name) && isEligibleSecondDose(name))
                .findFirst()
                .map(user -> {
                    user.setSecondDose(localDate);
                    return true;
                })
                .orElse(false);
    }

    public void viewReports(String name) throws VaccineManagementExceptions  {
        Optional<User> userOptional = userDao.getUser().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LOGGER.info("Name: {}", user.getName());
            LOGGER.info("Age: {}", user.getAge());
            LOGGER.info("First Dose Date: {}", user.getFirstDose() != null ? user.getFirstDose() : "Not Vaccinated");
            LOGGER.info("Second Dose Date: {}", user.getSecondDose() != null ? user.getSecondDose() : "Not Vaccinated");
        } else {
            throw new VaccineManagementExceptions("Please enter a valid name!");
        }
    }
}
