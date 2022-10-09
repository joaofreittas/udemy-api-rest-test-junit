package br.com.udemy.apijunit.application.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

}
