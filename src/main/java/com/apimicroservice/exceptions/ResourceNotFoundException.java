package com.apimicroservice.exceptions;

public class ResourceNotFoundException extends  RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id){
        super("O código '" + id + "' não foi encontrado!!!");
    }
}