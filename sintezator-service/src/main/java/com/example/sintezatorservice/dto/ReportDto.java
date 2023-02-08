package com.example.sintezatorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    private UUID id;
    private UUID createdFrom;
    private String agentName;

}
