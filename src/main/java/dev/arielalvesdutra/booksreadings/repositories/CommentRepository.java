package dev.arielalvesdutra.booksreadings.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Comment findByIdAndBookReading_Id(Long commentId, Long bookReadingId);
	
	Page<Comment> findAllByBookReading_Id(Long bookReadingId, Pageable pagination);
}
