package com.ui;

import com.service.VaccineServiceImpl;
import com.service.UserServiceImpl;
import com.dao.UserDaoImpl;
import com.dao.VaccineDaoImpl;
import com.exception.VaccineManagementExceptions;
import com.model.User;
import com.model.Vaccine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;


public class VaccineManagement {
	
    private static final Logger LOGGER = LogManager.getLogger(VaccineManagement.class);    
    static VaccineServiceImpl vaccineService = VaccineServiceImpl.getVaccineService();
    static VaccineDaoImpl vaccineDao = VaccineServiceImpl.getVaccineDao();
    static UserServiceImpl userService = UserServiceImpl.getUserService();
    static UserDaoImpl userDao = UserServiceImpl.getUserDao();
    static Scanner sc = new Scanner(System.in);
    private static String userName;
    
    public static void main(String[] args) {
    	
        LOGGER.info("Welcome To Vaccine Management System!!!");
        int role;
        boolean validRole = false;
        do {
        	LOGGER.info("Choose Your Role!");
        	LOGGER.info("1. Admin");
        	LOGGER.info("2. User");
            role = sc.nextInt();
            if (role == 1 || role == 2) {
                validRole = true;
            } else {
                LOGGER.info("Invalid role. Please choose a valid role");
            }
        } while (!validRole);
        
        if(role==1) {
            int choice;
            do {
                adminmenu();
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        vaccineService.viewVaccines();
                        break;
                    case 2:
                        addVaccine();
                        break;
                    case 3:
                        updateVaccine();
                        break;
                    case 4:
                        LOGGER.info("Thanks for using our application!!!");
                        break;
                    default:
                        LOGGER.error("Invalid option! Choose a valid option");
                }
            } while (choice != 4);

        }
        else {
        	userDetails();
            int choice;
            do {
                usermenu();
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        vaccineService.viewAvailableVaccines();
                        break;
                    case 2:
                        takeFirstDose(userName);
                        break;
                    case 3:
                        takeSecondDose(userName);
                        break;
                    case 4:
                        viewVaccineReport(userName);
                        break;
                    case 5:
                        LOGGER.info("Thanks for using our application!!!");
                        break;
                    default:
                        LOGGER.error("Invalid choice");
                }
            } while (choice != 5);
        }
    }


    private static void addVaccine() {
        boolean validVaccineName = false;
        String vaccineName = "";
        do {
        	LOGGER.info("Enter the vaccine name you want to add");                        	
            vaccineName = sc.nextLine();
            try {
                vaccineService.validateName(vaccineName);
                validVaccineName = true;
            } catch (VaccineManagementExceptions e) {
                LOGGER.info(e.getMessage());
            }
        } while(!validVaccineName);
        
        boolean validQuantity = false;
        int quantity = 0;
        do {
            LOGGER.info("Enter the quantity you want to add");
        	try {
                quantity = sc.nextInt();
                sc.nextLine();
        		vaccineService.validateQuantity(quantity);
        		validQuantity = true;
        	}
        	catch(VaccineManagementExceptions e) {
        		LOGGER.error(e.getMessage());
        	}
        	catch (InputMismatchException e) {
                LOGGER.error("Error: Invalid input. Quantity should be a number.");
                sc.nextLine(); 
            }
        } while(!validQuantity);  
        Vaccine vaccine = new Vaccine(vaccineName,quantity,quantity,LocalDate.now());
        vaccineDao.addVaccine(vaccine);
        LOGGER.info("Vaccine added successfully!!!");
    }

    private static void updateVaccine() {
        boolean validVaccineName = false;
        do {
        	LOGGER.info("Enter the name of the vaccine you want to update!");
            String vaccineNameToUpdate = sc.nextLine();
            if (vaccineService.checkVaccine(vaccineNameToUpdate)) {
            	validVaccineName = true;
                LOGGER.info("Enter the updated quantity of vaccine");
                int updatedQuantity = sc.nextInt();
                sc.nextLine();
                vaccineService.updateVaccine(vaccineNameToUpdate, updatedQuantity);
                LOGGER.info("Vaccine updated with the new quantity successfully!!!");
            } else {
                LOGGER.error("Invalid vaccine name. Please enter a valid vaccine name.");
            }
        } while (!validVaccineName);
    }
    
    public static void userDetails() {
        int userAge=0;
        Long userAadharNum=0L;
        sc.nextLine();

        boolean validUserName = false;
        do {
      	  LOGGER.info("Enter your name");
            userName = sc.nextLine();
            try {
                userService.validateName(userName);
                validUserName = true;
            } catch (VaccineManagementExceptions e) {
          	  LOGGER.error(e.getMessage());
            }
        } while (!validUserName);

        boolean validUserAge = false;
        do {
      	  LOGGER.info("Enter your age");
            try {
                userAge = sc.nextInt();
                sc.nextLine(); 

                userService.validateAge(userAge);
                validUserAge = true;
            } catch (VaccineManagementExceptions e) {
          	  LOGGER.error(e.getMessage());
            } catch (InputMismatchException e) {
          	  LOGGER.error("Error: Invalid input. Age should be a number.");
                sc.nextLine();
            }
        } while (!validUserAge);
        
        boolean validUserAadhar = false;
        do {
      	  LOGGER.info("Enter your aadhar");
            try {
                userAadharNum = sc.nextLong();
                sc.nextLine();
                userService.validateAadhar(userAadharNum);
                validUserAadhar = true;
            } catch (VaccineManagementExceptions e) {
          	  LOGGER.info(e.getMessage());
            }
            catch (InputMismatchException e) {
          	  LOGGER.error("Error: Invalid input. Age should be a number.");
                sc.nextLine();
            }
        } while (!validUserAadhar);
        User user = new User(userName, userAge, userAadharNum, null, null);
        userDao.addUser(user);
 
    }

    private static void takeFirstDose(String userName) {
    	String vaccineName;
    	boolean validVaccineName = false;
		vaccineService.viewAvailableVaccines();
    	do {
    		LOGGER.info("Enter the vaccine name you want to take from above available vaccines!");
			vaccineName = sc.nextLine();
			if(vaccineService.checkVaccine(vaccineName)) {
				validVaccineName = true;
			}
			else {
				LOGGER.info("Please enter a valid vaccine name!");
			}
    	}
		while(!validVaccineName);
			if(vaccineService.checkVaccine(vaccineName) && userService.addFirstDose(userName,LocalDate.now())) {
				vaccineService.decrementVaccineBalance(vaccineName);
				LOGGER.info("Successfully vaccinated first dose");
			}
			else {
				LOGGER.error("you have already taken first dose");
			}
    }

    private static void takeSecondDose(String userName) {
        String vaccineName = "";
        boolean validVaccineName=false;                    
        LOGGER.info("Available vaccines:");
        vaccineService.viewAvailableVaccines();
        do {
            LOGGER.info("Enter the vaccine name you want to take from the available vaccines:");
            vaccineName = sc.nextLine();
            
            if (vaccineService.checkVaccine(vaccineName)) {
                validVaccineName = true;
            } else {
                LOGGER.error("Please enter a valid vaccine name!");
            }
        } while (!validVaccineName);
        
        if (userService.hasAlreadyTakenSecondDose(userName)) {
            LOGGER.error("You have already taken the second dose.");
        } else if (!userService.hasAlreadyTakenFirstDose(userName)) {
            LOGGER.error("You should take the first dose before taking the second dose!");
        } else if (!userService.isEligibleSecondDose(userName)) {
            LOGGER.error("Cannot take the second dose before 6 months.");
        } else {
            userService.addSecondDose(userName,LocalDate.now());
            vaccineService.decrementVaccineBalance(vaccineName);
            LOGGER.info("Successfully vaccinated second dose.");
        }
    	
    }
    private static void viewVaccineReport(String userName) {
    	try {
			userService.viewReports(userName);
		} catch (VaccineManagementExceptions e) {
        	  LOGGER.info(e.getMessage());
		}    	
    }

    
    public static void adminmenu() {
        LOGGER.info("------Choose your option------");
        LOGGER.info("1. View vaccines");
        LOGGER.info("2. Add vaccines");
        LOGGER.info("3. Update vaccines");
        LOGGER.info("4. Exit");
    }

    public static void usermenu() {
        LOGGER.info("------Choose your option------");
        LOGGER.info("1. Check availability of vaccines");
        LOGGER.info("2. Take first dose of vaccine");
        LOGGER.info("3. Take second dose of vaccine");
        LOGGER.info("4. View my vaccine history");
        LOGGER.info("5. Exit");
    }


}
