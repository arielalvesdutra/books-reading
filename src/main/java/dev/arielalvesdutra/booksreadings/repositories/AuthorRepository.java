package dev.arielalvesdutra.booksreadings.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	List<Author> findAllById(Iterable<Long> ids);
	
	Page<Author> findAllByBooks_Id(Long bookId, Pageable pagination);
}
