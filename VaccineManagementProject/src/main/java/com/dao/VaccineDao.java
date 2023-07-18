package com.dao;

import java.util.List;
import com.model.Vaccine;

public interface VaccineDao {
    List<Vaccine> getVaccines();
    void addVaccine(Vaccine vaccine);
    
}
