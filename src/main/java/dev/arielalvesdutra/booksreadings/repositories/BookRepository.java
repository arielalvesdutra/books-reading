package dev.arielalvesdutra.booksreadings.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dev.arielalvesdutra.booksreadings.entities.Book;

@Transactional
@Repository
public class BookRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Book create(Book book) {
		this.em.persist(book);
		return book;
	}

	public void deleteById(Long id) {
		Book bookToDelete = this.find(id);

		this.em.remove(bookToDelete);
	}

	public Book find(Long id) {
		return this.em.find(Book.class, id); 
	}
	
	public List<Book> findAll() {
		return this.em.createQuery("select b from Book b", Book.class)
				.getResultList();
	}

	public Book update(Long id, Book parameterBook) {
		Book existingBook =  this.find(id);
		
		existingBook.setName(parameterBook.getName());
		
		return existingBook;
	}
}
