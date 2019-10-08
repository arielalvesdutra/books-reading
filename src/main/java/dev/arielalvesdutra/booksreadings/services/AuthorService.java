package dev.arielalvesdutra.booksreadings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.repositories.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	public Author create(Author author) {
		return this.authorRepository.create(author);
	}
	
	public void deleteById(Long id) {
		this.authorRepository.deleteById(id);
	}

	public Author find(Long id) {
		return this.authorRepository.find(id);
	}

	public List<Author> findAll() {
		return this.authorRepository.findAll();
	}

	public Author update(Long id, Author author) {
		return this.authorRepository.update(id, author);
	}
}
