package dev.arielalvesdutra.booksreadings.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.BookRead;

public interface BookReadRepository extends JpaRepository<BookRead, Long>{
}
