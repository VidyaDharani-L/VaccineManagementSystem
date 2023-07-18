package com.service;

import com.dao.VaccineDaoImpl;
import com.exception.VaccineManagementExceptions;
import com.model.Vaccine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class VaccineServiceImpl implements VaccineService {
	
	private static final Logger LOGGER = LogManager.getLogger(VaccineServiceImpl.class);	  
    private static VaccineDaoImpl vaccineDao = new VaccineDaoImpl();
    public static void setVaccineDao(VaccineDaoImpl vaccineDao) {
        VaccineServiceImpl.vaccineDao = vaccineDao;
    }
    public static VaccineDaoImpl getVaccineDao() {
        return vaccineDao;
    }    
    
    public static VaccineServiceImpl getVaccineService() {
        return new VaccineServiceImpl();
    }
  
    public void validateName(String name) throws VaccineManagementExceptions {
        if (!name.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            throw new VaccineManagementExceptions("Name should start with a alphabet and shoudn't be null!!");
        }
    }

    public void validateQuantity(int quantity) throws VaccineManagementExceptions {
        if (quantity <= 0) {
            throw new VaccineManagementExceptions("Quantity should be a positive number!");
        }
    }

    public List<Vaccine> viewVaccines() {
        LOGGER.info("VaccineName TotalQuantity Balance");
        return vaccineDao.getVaccines().stream()
                .map(vaccine -> {
                    LOGGER.info(String.format("%-15s  %d          %d", vaccine.getName(), vaccine.getInitial(), vaccine.getBalance()));
                    return vaccine;
                })
                .collect(Collectors.toList());
    }

    public List<Vaccine> viewAvailableVaccines() {
        LOGGER.info("VaccineName    Balance");
        return vaccineDao.getVaccines().stream()
                .filter(vaccine -> {
                    LOGGER.info(String.format("%-15s %d", vaccine.getName(), vaccine.getBalance()));
                    return vaccine.getBalance() > 0;
                })
                .collect(Collectors.toList());
    }

    public void decrementVaccineBalance(String vaccineName) {
        Optional<Vaccine> vaccineOptional = vaccineDao.getVaccines().stream()
                .filter(vaccine -> vaccine.getName().equals(vaccineName))
                .findFirst();

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();
            int balance = vaccine.getBalance();
            if (balance > 0) {
                vaccine.setBalance(balance - 1);
            } else {
                LOGGER.error("Vaccine balance is already zero!");
            }
        } else {
            LOGGER.error("Vaccine does not exist!");
        }
    }

    public boolean checkVaccine(String vaccineName) {
        return vaccineDao.getVaccines().stream()
                .anyMatch(vaccine -> vaccine.getName().equals(vaccineName));
    }
    
   
    public void updateVaccine(String vaccineName, int updatedQuantity) {
        vaccineDao.getVaccines().stream()
                .filter(vaccine -> vaccine.getName().equals(vaccineName))
                .forEach(vaccine -> {
                    vaccine.setInitial(updatedQuantity);
                    vaccine.setBalance(updatedQuantity);
                });
    }
}
