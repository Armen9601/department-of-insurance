package com.example.thirdpartyagent.model;

import lombok.AllArgsConstructor;
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
@Document
public class Report {

    @Id
    @GeneratedValue(strategy = UNIQUE)
    private UUID id;
    private UUID createdFrom;
    private String agentName;

}
