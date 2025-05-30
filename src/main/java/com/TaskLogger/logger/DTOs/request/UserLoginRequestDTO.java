package com.TaskLogger.logger.DTOs.request;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
