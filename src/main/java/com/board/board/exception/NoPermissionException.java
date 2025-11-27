package com.board.board.exception;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message){
        super(message);
    }
}
