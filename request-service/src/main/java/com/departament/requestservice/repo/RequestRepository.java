package com.departament.requestservice.repo;

import com.departament.requestservice.entity.Request;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepository extends CouchbaseRepository<Request, UUID> {

}
