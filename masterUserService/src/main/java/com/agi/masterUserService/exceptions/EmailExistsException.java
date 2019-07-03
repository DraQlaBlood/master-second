package com.agi.masterUserService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String s) {
        super(s);
    }

}
