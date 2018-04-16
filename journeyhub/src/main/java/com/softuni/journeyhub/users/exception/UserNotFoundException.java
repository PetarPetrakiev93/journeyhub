package com.softuni.journeyhub.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user exists!")
public class UserNotFoundException extends RuntimeException{
}
