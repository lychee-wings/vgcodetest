package com.example.vgcodetest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Time period [from] MUST before [to]")
public class InvalidTimePeriodException extends ApplicativeException {

}
