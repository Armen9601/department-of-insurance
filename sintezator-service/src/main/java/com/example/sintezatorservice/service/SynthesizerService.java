package com.example.sintezatorservice.service;

import com.example.sintezatorservice.dto.SynthesizerRequest;

public interface SynthesizerService {

    void acceptedRequestForProcessing(SynthesizerRequest synthesizerRequest);

}
