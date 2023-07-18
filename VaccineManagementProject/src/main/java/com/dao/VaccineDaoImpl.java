package com.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.model.Vaccine;

public class VaccineDaoImpl implements VaccineDao {
    
	private static List<Vaccine> vaccines = new ArrayList<Vaccine>();   
    
	@Override
    public List<Vaccine> getVaccines() {
        return vaccines;
    }
  
    static {
        Vaccine vaccine1 = new Vaccine("vaccine1", 7, 7, LocalDate.now());
        vaccines.add(vaccine1);
        Vaccine vaccine2 = new Vaccine("vaccine2", 9, 9, LocalDate.now());
        vaccines.add(vaccine2);
        Vaccine vaccine3 = new Vaccine("vaccine3", 3, 3, LocalDate.now());
        vaccines.add(vaccine3);
    }
    
    @Override
    public void addVaccine(Vaccine vaccine) {
        vaccines.add(vaccine);
    }



}
