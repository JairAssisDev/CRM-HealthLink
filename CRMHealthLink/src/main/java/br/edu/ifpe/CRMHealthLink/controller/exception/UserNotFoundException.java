package br.edu.ifpe.CRMHealthLink.controller.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String identifier){
        super(String.format("User [%s] not found",identifier));
    }
}
