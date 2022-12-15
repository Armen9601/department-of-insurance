package com.example.sintezatorservice.mapper;

import com.example.sintezatorservice.dto.SynthesizerDto;
import com.example.sintezatorservice.model.Synthesizer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SynthesizerMapper extends EntityMapper<SynthesizerDto, Synthesizer> {


}
