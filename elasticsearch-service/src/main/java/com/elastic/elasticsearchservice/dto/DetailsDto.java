package com.elastic.elasticsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailsDto {

    private UUID id;
    private Status status;
    private String passportNumber;

}
