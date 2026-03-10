package com.example.sehetak_bethakaa.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
         super("Unauthorized: API key is missing or invalid" );
    }
}
