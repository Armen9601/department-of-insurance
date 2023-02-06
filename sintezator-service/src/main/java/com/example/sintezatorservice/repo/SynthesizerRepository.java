package com.example.sintezatorservice.repo;

import com.example.sintezatorservice.model.Synthesizer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SynthesizerRepository extends ReactiveCouchbaseRepository<Synthesizer, UUID> {

}
