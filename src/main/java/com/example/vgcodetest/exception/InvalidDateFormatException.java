package com.example.vgcodetest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Date Format. Please use yyyy-MM-dd!")
public class InvalidDateFormatException extends ApplicativeException {

}
