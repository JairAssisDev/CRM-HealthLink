package br.edu.ifpe.CRMHealthLink.controller.exception;

public class IncorrectInputException extends RuntimeException{

    public IncorrectInputException(String message) {
        super(message);
    }
}
