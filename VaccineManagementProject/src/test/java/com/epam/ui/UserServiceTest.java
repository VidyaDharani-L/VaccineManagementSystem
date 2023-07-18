package com.epam.ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.dao.UserDaoImpl;
import com.exception.VaccineManagementExceptions;
import com.model.User;
import com.service.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

class UserServiceImplTest {
    private UserServiceImpl userService;
    private UserDaoImpl userDao;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl();
        userDao = new UserDaoImpl();
        UserServiceImpl.setUserDao(userDao);
    }

    @Test
    void test_CheckUser() {
        User user = new User("Vidya", 30, 123456789012L, null, null);
        userDao.addUser(user);
        boolean exists = userService.checkUser(123456789012L);
        assertTrue(exists);
    }

    @Test
    void test_CheckUser_Invalid() {
        boolean exists = userService.checkUser(987654321098L);
        assertFalse(exists);
    }
    
    @Test
    void test_AddFirstDose_valid() {
        String userName = "Doremon";
        LocalDate localDate = LocalDate.now();
        boolean added = userService.addFirstDose(userName, localDate);
        assertTrue(added);
    }
    
    @Test
    void test_AddFirstDose_invalid() {
        String userName = "Jerry";
        LocalDate localDate = LocalDate.now();
        boolean added = userService.addFirstDose(userName, localDate);
        assertFalse(added);
    }

    @Test
    void test_AddSecondDose() {
        boolean added = userService.addSecondDose("Ram", LocalDate.now());
        assertTrue(added);
    }
    
    @Test
    void test_AddSecondDose_invalid() {
    	boolean added = userService.addSecondDose("Bheem",LocalDate.now());
    	assertFalse(added);    	
    }

    @Test
    void test_HasAlreadyTakenFirstDose_NotTaken() {
        String userName = "Bheem";
        boolean hasTaken = userService.hasAlreadyTakenFirstDose(userName);
        assertFalse(hasTaken);
    }

    @Test
    void test_HasAlreadyTakenSecondDose_NotTaken() {
        String userName = "Shinchan";
        boolean hasTaken = userService.hasAlreadyTakenSecondDose(userName);
        assertFalse(hasTaken);
    }
    
    @Test
    void test_ViewReports() {
        String userName = "Vidya";
        User user = new User(userName, 40, 123456789012L, LocalDate.now().minusMonths(3), LocalDate.now().minusMonths(1));
        userDao.addUser(user);
        assertDoesNotThrow(() -> userService.viewReports(userName));
    }
    
    @Test
    void test_ViewReports_Invalid() {
        VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class,
                () -> userService.viewReports("Oogy"));
        assertEquals("Please enter a valid name!", exception.getMessage());

    }

    @Test
    void test_ValidateName_ValidName() {
        String validName = "Vidya";
        assertDoesNotThrow(() -> userService.validateName(validName));
    }

    @Test
    void test_ValidateName_InvalidName() {
        String invalidName = "122";
        VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class,
                () -> userService.validateName(invalidName));
        assertEquals("Name should only contain alphabets!", exception.getMessage());
    }

    @Test
    void test_ValidateAge_ValidAge() {
        int validAge = 30;
        assertDoesNotThrow(() -> userService.validateAge(validAge));
    }

    @Test
    void test_ValidateAge_NegativeNumber() {
        int invalidAge = -5;
        VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class,
                () -> userService.validateAge(invalidAge));
        assertEquals("Age should be a positive number!", exception.getMessage());
    }

    @Test
    void test_ValidateAadhar_ValidAadhar() {
        long validAadhar = 123456789012L;
        assertDoesNotThrow(() -> userService.validateAadhar(validAadhar));
    }

    @Test
    void test_ValidateAadhar_InvalidLength() {
        long invalidAadhar = 123456789;
        VaccineManagementExceptions exception = assertThrows(VaccineManagementExceptions.class,
                () -> userService.validateAadhar(invalidAadhar));
        assertEquals("Aadhar number should be a 12-digit number!", exception.getMessage());
    }



}