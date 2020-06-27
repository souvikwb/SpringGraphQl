package com.souvik.org.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.souvik.org.config.GraphQlConfig;
import com.souvik.org.dao.PersonRepository;
import com.souvik.org.entity.Person;

import graphql.ExecutionResult;
import graphql.GraphQL;

@RestController
public class PersonController {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	GraphQlConfig graphQlConfig;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String addPerson(@RequestBody List<Person> persons) {
		personRepository.saveAll(persons);
		return "record inserted";
	}
	
	@RequestMapping(value="/fetchPerson", method=RequestMethod.GET)
	public List<Person> getPerson(){
		return personRepository.findAll();
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<Object> getAll(@RequestBody String query) throws IOException {
		GraphQL graphQL = graphQlConfig.loadSchema();
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@PostMapping("/getPersonByEmail")
	public ResponseEntity<Object> getPersonByEmail(@RequestBody String query) throws IOException {
		GraphQL graphQL = graphQlConfig.loadSchema();
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}
