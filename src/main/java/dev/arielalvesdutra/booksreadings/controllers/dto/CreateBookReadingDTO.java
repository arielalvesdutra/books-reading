package dev.arielalvesdutra.booksreadings.controllers.dto;

public class CreateBookReadingDTO {
  private Long bookId;

  public CreateBookReadingDTO() {}

  public CreateBookReadingDTO(Long bookId) {
    this.bookId = bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  public Long getBookId() {
    return this.bookId;
  }
}
