package dev.arielalvesdutra.booksreadings.converters;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ObjectToJsonString {

	/**
	 * Converte Objeto para String formatada como JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
