package com.rpgstats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpgstats.exceptions.ModelException;

import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.messages.ErrorResponse;
import com.rpgstats.messages.ItemNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ModelException.class)
    void handleModelException(HttpServletResponse response, Exception exception) throws IOException {
        sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConflictDataException.class)
    void handleConflictData(HttpServletResponse response, Exception exception) throws IOException {
        sendResponse(response, HttpServletResponse.SC_CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    void handleNotFound(HttpServletResponse response, Exception exception)  throws IOException {
        sendResponse(response, HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
    }

    void sendResponse(HttpServletResponse response, int status, String errorMsg) throws IOException {
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            bw.write(mapper.writeValueAsString(new ErrorResponse(errorMsg)));
        }
    }
}
