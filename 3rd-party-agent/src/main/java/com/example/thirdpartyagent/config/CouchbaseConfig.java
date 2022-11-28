package com.example.thirdpartyagent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "localhost:11210";
    }

    @Override
    public String getUserName() {
        return "user";
    }

    @Override
    public String getPassword() {
        return "couchbase";
    }

    @Override
    public String getBucketName() {
        return "report";
    }
}