package com.example.security.controller;

import com.example.security.domain.Person;
import com.example.security.service.TestService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Person helloWorld(){
        Person person = testService.testApi();
        return person;
    }

    @RequestMapping(value = "/findPerson",method = RequestMethod.GET)
    public List<Person> findPerson(){
        List<Person> personList = testService.findPersonList();
        return personList;
    }

}
