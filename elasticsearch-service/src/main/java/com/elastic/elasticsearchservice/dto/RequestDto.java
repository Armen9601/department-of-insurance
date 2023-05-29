package com.elastic.elasticsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {

    private UUID id;
    private UUID applicantId;
    private LocalDateTime createdDate;
    private SynthesizerDto synthesizer;

}
