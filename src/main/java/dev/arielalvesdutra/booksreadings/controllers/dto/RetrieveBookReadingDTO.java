package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.Comment;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.entities.enums.ReadingStatus;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveBookReadingDTO {
	
	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(position = 2)
	private ReadingStatus readingStatus;
	
	@ApiModelProperty( position = 3)
	private Book book;
	
	@ApiModelProperty(position = 4)
	private User reader;
	
	@ApiModelProperty(position = 5)
	private List<Comment> comments;
	
	public RetrieveBookReadingDTO(BookReading bookReading) {
		this.id = bookReading.getId();
		this.readingStatus = bookReading.getReadingStatus();
		this.reader = bookReading.getReader();
		this.comments = bookReading.getComments();
		this.book = bookReading.getBook();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReadingStatus getReadingStatus() {
		return readingStatus;
	}

	public void setReadingStatus(ReadingStatus readingStatus) {
		this.readingStatus = readingStatus;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public static Page<RetrieveBookReadingDTO> fromBookReadingPageToRetriveBookReadingDTOPage(
			Page<BookReading> bookReadingPage) {
		
		return bookReadingPage.map(RetrieveBookReadingDTO::new);
	}
}
