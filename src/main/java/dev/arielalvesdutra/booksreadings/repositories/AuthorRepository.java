package dev.arielalvesdutra.booksreadings.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dev.arielalvesdutra.booksreadings.entities.Author;

@Transactional
@Repository
public class AuthorRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Author create(Author author) {
		this.em.persist(author);
		return author;
	}
	
	public void deleteById(Long id) {
		Author authoToDelete = this.find(id);
		
		this.em.remove(authoToDelete);
	}

	public Author find(Long id) {
		return this.em.find(Author.class, id);
	}
	
	public List<Author> findAll() {		
		return this.em.createQuery("select a from Author a", Author.class)
				.getResultList();
	}

	public Author update(Long id, Author parameterAuthor) {
		Author existingAuthor = this.find(id);
		
		existingAuthor.setEmail(parameterAuthor.getEmail());
		existingAuthor.setName(parameterAuthor.getName());
		
		return existingAuthor;
	}
}
