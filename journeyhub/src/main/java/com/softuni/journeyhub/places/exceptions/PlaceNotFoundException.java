package com.softuni.journeyhub.places.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such place exists!")
public class PlaceNotFoundException extends RuntimeException{
}
