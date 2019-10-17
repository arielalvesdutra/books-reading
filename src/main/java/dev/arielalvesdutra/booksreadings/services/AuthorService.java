package dev.arielalvesdutra.booksreadings.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	public Author create(Author author) {
		
		if (author.hasBooks()) {
			author = this.authorWithValidatedBooks(author);
		}
		
		return this.authorRepository.save(author);
	}

	public void deleteById(Long id) {
		Author author = this.find(id);
		
		this.authorRepository.delete(author);
	}

	public Author find(Long id) {
		return this.authorRepository
					.findById(id).
					orElseThrow(() -> 
					new EntityNotFoundException("Author com ID "+ id +" não encontrado"));
	}

	public List<Author> findAll() {
		return this.authorRepository.findAll();
	}

	public List<Author> findAll(String name) {
		Example<Author> authorExample 
		                  = this.getExampleToFindContaingNameCaseInsensitive(name);

		return this.authorRepository.findAll(authorExample);
	}

	public Page<Author> findAll(String name, Pageable pagination) {
		Example<Author> authorExample 
		                  = this.getExampleToFindContaingNameCaseInsensitive(name);

		return this.authorRepository.findAll(authorExample, pagination);
	}	
	
	public List<Author> findAllByIds(List<Long> ids) {
		List<Author> authors = new ArrayList<Author>();
		
		for (Long id: ids) {
			authors.add(this.find(id));
		}
		
		return authors;
	}

	public Author update(Long id, Author parameterAuthor) {
		
		Author existingAuthor = this.find(id);
		
		existingAuthor.setEmail(parameterAuthor.getEmail());
		existingAuthor.setName(parameterAuthor.getName());
		this.authorRepository.save(existingAuthor);

		return existingAuthor;
	}

	private Example<Author> getExampleToFindContaingNameCaseInsensitive(String name) {
		Author author = new Author();
		author.setName(name);
		Example<Author> authorExample = Example.of(author, ExampleMatcher.matchingAny()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

		return authorExample;
	}

	private Author authorWithValidatedBooks(Author parameterAuthor) {
		try {
			Set<Book> validatedBooks = new HashSet<Book>();
			Author author = parameterAuthor.clone();
			
			for (Book book: author.getBooks()) {
				validatedBooks.add(this.bookService.find(book.getId()));
			}
			
			author.setBooks(validatedBooks);
			
			return author;
			
		} catch (CloneNotSupportedException e) {		
			throw new RuntimeException(e.getMessage());
		}		
	}
}
