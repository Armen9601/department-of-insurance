package com.departament.controller.util;

import com.departament.requestservice.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RequestGenerator {

    public static RequestDto requestGenerator() {
        return new RequestDto(UUID.fromString("06650668-a0eb-45cd-aa90-7d753685b3d4"),
                UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f6"), LocalDateTime.now());
    }

}
