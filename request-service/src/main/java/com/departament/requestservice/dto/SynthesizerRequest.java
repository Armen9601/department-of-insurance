package com.departament.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynthesizerRequest {

    private RequestDto requestDto;
    private ReportDto reportDto;

}
