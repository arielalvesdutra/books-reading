package dev.arielalvesdutra.booksreadings.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.AuthorRepository;
import dev.arielalvesdutra.booksreadings.services.AuthorService;
import dev.arielalvesdutra.booksreadings.services.BookService;
import dev.arielalvesdutra.booksreadings.unit.entities.AuthorBuilder;
import dev.arielalvesdutra.booksreadings.unit.entities.BookBuilder;


@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {
	
	private AuthorBuilder authorBuilder;
	
	@Mock
	private Page<Author> pageAuthorMock;
	
	@Mock
	private Pageable pageableMock;
	
	@Mock
	private AuthorRepository authorRepository;
	
	@Mock
	private BookService bookService;
	
	private AuthorService authorService;
	
	@Before
	public void setUp() { 
		authorService = new AuthorService(this.authorRepository, this.bookService);
		authorBuilder = new AuthorBuilder();
	}
	
	@Test
	public void createAuthor_shouldWork() {
		String authorName = "Martin";
		String authorEmail = "martin@fowler.com";
		Long authorId = 1L;
	
		given(this.authorRepository.save(authorBuilder
				.withName(authorName)
				.withEmail(authorEmail)
				.build())).willReturn(authorBuilder.withId(authorId).build());
		
		Author createdAuthor = this.authorService.create(new Author(authorName, authorEmail));
		
		
		assertThat(createdAuthor).isNotNull();
		assertThat(createdAuthor.getId()).isEqualTo(authorId);
		assertThat(createdAuthor.getName()).isEqualTo(authorName);
		assertThat(createdAuthor.getEmail()).isEqualTo(authorEmail);	
	}
	
	@Test(expected = RuntimeException.class)
	public void createAuthor_withDuplicatedEmail_shouldThrowAnConstraintException() {
		Author author1 = authorBuilder
				.withName("Martin Fowler")
				.withEmail("email@email.com")
				.build();
		
		Author author2 = authorBuilder
				.withName("Kent Beck")
				.withEmail("email@email.com")
				.build();
		given(this.authorRepository.save(author2))
				.willThrow(new RuntimeException("Não é possível criar mais de um autor com o mesmo ID"));
		
		this.authorService.create(author1);
		this.authorService.create(author2);
	}
	
	@Test
	public void deleteAuthorById_shouldWork() {
		Author author = authorBuilder
				.withName("Robert Martin")
				.withEmail("robert@martin.com")
				.withId(1L)
				.build();
		given(this.authorRepository.findById(1L)).willReturn(Optional.of(author));
		
		
		this.authorService.deleteById(1L);
		
		
		verify(this.authorRepository, Mockito.atLeastOnce()).delete(author);
	}
	
	@Test
	public void findAuthorById_shouldWork() {
		Author author = authorBuilder
				.withName("Dave Thomas")
				.withEmail("dave@thomas.com")
				.withId(1L)
				.build();
		given(this.authorRepository.findById(1L)).willReturn(Optional.of(author));
		
		
		Author fetchedAuthor = this.authorService.find(1L);
		
		
		assertThat(fetchedAuthor.getId()).isEqualTo(1L);
		assertThat(fetchedAuthor.getEmail()).isEqualTo("dave@thomas.com");
		assertThat(fetchedAuthor.getName()).isEqualTo("Dave Thomas");
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void findAuthorById_withoutAuthor_shouldTrownAnNotFindEntityException() {
	
		this.authorService.find(1L);
		
		verify(this.authorRepository, Mockito.atLeastOnce()).findById(1L);
	}
	
	@Test
	public void findAllAuthorsContaingNameWithPagination_shouldWork() {
		Author author1 = authorBuilder
				.withName("Martin Fowler")
				.withEmail("martin@fowler.com")
				.withId(1L)
				.build();
		
		Author author2 = authorBuilder
				.withName("Kent Beck")
				.withEmail("kent@beck.com")
				.withId(2L)
				.build();
		
		List<Author> authorList = new ArrayList<Author>();
		authorList.add(author1);
		authorList.add(author2);
		
		Example<Author> containingNameFilter = Example.of(new Author(), ExampleMatcher.matchingAny()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));;
		
		given(this.authorRepository.findAll(containingNameFilter, this.pageableMock))
			.willReturn(this.pageAuthorMock);
		given(this.pageAuthorMock.getContent()).willReturn(authorList);
		
		
		Page<Author> authorPage = this.authorService.findAll("n", this.pageableMock);
		List<Author> fetchedAuthorList = authorPage.getContent();
		
		
		assertThat(authorPage).isNotNull();
		assertThat(fetchedAuthorList).isNotNull();
		assertThat(fetchedAuthorList.size()).isEqualTo(2);
		assertThat(fetchedAuthorList.contains(author1)).isTrue();
		assertThat(fetchedAuthorList.contains(author2)).isTrue();
	}
	
	@Test
	public void findAllAuthorsByIds_shouldWork() {
		Author author1 = authorBuilder
				.withName("Martin Fowler")
				.withEmail("martin@fowler.com")
				.withId(1L)				
				.build();
		
		Author author2 = authorBuilder
				.withName("Kent Beck")
				.withEmail("kent@beck.com")
				.withId(2L)
				.build();
		
		List<Long> authorsIds = new ArrayList<Long>();
		authorsIds.add(1L);
		authorsIds.add(2L);

		List<Author> authorList = new ArrayList<Author>();
		authorList.add(author1);
		authorList.add(author2);
		given(this.authorRepository.findById(1L)).willReturn(Optional.of(author1));
		given(this.authorRepository.findById(2L)).willReturn(Optional.of(author2));
		
		
		List<Author> fetchedAuthorList = this.authorService.findAllByIds(authorsIds);
		
				
		assertThat(fetchedAuthorList).isNotNull();
		assertThat(fetchedAuthorList.contains(author1)).isTrue();
		assertThat(fetchedAuthorList.contains(author2)).isTrue();
	}
	
	@Test
	public void findAllAuthorsByBookIdWithPagination_shouldWork() {
		Book book = new BookBuilder()
				.withName("Refatoração")
				.withPublicationYear(1999)
				.withId(1L)
				.build();
		
		Author author1 = authorBuilder
				.withName("Martin Fowler")
				.withEmail("martin@fowler.com")
				.withId(1L)
				.withBook(book)
				.build();
		
		Author author2 = authorBuilder
				.withName("Kent Beck")
				.withEmail("kent@beck.com")
				.withId(2L)
				.withBook(book)
				.build();

		List<Author> authorList = new ArrayList<Author>();
		authorList.add(author1);
		authorList.add(author2);
		
		given(this.authorRepository.findAllByBooks_Id(1L, this.pageableMock)).willReturn(this.pageAuthorMock);
		given(this.pageAuthorMock.getContent()).willReturn(authorList);
		
		
		Page<Author> authorsPage = this.authorService.findAllByBookId(1L, this.pageableMock);
		List<Author> fetchedAuthorList = authorsPage.getContent();
		
		
		assertThat(fetchedAuthorList).isNotNull();
		assertThat(fetchedAuthorList.contains(author1)).isTrue();
		assertThat(fetchedAuthorList.contains(author2)).isTrue();
	}
	
	@Test
	public void updateAuthor_shouldWork( ) {
		Author author = authorBuilder
				.withName("Erich Gamma")
				.withEmail("erich@gamma.com")
				.withId(1L)
				.build();		
		given(this.authorRepository.findById(1L)).willReturn(Optional.of(author));
		
		
		Author fetchedAuthor = this.authorService.find(1L);	
		fetchedAuthor.setName("Erich");
		fetchedAuthor.setEmail("erich@gamma.net");
		Author updatedAuthor = this.authorService.update(fetchedAuthor.getId(), fetchedAuthor);
		
		
		assertThat(updatedAuthor).isNotNull();
		assertThat(updatedAuthor.getId()).isEqualTo(author.getId());
		assertThat(updatedAuthor.getName()).isEqualTo(author.getName());
		assertThat(updatedAuthor.getEmail()).isEqualTo(author.getEmail());
	}
}
 