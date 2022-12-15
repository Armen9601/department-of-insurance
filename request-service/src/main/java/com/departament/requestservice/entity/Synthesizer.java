package com.departament.requestservice.entity;

import com.departament.requestservice.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Synthesizer {

    private UUID id;
    private Status status;
    private UUID reportId;
    private UUID requestId;

}
