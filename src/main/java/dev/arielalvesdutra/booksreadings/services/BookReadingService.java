package dev.arielalvesdutra.booksreadings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.Comment;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.BookReadingRepository;
import dev.arielalvesdutra.booksreadings.repositories.CommentRepository;

@Service
public class BookReadingService {
	
	@Autowired
	private BookReadingRepository bookReadingRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	public Page<BookReading> findAll(Pageable pagination) {
		return this.bookReadingRepository.findAll(pagination);
	}
	
	public Page<BookReading> findAllByUserId(Long userId, Pageable pagination) {
		return this.bookReadingRepository.findAllByReader_Id(userId, pagination);
	}

	public BookReading find(Long bookReadingId) {
		return this.bookReadingRepository
				.findById(bookReadingId)
				.orElseThrow(() -> 
				new EntityNotFoundException("Leitura com ID "+ bookReadingId +" não encontrada"));
	}

	public Comment addComment(Long bookReadingid, Comment comment) {
		BookReading bookReading = this.find(bookReadingid);
		
		comment.setBookReading(bookReading);		
		Comment createdComment = this.commentRepository.save(comment);
		
		return createdComment;		
	}
	
	public BookReading create(BookReading bookReading) {
		return this.bookReadingRepository.save(bookReading);
	}

	public Comment findComment(Long bookReadingId, Long commentId) {

		Comment comment = this.commentRepository.findByIdAndBookReading_Id(commentId, bookReadingId);
		
		if (comment == null) {
			throw new EntityNotFoundException("Comentário com ID "+ commentId 
					+ " da Leitura com ID "+ bookReadingId + " não encontrado");
		}
		
		return comment;
	}
		
	public Page<Comment> findComments(Long bookReadingId, Pageable pagination) {

		return this.commentRepository.findAllByBookReading_Id(bookReadingId, pagination);
	}

	public void deleteComment(Long bookReadingId, Long commentId) {
		BookReading bookReading = this.find(bookReadingId);
		Comment comment = this.findComment(bookReadingId, commentId);
		
		bookReading.removeComment(comment);
		this.bookReadingRepository.save(bookReading);
	}

	public BookReading findByIdAndUserId(Long bookReadingId, Long userId) {
		BookReading bookReading = this.bookReadingRepository.findByIdAndReader_Id(bookReadingId, userId);
		
		if (bookReading == null) {
			throw new EntityNotFoundException("Leitura de ID " 
							+ bookReadingId + " do usuário de ID "
							+ userId + " Não encontrada");
		}
		
		return bookReading;
	}
	
	public BookReading update(Long bookReadingId, BookReading parameterBookReading, Long userId) {
		BookReading bookReading = this.find(bookReadingId);
		
		bookReading.setReadingStatus(parameterBookReading.getReadingStatus());
		this.bookReadingRepository.save(bookReading);
		
		return bookReading;
	}
}
