package com.elastic.elasticsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SynthesizerDto {

    private UUID id;
    private Status status;
    private UUID reportId;
    private UUID requestId;
    private List<DetailsDto> details;

}
