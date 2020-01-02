package dev.arielalvesdutra.booksreadings.unit.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import dev.arielalvesdutra.booksreadings.builders.BookBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;

@RunWith(SpringRunner.class)
public class AuthorTest {
	
	@Test
	public void setAndGetName_shouldWork() {
		String name = "Author Name";
		Author author = new Author();
		
		author.setName(name);
		
		assertThat(author.getName()).isEqualTo(name);
	}
	
	@Test
	public void setAndGetEmail_shouldWork() {
		String email = "exemplo@exemplo.com";
		Author author = new Author();
		
		author.setEmail(email);
		
		assertThat(author.getEmail()).isEqualTo(email);
	}
	
	@Test
	public void setAndGetId_shouldWork() {
		Long id = 1L;
		Author author = new Author();
		
		author.setId(id);
		
		assertThat(author.getId()).isEqualTo(id);
	}
	
	@Test
	public void setAndGetBooks_shouldWork() {
		Book book = new BookBuilder()
				.withId(1L)
				.withName("Livro de Teste")
				.withPublicationYear(2005)
				.build();
		Set<Book> bookSet = new HashSet<Book>();
		Author author = new Author();
		
		bookSet.add(book);
		author.setBooks(bookSet);
		
		assertThat(author.getBooks().size()).isEqualTo(1);
		assertThat(author.getBooks().contains(book)).isTrue();
	}
	
	@Test(expected = InvalidParameterException.class)
	public void setInvalidEmail_shouldThrowAnInvalidParameterException() {
		String invalidEmail = "emailinvalido";
		Author author = new Author();
		
		author.setEmail(invalidEmail);
	}
}
