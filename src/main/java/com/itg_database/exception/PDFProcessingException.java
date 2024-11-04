package com.itg_database.exception;

public class PDFProcessingException extends Exception{
    public PDFProcessingException(String message, Throwable cause){
        super(message, cause);
    }
    public PDFProcessingException(String message) {
        super(message);
    }
}
