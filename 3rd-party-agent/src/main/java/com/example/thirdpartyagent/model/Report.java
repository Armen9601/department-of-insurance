package com.example.thirdpartyagent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Document
public class Report {

    //    @Id
//    @GeneratedValue(strategy = UNIQUE)
    private UUID id;
    private UUID createdFrom;
    private String agentName;

}
