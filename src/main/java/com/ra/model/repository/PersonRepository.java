package com.ra.model.repository;

import com.ra.model.entity.Person;


import java.util.List;

public interface PersonRepository {
    List<Person> findAll();
    Person saveOrUpdate(Person person);

    void delete(Long id);
    Person findById(Long id);

}
