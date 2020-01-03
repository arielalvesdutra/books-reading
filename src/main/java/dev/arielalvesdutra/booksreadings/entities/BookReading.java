package dev.arielalvesdutra.booksreadings.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.arielalvesdutra.booksreadings.entities.enums.ReadingStatus;

@Entity
public class BookReading implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "book_id")
	@ManyToOne
	@JsonIgnore
	private Book book;

	@JoinColumn(name = "reader_id")
	@ManyToOne
	@JsonIgnore
	private User reader;
	
	@Enumerated(EnumType.STRING)
	private ReadingStatus readingStatus = ReadingStatus.RESERVED;
	
	@OneToMany(mappedBy = "bookReading", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<Comment>();

	public BookReading() {}

	public BookReading(Book book) {
		this.book =  book;
	}
	
	public BookReading(Book book, User reader) {
		this.book =  book;
		this.reader = reader;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		comment.setBookReading(this);
		this.getComments().add(comment);
	}
	
	public void removeComment(Comment comment) {
		this.comments.remove(comment);
	}
	
	public ReadingStatus getReadingStatus() {
		return readingStatus;
	}

	public void setReadingStatus(ReadingStatus readingStatus) {
		this.readingStatus = readingStatus;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	@Override
	public String toString() {
		return "[ id: " + this.getId() 
						+ ", status: " + this.getReadingStatus()
						+ ", bookName: " + this.getBook().getName() +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((reader == null) ? 0 : reader.hashCode());
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
		BookReading other = (BookReading) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (reader == null) {
			if (other.reader != null)
				return false;
		} else if (!reader.equals(other.reader))
			return false;
		return true;
	}
}
