package dev.arielalvesdutra.booksreadings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class BooksReadingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksReadingsApplication.class, args);
	}
}
