package com.epam.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dao.VaccineDaoImpl;
import com.exception.VaccineManagementExceptions;
import com.model.Vaccine;
import com.service.VaccineServiceImpl;


// TO-DO
// check vaccine if its not present
// view vaccines when there are no vaccines
// update vaccine 
// add vaccine
class VaccineServiceTest { 
	
	private VaccineDaoImpl vaccinedao;
	private VaccineServiceImpl vaccineservice;
	
	@BeforeEach
	void setup() {
		 vaccinedao = new VaccineDaoImpl();
		 vaccineservice = new VaccineServiceImpl();
		 VaccineServiceImpl.setVaccineDao(vaccinedao);
	}
	
	@Test
	void test_CheckVaccine() {
		boolean actual = vaccineservice.checkVaccine("Vaccine190");		
		assertEquals(false,actual);
	}
	
	@Test
	void test_VaccinesLength() {
	    assertEquals(3, vaccinedao.getVaccines().size()); 
	}
	
	@Test
	void test_AddVaccine() {
		Vaccine vaccine = new Vaccine("vaccine33",33,33,LocalDate.now());
		vaccinedao.addVaccine(vaccine);
	    List<Vaccine> vaccineList = vaccinedao.getVaccines();
	    assertEquals(4, vaccineList.size()); 	    
	    Vaccine addedVaccine = vaccineList.get(vaccineList.size() - 1); 
	    assertEquals("vaccine33", addedVaccine.getName()); 
	    assertEquals(33,addedVaccine.getInitial());
	    assertEquals(33,addedVaccine.getBalance());
	}
	
	@Test 
	void test_UpdateVaccine() {
		vaccineservice.updateVaccine("vaccine1", 33);
		assertEquals(33,vaccinedao.getVaccines().get(0).getInitial());
	}
	
	@Test
    void test_DecrementVaccineBalance() {
        vaccineservice.decrementVaccineBalance("vaccine1");
        assertEquals(32, vaccinedao.getVaccines().get(0).getBalance());
    }	
	
	@Test
	void test_ViewVaccines() {
		Vaccine vaccine3 = new Vaccine("v3",21,0,LocalDate.now());
		Vaccine vaccine4 = new Vaccine("v4",6,3,LocalDate.now());
		vaccinedao.addVaccine(vaccine3);
		vaccinedao.addVaccine(vaccine4);
		assertEquals(6,vaccineservice.viewVaccines().size());
	}
	
	@Test
    void test_ViewAvailableVaccines() {
		Vaccine vaccine1 = new Vaccine("v1",33,10,LocalDate.now());
		Vaccine vaccine2 = new Vaccine("v2",33,0,LocalDate.now());
		vaccinedao.addVaccine(vaccine1);
		vaccinedao.addVaccine(vaccine2);
		assertEquals(6,vaccineservice.viewAvailableVaccines().size());
    }
	
	@Test
	void test_ValidateQuantity_NegativeNumber() {
	    int invalidQuantity = -5;
	    VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class, () -> vaccineservice.validateQuantity(invalidQuantity));
	    assertEquals("Quantity should be a positive number!", exception.getMessage());
	}
	
	@Test 
	void test_ValidateVaccineName() {
		VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class, () -> vaccineservice.validateName("12w"));
		assertEquals("Name should start with a alphabet and shoudn't be null!!",exception.getMessage());
	}

}
