package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import io.swagger.annotations.ApiModelProperty;

public class UpdateBookDTO {
	
	@ApiModelProperty(example = "Sapiens: Uma Breve História da Humanidade", required = true, position = 1)
	@NotEmpty
	@Size(min = 5)
	private String name;
	
	@ApiModelProperty(example = "2019", required = true, position = 2)
	@NotNull
	private Integer publicationYear;
	
	@ApiModelProperty(notes = "Array de IDs dos autores", required = false, position = 3)
	private List<Integer> authorsIds = new ArrayList<Integer>();
	
	public UpdateBookDTO() {}
	
	public UpdateBookDTO(String name, Integer publicationYear) {
		
		this.name = name;
		this.publicationYear = publicationYear;
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

	public List<Integer> getAuthorsIds() {
		return authorsIds;
	}

	public void setAuthorsIds(List<Integer> authorsIds) {
		this.authorsIds = authorsIds;
	}
	
	public Book toBook() {
		Book book = new Book(this.getName(), this.getPublicationYear());
		book.setAuthors(this.withAuthors());
		
		return book;
	}

	private Set<Author> withAuthors() {
		Set<Author> authors = new HashSet<Author>();
		
		for (Integer id: this.getAuthorsIds()) {
			Author author = new Author();
			author.setId((long)id);
			authors.add(author);
		}
		
		return authors;
	}
}
