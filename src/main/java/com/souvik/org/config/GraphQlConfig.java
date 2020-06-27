package com.souvik.org.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.souvik.org.dao.PersonRepository;
import com.souvik.org.entity.Person;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Configuration
public class GraphQlConfig {

	@Autowired
	private PersonRepository repository;

	private GraphQL graphQL;
	
	@PostConstruct
	public GraphQL loadSchema() throws IOException {
		File schemaFile = ResourceUtils.getFile("classpath:person.graphqls");
		TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
		return graphQL;
	}

	private RuntimeWiring buildWiring() {
		DataFetcher<List<Person>> fetcher1 = data -> {
			return (List<Person>) repository.findAll();
		};

		DataFetcher<Person> fetcher2 = data -> {
			return repository.findByEmail(data.getArgument("email"));
		};

		return RuntimeWiring.newRuntimeWiring().type("Query",
				typeWriting -> typeWriting.dataFetcher("getAllPerson", fetcher1).dataFetcher("findPerson", fetcher2))
				.build();

	}
}
