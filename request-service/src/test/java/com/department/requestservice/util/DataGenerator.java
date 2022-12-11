package com.department.requestservice.util;

import com.departament.requestservice.dto.RequestDto;

import java.util.UUID;

public class DataGenerator {

    public static RequestDto generatorRequestDtoData() {
        return RequestDto.builder()
                .applicantId(UUID.fromString("06650668-a0eb-45cd-aa90-7d753685b3d4"))
                .id(UUID.fromString("06650668-a0eb-45cd-aa90-7d753685b3d7"))
                .createdDate(null)
                .build();
    }
}