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
	
	public RetrieveAuthorDTO() {}

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

	@Override
	public String toString() {
		return "RetrieveAuthorDTO [id=" + id 
				+ ", name=" + name 
				+ ", email=" + email 
				+ ", books=" + books + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetrieveAuthorDTO other = (RetrieveAuthorDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
