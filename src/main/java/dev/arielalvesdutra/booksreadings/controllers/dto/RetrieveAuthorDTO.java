package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveAuthorDTO {
	
	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "Martin Fowler", position = 2)
	private String name;
	
	@ApiModelProperty(example = "email@exemplo.com", position = 3)
	private String email;
	
	@ApiModelProperty(position = 4)
	private Set<Book> books = new HashSet<Book>();

	public RetrieveAuthorDTO(Author author) {
		this.id = author.getId();
		this.name = author.getName();
		this.email = author.getEmail();
		this.setBooks(author.getBooks());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public static Page<RetrieveAuthorDTO> fromAuthorPageToRetrieveAuthorDTOPage(
			Page<Author> authorsPage) {
		return authorsPage.map(RetrieveAuthorDTO::new);
	}
}
