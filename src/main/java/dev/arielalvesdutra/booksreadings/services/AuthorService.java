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
		return this.authorRepository.save(author);
	}
	
	public void deleteById(Long id) {
		Author author = this.find(id);

		this.authorRepository.delete(author);
	}

	public Author find(Long id) {
		return this.authorRepository.findById(id).get();
	}

	public List<Author> findAll() {
		return this.authorRepository.findAll();
	}
	
	public List<Author> findAllByIds(List<Long> ids) {
		return this.authorRepository.findAllById(ids);
	}

	public Author update(Long id, Author parameterAuthor) {
		
		Author existingAuthor = this.find(id);
		
		existingAuthor.setEmail(parameterAuthor.getEmail());
		existingAuthor.setName(parameterAuthor.getName());
		this.authorRepository.save(existingAuthor);

		return existingAuthor;
	}
}
