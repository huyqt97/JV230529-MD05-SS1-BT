package com.ra.model.service;


import com.ra.model.dto.PersonDtoForm;
import com.ra.model.entity.Person;
import com.ra.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private String pathImage = "C:\\Users\\admin\\Desktop\\MD5-session01\\src\\main\\webapp\\upload\\";
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public void save(PersonDtoForm p) {
        // xử lý chuyển đổi
        // up load file
        String filename = null;
        if (!(p.getAvatar().isEmpty())) {
            filename = p.getAvatar().getOriginalFilename();
            try {
                FileCopyUtils.copy(p.getAvatar().getBytes(), new File(pathImage + filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //chuyển từ dto thành entity
        Person person = new Person(p.getId(), p.getAddress(), p.getAge(), filename, p.isSex(), p.getName());
        personRepository.saveOrUpdate(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.delete(id);
    }
}
