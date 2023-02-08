package com.example.sintezatorservice.dto;

import com.example.sintezatorservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import java.util.UUID;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SynthesizerDto {

    private UUID id;
    private Status status;
    private UUID reportId;
    private UUID requestId;

}
