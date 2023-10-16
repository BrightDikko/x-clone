package com.xclonebackend.xclone.exceptions;

public class EmailFailedToSendException extends RuntimeException {

    public EmailFailedToSendException() {
        super("The email failed to send");
    }
}
