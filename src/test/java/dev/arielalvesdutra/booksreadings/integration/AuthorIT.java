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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateAuthorDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveAuthorDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateAuthorDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.repositories.AuthorRepository;
import dev.arielalvesdutra.booksreadings.services.AuthorService;
import dev.arielalvesdutra.booksreadings.services.TokenService;
import dev.arielalvesdutra.booksreadings.services.UserService;

/**
 * Teste de Integração para o Author
 * 
 * 
 * @author Ariel Alves Dutra
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ActiveProfiles("it")
public class AuthorIT {
	
	@Autowired
	private UserService userService;
	
	private User userForAuth;	
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private AuthorRepository authorRepository;

	@Before
	public void setUp() {
		this.userForAuth = this.userService.create(
				new User("admin", "admin@exemplo.com", "password"));
	
	}

	@After
	public void tearDown() {
		authorRepository.deleteAll();
		userService.deleteById(this.userForAuth.getId());
	}
	
	@Test
	public void createAuthor_shouldWorkAndReturn201() {
		
		CreateAuthorDTO createAuthorDto = new CreateAuthorDTO("Nat Pryce", "nat@pryce.com");
		String token = "Bearer " + this.tokenService.generateToken(this.userForAuth);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		
		HttpEntity<CreateAuthorDTO> httpEntity = 
				new HttpEntity<CreateAuthorDTO>(createAuthorDto, headers);
		
		
		ResponseEntity<RetrieveAuthorDTO> response = restTemplate.exchange(
				"/authors",
				HttpMethod.POST,
				httpEntity,
				RetrieveAuthorDTO.class);
		
		RetrieveAuthorDTO createdAuthor = response.getBody();
	
		
		assertThat(response.getStatusCode().value()).isEqualTo(201);
		assertThat(response.getHeaders().containsKey("Location")).isTrue();
		assertThat(createdAuthor.getName()).isEqualTo(createAuthorDto.getName());
		assertThat(createdAuthor.getEmail()).isEqualTo(createAuthorDto.getEmail());
		assertThat(createdAuthor.getId()).isNotNull();
	}
	
	@Test
	public void deleteAuthorById_shouldWork() {
		String token = "Bearer " + this.tokenService.generateToken(this.userForAuth);
		
		Author author = this.authorService.create(
				new Author("Kent Beck", "kent@beck.com"));		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		
		ResponseEntity<String> response = restTemplate.exchange(
				"/authors/" + author.getId(),
				HttpMethod.DELETE,
				httpEntity,
				String.class);
		
		Author fetchedAuthor = null;
		
		try {
			
			fetchedAuthor = this.authorService.find(author.getId());			
		} catch(Exception e) { }
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(fetchedAuthor).isNull();		
	}
	
	@Test
	public void deleteAuthorById_withoutToken_shouldReturn403() {
		Author author = this.authorService.create(
				new Author("Michael Feathers", "michael@feathers.com"));
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		
		ResponseEntity<String> response = restTemplate.exchange(
				"/authors/" + author.getId(),
				HttpMethod.DELETE,
				httpEntity,
				String.class);
		
	
		assertThat(response.getStatusCode().value()).isEqualTo(403);
	}
	
	@Test
	public void getAuthorById_shouldWork() {
		String token = "Bearer " + this.tokenService.generateToken(this.userForAuth);
		
		Author author = this.authorService.create(
				new Author("Michael Feathers", "michael@feathers.com"));		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		
		ResponseEntity<RetrieveAuthorDTO> response = restTemplate.exchange(
				"/authors/" + author.getId(),
				HttpMethod.GET,
				httpEntity,
				RetrieveAuthorDTO.class);
		
		RetrieveAuthorDTO retrievedAuthor = response.getBody();
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(retrievedAuthor.getId()).isEqualTo(author.getId());
		assertThat(retrievedAuthor.getName()).isEqualTo(author.getName());		
		assertThat(retrievedAuthor.getEmail()).isEqualTo(author.getEmail());
	}
	
	@Test
	public void getAuthorById_withoutToken_shouldReturn403() {
		Author author = this.authorService.create(
				new Author("Michael Feathers", "michael@feathers.com"));
		
		
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/authors/" + author.getId(), 
				String.class);
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(403);
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

	@Test
	public void updateAuthorById_shouldWork() {
		String token = "Bearer " + this.tokenService.generateToken(this.userForAuth);
		
		Author author = this.authorService.create(
				new Author("Martin Fowler", "martin@fowler.com"));
		
		UpdateAuthorDTO updateAuthorDto = new UpdateAuthorDTO("Martin", "mart@fowl.com");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		
		HttpEntity<UpdateAuthorDTO> httpEntity = 
				new HttpEntity<UpdateAuthorDTO>(updateAuthorDto, headers);
		
		
		ResponseEntity<RetrieveAuthorDTO> response = restTemplate.exchange(
				"/authors/" + author.getId(),
				HttpMethod.PUT,
				httpEntity,
				RetrieveAuthorDTO.class);
		RetrieveAuthorDTO updatedAuthor = response.getBody();
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(updatedAuthor.getId()).isEqualTo(author.getId());
		assertThat(updatedAuthor.getName()).isEqualTo(updateAuthorDto.getName());
		assertThat(updatedAuthor.getEmail()).isEqualTo(updateAuthorDto.getEmail());
	}
	
	@Test
	public void updateAuthorById_withoutToken_shouldReturn403() {
		
		Author author = this.authorService.create(
				new Author("Martin Fowler", "martin@fowler.com"));
		
		UpdateAuthorDTO updateAuthorDto = new UpdateAuthorDTO("Martin", "mart@fowl.com");
		
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<UpdateAuthorDTO> httpEntity = 
				new HttpEntity<UpdateAuthorDTO>(updateAuthorDto, headers);
		
		
		ResponseEntity<String> response = restTemplate.exchange(
				"/authors/" + author.getId(),
				HttpMethod.PUT,
				httpEntity,
				String.class);
		
		
		assertThat(response.getStatusCode().value()).isEqualTo(403);
	}
}
