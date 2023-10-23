package com.xclonebackend.xclone.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException {
    public IncorrectVerificationCodeException() {
        super("The code provided did not match the users verification code");
    }
}
