package com.trendyol.student;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRepository {

    private static final Map<String,Student> students = new HashMap<>();

    @PostConstruct
    public void initData() {
        Student student_1 = new Student();
        student_1.setAge(BigInteger.TEN);
        student_1.setBranch("Matematik");
        student_1.setName("Ali");
        student_1.setSurname("Yılmaz");

        Country student1Country = new Country();
        student1Country.setName("Türkiye");
        student1Country.setCapital("Ankara");
        student1Country.setPopulation(1000000);

        student_1.setCountry(student1Country);


        students.put(student_1.getName(),student_1);

        Student student_2 = new Student();
        student_2.setName("Veli");
        student_2.setAge(BigInteger.TEN);
        student_2.setBranch("Fen");
        student_2.setSurname("Can");

        Country student2Country = new Country();
        student2Country.setCapital("Paris");
        student2Country.setName("Fransa");
        student2Country.setPopulation(2000000);

        student_2.setCountry(student2Country);

        students.put(student_2.getName(),student_2);
    }

    public Student findStudent(String name) {
        Assert.notNull(name, "The student's name must not be null");
        return students.get(name);
    }
}
