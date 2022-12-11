package com.example.thirdpartyagent.mapper;

import com.example.thirdpartyagent.dto.ReportDto;
import com.example.thirdpartyagent.model.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDto, Report> {


}
