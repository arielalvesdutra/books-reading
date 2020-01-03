package dev.arielalvesdutra.booksreadings.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Book implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(example = "1" ,position = 1)
	private Long id;
	
	@ApiModelProperty(example = "Nome do livro" ,position = 2)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "2001" ,position = 3)
	@NotNull
	private Integer publicationYear;
	
	@ManyToMany
	@JoinTable(name="author_book",
	 joinColumns = @JoinColumn(name="book_id", referencedColumnName= "id"),
	 inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName = "id"))
	@JsonIgnore
	private Set<Author> authors = new HashSet<Author>();

	@JsonIgnore
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BookReading> booksReadings = new HashSet<BookReading>();
	
	public Book() {}

	public Book(String name, Integer publicationYear) {
		this.setName(name);
		this.setPublicationYear(publicationYear);               
	}

	@JsonIgnore
	public Set<BookReading> getBooksReadings() {
		return this.booksReadings;
	}

	public void setBooksReadings(Set<BookReading> readings) {
		this.booksReadings = readings;
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

	public Set<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}
	
	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	@Override
	public String toString() {
		return "[ id: " + this.getId()
				+ ", name: "+ this.getName()
				+ ", authors: " + this.getAuthors()
				+ ", year: " + this.getPublicationYear()
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
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Boolean hasAuthors() {
		if (this.getAuthors().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public Book clone() throws CloneNotSupportedException {
		return (Book)super.clone();
	}
}
