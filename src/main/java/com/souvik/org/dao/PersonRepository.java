package com.souvik.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souvik.org.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	Person findByEmail(String email);
}
