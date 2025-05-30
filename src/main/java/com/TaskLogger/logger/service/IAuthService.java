package com.TaskLogger.logger.service;

import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import com.TaskLogger.logger.DTOs.request.UserLoginRequestDTO;
import com.TaskLogger.logger.DTOs.request.UserRegisterRequestDTO;

public interface IAuthService {
     String registerUser(UserRegisterRequestDTO userRegisterRequestDTO);
     UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO);
}
