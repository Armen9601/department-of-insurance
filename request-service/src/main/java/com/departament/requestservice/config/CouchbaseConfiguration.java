package com.departament.requestservice.config;

import com.departament.requestservice.entity.Synthesizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import java.util.Collections;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.couchbase.connection-string}")
    private String connection;

    @Value("${spring.couchbase.username}")
    private String username;

    @Value("${spring.couchbase.password}")
    private String password;

    @Value("${spring.data.couchbase.bucket-name}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return this.connection;
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getBucketName() {
        return this.bucketName;
    }


    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping mapping) {

        try {
            mapping.mapEntity(Synthesizer.class, getCouchbaseTemplate("synthesizer"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CouchbaseTemplate getCouchbaseTemplate(String bucketName) throws Exception {

        CouchbaseTemplate template = new CouchbaseTemplate(couchbaseClientFactory(bucketName),
                mappingCouchbaseConverter(couchbaseMappingContext(customConversions()),
                        new CouchbaseCustomConversions(Collections.emptyList())));
        template.setApplicationContext(applicationContext);
        return template;
    }

    private CouchbaseClientFactory couchbaseClientFactory(String bucketName) {

        return new SimpleCouchbaseClientFactory(couchbaseCluster(couchbaseClusterEnvironment()), bucketName, this.getScopeName());
    }
}