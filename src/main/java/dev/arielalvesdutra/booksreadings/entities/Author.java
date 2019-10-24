package dev.arielalvesdutra.booksreadings.entities;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Author implements Serializable, Cloneable {
	
	private final String emailPattern = ".+@.+\\.[a-z]+";
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "1" ,position = 1)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(example = "Nome do autor" ,position = 2)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "email@exemplo.com" ,position = 3)
	@NotBlank
	@Pattern(regexp = emailPattern)
	private String email;
	
	@JsonIgnoreProperties("authors")
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="author_book",
	 inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName= "id"),
	 joinColumns = @JoinColumn(name="book_id", referencedColumnName = "id"))
	private Set<Book> books = new HashSet<Book>();	

	public Author() {}

	public Author(String name, String email) {
		this.setName(name);
		this.setEmail(email);
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
		if (email.matches(this.emailPattern)) {
			this.email = email;
			return;
		}
		
		throw new InvalidParameterException("Email inválido.");
	}

	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "[ name: "+ this.getName() 
				+", email: "+ this.getEmail()
				+", id:"  + this.getId() 
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Author other = (Author) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Boolean hasBooks() {
		if (this.getBooks().isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public Author clone() throws CloneNotSupportedException {
		return (Author) super.clone();
	}
}
