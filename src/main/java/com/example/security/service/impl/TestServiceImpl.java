package com.example.security.service.impl;
import com.example.security.domain.Person;
import com.example.security.repository.TestMapper;
import com.example.security.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Person testApi() {
        Person person = new Person();
        person.setPersonId(10086);
        person.setAge(18);
        person.setName(" 中国移动");
        person.setSex(1);
        person.setBirthday(new Date());
        return person;
    }

    @Override
    @Transactional
    public List<Person> findPersonList() {
        List<Person> personList = testMapper.findPerson();
        return personList;
    }
}
