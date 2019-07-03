package com.agi.masterUserService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserInGroupException extends RuntimeException {
    public  UserInGroupException (String s){
        super(s);
    }
}
