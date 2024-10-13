package com.example.vgcodetest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unknown - Too generic!")
public class ApplicativeException extends RuntimeException{

}
