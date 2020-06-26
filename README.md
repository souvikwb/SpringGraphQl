# SpringGraphQl
This is a basic graphql example using h2 db. We have added a person schema "person.graphqls" in resource folder. Created a separate graphql config and added to diffrent data fetchers 
a. "getAllPerson", b. "findPerson". In controller class load the graphQL schema and execute the query.

From Jmeter or POSTman we are passing below body data as below for "getAllPerson" to fetch from DB.
<br/>
{<br/>
	getAllPerson{ <br/>
		name <br/>
		email <br/>
		mobile <br/>
	} <br/>
} <br/>
