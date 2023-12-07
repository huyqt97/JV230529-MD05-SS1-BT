package com.ra.model.service;


import com.ra.model.dto.PersonDtoForm;
import com.ra.model.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person findById(Long id);
    void save(PersonDtoForm p);
    void delete(Long id);

}
