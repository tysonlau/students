package com.students.students.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    List<Student> findByWeight(int weight);
    List<Student> findByNameAndColour(String name, String colour);
    List<Student> findByUid(int uid);
}
