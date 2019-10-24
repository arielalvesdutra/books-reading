package dev.arielalvesdutra.booksreadings.unit.entities;

import java.util.HashSet;
import java.util.Set;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;

public class AuthorBuilder {
	
	public Author author = new Author();
	
	public AuthorBuilder withName(String name) {
		this.author.setName(name);
		return this;
	}
	
	public AuthorBuilder withEmail(String email) {
		this.author.setEmail(email);
		return this;
	}
	
	public AuthorBuilder withId(Long id) {
		this.author.setId(id);
		return this;
	}
	
	public AuthorBuilder withBook(Book book) {
		Set<Book> books = new HashSet<Book>();
		books.add(book);
		
		this.author.setBooks(books);
		return this;
	}	
	
	public Author build() {
		try {
			return this.author.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

