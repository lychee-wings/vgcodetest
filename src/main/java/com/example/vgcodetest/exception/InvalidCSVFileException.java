package com.example.vgcodetest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid CSV file!")
public class InvalidCSVFileException extends ApplicativeException {

}
