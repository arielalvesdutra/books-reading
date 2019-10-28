package dev.arielalvesdutra.booksreadings.unit.controllers;

import static dev.arielalvesdutra.booksreadings.unit.controllers.ObjectToJsonString.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dev.arielalvesdutra.booksreadings.controllers.AuthorController;
import dev.arielalvesdutra.booksreadings.controllers.dto.CreateAuthorDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateAuthorDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;
import dev.arielalvesdutra.booksreadings.services.AuthenticationService;
import dev.arielalvesdutra.booksreadings.services.AuthorService;
import dev.arielalvesdutra.booksreadings.services.TokenService;
import dev.arielalvesdutra.booksreadings.unit.entities.AuthorBuilder;
import dev.arielalvesdutra.booksreadings.unit.entities.CreateAuthorDTOBuilder;
import dev.arielalvesdutra.booksreadings.unit.entities.UpdateAuthorDTOBuilder;
import dev.arielalvesdutra.booksreadings.unit.entities.UserBuilder;;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorService authorService;
	
	@MockBean
	private UserRepository userRepository;
	
	@SpyBean
	private TokenService tokenService;
		
	@MockBean
	private AuthenticationService authService;
	
	private User fakeUser = new UserBuilder()
			.withEmail("email@email.com")
			.withPassword("password")
			.withName("Dennis")
			.withId(1L)
			.build();
	
	@Test
	public void listAuthors_shouldWork() throws Exception {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
		List<Author> authorList = new ArrayList<Author>();
		
		Author author = new AuthorBuilder()
				.withName("Ken Thompson")
				.withEmail("ken@thompson.com")
				.withId(1L)
				.build();
		
		authorList.add(author);
		Page<Author> authorPage = new PageImpl<Author>(authorList);
		given(this.authorService.findAll(null, pageable)).willReturn(authorPage);	
		
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/authors"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.[0].name").value(author.getName()))
			.andExpect(jsonPath("$.content.[0].email").value(author.getEmail()))
			.andExpect(jsonPath("$.content.[0].id").value(author.getId()))
			.andExpect(jsonPath("$.content.[0].books").exists());
	}
	
	@Test
	public void getAuthorById_withValidToken_shouldWork() throws Exception {
		
		String token = this.prepareFakeUserVerificationAndGenerateToken();
		
		Author author = new AuthorBuilder()
				.withName("Ken Thompson")
				.withEmail("ken@thompson.com")
				.withId(1L)
				.build();
		
		given(this.authorService.find(1L)).willReturn(author);
	
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/authors/1")
				.header("Authorization", "Bearer "+ token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(author.getName()))
				.andExpect(jsonPath("$.email").value(author.getEmail()))
				.andExpect(jsonPath("$.id").value(author.getId()));
	}
	
	@Test
	public void getAuthorById_withoutToken_shouldReturn403() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/authors/1"))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void getAuthorById_withoutAuthor_shouldReturn404() throws Exception {

		String token = this.prepareFakeUserVerificationAndGenerateToken();
		
		given(this.authorService.find(1L))
				.willThrow(new EntityNotFoundException("Author não encontrado"));
		
		
		mockMvc.perform(MockMvcRequestBuilders		
				.get("/authors/1")
				.header("Authorization", "Bearer "+ token))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void createAuthor_shouldWork() throws Exception {
		
		String token = this.prepareFakeUserVerificationAndGenerateToken();
		
		CreateAuthorDTO createAuthorDTO = new CreateAuthorDTOBuilder()
				.withName("Ken Thompson")
				.withEmail("ken@thompson.com")
				.build();
		
		Author createdAuthor = new AuthorBuilder()
				.withName(createAuthorDTO.getName())
				.withEmail(createAuthorDTO.getEmail())
				.withId(1L)
				.build();
		
		given(this.authorService.create(createAuthorDTO.toAuthor()))
				.willReturn(createdAuthor);
		
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/authors")
				.content(asJsonString(createAuthorDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+ token))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(createdAuthor.getName()))
				.andExpect(jsonPath("$.email").value(createdAuthor.getEmail()))
				.andExpect(jsonPath("$.id").value(createdAuthor.getId()));
	}
	
	@Test
	public void updateAuthor_shouldWork() throws Exception {
		
		String token = this.prepareFakeUserVerificationAndGenerateToken();
		
		UpdateAuthorDTO updateAuthorDto = new UpdateAuthorDTOBuilder()
				.withName("Ken Thomp.")
				.withEmail("ken@thomp.com")
				.build();
		
		Author updatedAuthor = new AuthorBuilder()
				.withName(updateAuthorDto.getName())
				.withEmail(updateAuthorDto.getEmail())
				.withId(1L)
				.build();
		
		given(this.authorService.update(1L, updateAuthorDto.toAuthor()))
				.willReturn(updatedAuthor);
		
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/authors/1")
				.content(asJsonString(updateAuthorDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+ token))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteAuthor_shouldWork() throws Exception {
		
		String token = this.prepareFakeUserVerificationAndGenerateToken();
		
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/authors/1")
				.header("Authorization", "Bearer "+ token))
				.andExpect(status().isOk());
	}
	
	private String prepareFakeUserVerificationAndGenerateToken() {
		
		given(this.userRepository.findById(ArgumentMatchers.anyLong()))
				.willReturn(Optional.of(fakeUser));
		
		String token = this.tokenService.generateToken(fakeUser);
		
		return token;		
	}
}
