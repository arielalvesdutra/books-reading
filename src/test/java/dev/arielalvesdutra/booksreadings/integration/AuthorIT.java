package dev.arielalvesdutra.booksreadings.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveAuthorDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.repositories.AuthorRepository;
import dev.arielalvesdutra.booksreadings.services.AuthorService;

/**
 * Teste de Integração para o Author
 * 
 * 
 * @author Ariel Alves Dutra *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public class AuthorIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private AuthorRepository authorRepository;

	@Before
	public void setUp() {}

	@After
	public void tearDown() {
		authorRepository.deleteAll();
	}

	@Test
	public void listAuthors_shouldWork() {
		Author author1 = this.authorService.create(
				new Author("Kent Beck", "kent@beck.com"));
		Author author2 = this.authorService.create(
				new Author("Michael Feathers", "michael@feathers.com"));
		
		RetrieveAuthorDTO expectedAuthor1 = new RetrieveAuthorDTO(author1);
		RetrieveAuthorDTO expectedAuthor2 = new RetrieveAuthorDTO(author2);
		
		
		ResponseEntity<PagedResources<RetrieveAuthorDTO>> response = restTemplate.exchange(
				"/authors",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<PagedResources<RetrieveAuthorDTO>>() {});
		PagedResources<RetrieveAuthorDTO> resources = response.getBody();
		List<RetrieveAuthorDTO> retrievedAuthorDtoList = new ArrayList<RetrieveAuthorDTO>(resources.getContent());
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(retrievedAuthorDtoList.size()).isEqualTo(2);
		assertThat(retrievedAuthorDtoList.contains(expectedAuthor1)).isTrue();
		assertThat(retrievedAuthorDtoList.contains(expectedAuthor2)).isTrue();
	}
}
