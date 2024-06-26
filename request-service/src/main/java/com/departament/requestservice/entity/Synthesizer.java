package com.departament.requestservice.entity;

import com.departament.requestservice.dto.DetailsDto;
import com.departament.requestservice.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Synthesizer {

    @Id
    @GeneratedValue(strategy = UNIQUE)
    private UUID id;
    private Status status;
    private UUID reportId;
    private UUID requestId;
    private List<DetailsDto> details;

}
