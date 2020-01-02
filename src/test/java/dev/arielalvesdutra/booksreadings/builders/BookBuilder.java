package dev.arielalvesdutra.booksreadings.builders;

import dev.arielalvesdutra.booksreadings.entities.Book;

public class BookBuilder {
	
	private Book book = new Book();
	
	public BookBuilder withId(Long id) {
		this.book.setId(id);
		return this;
	}
	
	public BookBuilder withName(String name) {
		this.book.setName(name);
		return this;
	}
	
	public BookBuilder withPublicationYear(Integer publicationYear) {
		this.book.setPublicationYear(publicationYear);
		return this;
	}
	
	public Book build() {	
		try {
			return this.book.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
