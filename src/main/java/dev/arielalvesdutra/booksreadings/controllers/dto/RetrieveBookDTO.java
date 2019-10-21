package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveBookDTO {
	
	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(position = 2,
			example = "O Mundo Assombrado Pelos Demônios: A Ciência Vista Como uma Vela no Escuro")
	private String name;
	
	@ApiModelProperty(example = "1995", position = 3)
	private Integer publicationYear = null;
	
	@ApiModelProperty(position = 4)
	private Set<Author> authors = new HashSet<Author>();

	public RetrieveBookDTO (Book book) {
		this.id = book.getId();
		this.name = book.getName();
		this.authors = book.getAuthors();
		this.publicationYear = book.getPublicationYear();
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

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}
	
	public static Page<RetrieveBookDTO> fromBookPageToRetrieveBookDTOPage(
			Page<Book> bookPage) {
		return bookPage.map(RetrieveBookDTO::new);
	}
}
