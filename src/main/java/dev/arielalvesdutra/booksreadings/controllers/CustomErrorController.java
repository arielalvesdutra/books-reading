package dev.arielalvesdutra.booksreadings.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import dev.arielalvesdutra.booksreadings.error_handlers.dto.ResponseErrorDTO;
import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@RestController
public class CustomErrorController implements ErrorController {

	private final String PATH = "/error";
	
	private final boolean debug = true;
	
	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping(value = PATH)
	public ResponseErrorDTO error(HttpServletRequest request, HttpServletResponse response) {	
		
		return new ResponseErrorDTO(response.getStatus(), getErrorAttributes(request));
	}

	@Override
	public String getErrorPath() {
		return this.PATH;
	}
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		WebRequest webRequest = new ServletWebRequest(request);
	    return errorAttributes.getErrorAttributes(webRequest, this.debug);
	}
}
