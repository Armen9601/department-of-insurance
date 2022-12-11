package com.department.requestservice.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;
import org.testcontainers.couchbase.CouchbaseService;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@Testcontainers
public class CouchbaseConfig {

    static private final String couchbaseBucketName = "mybucket";
    static private final String username = "user";
    static private final String password = "couchabse";
    private static final BucketDefinition bucketDefinition = new BucketDefinition(couchbaseBucketName);
    private static final DockerImageName COUCHBASE_IMAGE_ENTERPRISE = DockerImageName.parse("couchbase:enterprise")
            .asCompatibleSubstituteFor("couchbase/server")
            .withTag("6.0.1");

    static CouchbaseContainer container = new CouchbaseContainer(COUCHBASE_IMAGE_ENTERPRISE)
            .withCredentials(username, password)
            .withBucket(bucketDefinition)
            .withEnabledServices(CouchbaseService.QUERY)
            .withEnabledServices(CouchbaseService.INDEX)
            .withEnabledServices(CouchbaseService.SEARCH)
            .withEnabledServices(CouchbaseService.ANALYTICS)
            .withStartupTimeout(Duration.ofSeconds(90))
//            .withExposedPorts(8091, 8092, 8093, 8094, 11207, 11210, 11211, 18091, 18092, 18093)
            .waitingFor(Wait.forHealthcheck());


    @BeforeAll
    public static void setup() {
        container.start();

    }

    @AfterAll
    public static void teardown() {
        container.stop();
    }

}
