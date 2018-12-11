package com.example.security.repository;
import com.example.security.domain.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    Person testApi();

    List<Person> findPerson();
}
