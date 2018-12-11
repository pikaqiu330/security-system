package com.example.security.service;
import com.example.security.domain.Person;

import java.util.List;

public interface TestService {
    Person testApi();

    List<Person> findPersonList();
}
