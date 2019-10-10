package dev.arielalvesdutra.booksreadings.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
