package dev.arielalvesdutra.booksreadings.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "1", required = true, position = 1)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(example = "Iniciando a leitura.", required = true, position = 2)
	private String content;
	
	@ApiModelProperty(required = true, position = 3)
	private OffsetDateTime timestamp;
	
	@ManyToOne
	@JsonIgnoreProperties("comments")
	@JoinColumn(name = "book_reading_id")
	@JsonIgnore
	private BookReading bookReading;
	
	public Comment() {}

	public Comment(String content, OffsetDateTime now) {
		this.content = content;
		this.timestamp = now;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public BookReading getBookReading() {
		return bookReading;
	}

	public void setBookReading(BookReading bookReading) {
		this.bookReading = bookReading;
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
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[ id: " + this.getId() 
				+ ", content: " + this.getContent()
				+ ", timestamp: " + this.getTimestamp() + "]";
	}
}
