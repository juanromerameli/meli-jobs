package com.meli.job;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

/**
 * An RestRequestJob is an implementation for a Job that
 * made a get request to a service
 * <p>
 * Using the <p>uri</p> the job makes
 * a request to the service and save the results.
 * <p>
 *
 * @author Juan Manuel Romera Ferrio
 */
public class RestRequestJob extends Job {

    private Logger log = LoggerFactory.getLogger(RestRequestJob.class);
    private URI uri;
    private RestTemplate restTemplate = new RestTemplate();

    protected RestRequestJob(String name, URI uri) {
        super(name);
        this.uri = uri;
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected void execute() {
        ResponseEntity<JsonObject> response;

        try {
            response = restTemplate.getForEntity(uri, JsonObject.class);
        } catch (RestClientException e) {
            log.error("Error in request", e);
            return;
        }

        if (response != null && response.getBody() != null) {
            JobResults results = new JobResults(Arrays.asList(response.getBody()));
            setJobResults(results);
        }
    }

    public static class Builder {
        private String name;
        private URI uri;

        public Builder() {

        }

        public RestRequestJob.Builder name(String name) {
            this.name = name;
            return this;
        }

        public RestRequestJob.Builder uri(URI uri) {
            this.uri = uri;
            return this;
        }

        public RestRequestJob create() {
            return new RestRequestJob(this.name, this.uri);
        }
    }
}
