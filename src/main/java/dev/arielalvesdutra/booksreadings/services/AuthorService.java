package dev.arielalvesdutra.booksreadings.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
			author = this.withValidatedBooks(author);
		}
		
		return this.authorRepository.save(author);
	}
	
	private Author withValidatedBooks(Author parameterAuthor) {
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
}
