package dev.arielalvesdutra.booksreadings.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.BookReading;

public interface BookReadingRepository extends JpaRepository<BookReading, Long>{

  BookReading findByIdAndReader_Id(Long id, Long userId);
  
  Page<BookReading> findAllByReader_Id(Long userId, Pageable pagination);
}
