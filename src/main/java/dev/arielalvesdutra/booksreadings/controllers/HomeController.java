package dev.arielalvesdutra.booksreadings.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "Home", description = "Home")
public class HomeController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String index() {
		
		return "Seja bem vindo à aplicação Leitura de Livros!";
	}
}
