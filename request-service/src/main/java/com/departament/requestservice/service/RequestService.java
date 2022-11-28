package com.departament.requestservice.service;

import com.departament.requestservice.dto.ReportDto;
import com.departament.requestservice.dto.RequestDto;

import java.util.UUID;

public interface RequestService {

    RequestDto add(RequestDto requestDto);

    RequestDto findById(UUID id);

    RequestDto update(RequestDto requestDto);

    void delete(UUID id);

    ReportDto reportByApplicantId(UUID id);

}
