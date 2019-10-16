package dev.arielalvesdutra.booksreadings.controllers.dto;

public class CreateBookReadDTO {
  private Long bookId;

  public CreateBookReadDTO() {}

  public CreateBookReadDTO(Long bookId) {
    this.bookId = bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  public Long getBookId() {
    return this.bookId;
  }
}
