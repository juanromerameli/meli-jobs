package com.meli.job;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.meli.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * An Elastic Job is an implementation for a Job that
 * made a query request to an ElasticSearch service.
 * <p>
 * Using the <p>uri</p> and <p>query</p> the job makes
 * a request to the service and save the results.
 * <p>
 *
 * @author Juan Manuel Romera Ferrio
 */
public class ElasticJob extends Job {

    private Logger log = LoggerFactory.getLogger(ElasticJob.class);
    private String query;
    private URI uri;
    private RestTemplate restTemplate;

    protected ElasticJob(String name, String query, URI uri, RestTemplate restTemplate) {
        super(name);
        this.query = query;
        this.uri = uri;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void execute() {
        this.log.info("Execute Query " + this.query);
        ResponseEntity<JsonObject> response;

        try {
            response = restTemplate.postForEntity(uri, query, JsonObject.class);
        } catch (RestClientException e) {
            log.error("Error in request", e);
            return;
        }

        List<Object> result = new ArrayList<>();
        if (response != null && response.getBody() != null) {
            result = parseResponse(response.getBody());
        }

        JobResults results = new JobResults(result);
        setJobResults(results);

    }

    private List<Object> parseResponse(JsonObject body) {
        List<Object> result = new ArrayList<>();
        JsonArray jsonArray = body.getAsJsonObject("hits").getAsJsonArray("hits");
        jsonArray.forEach(
                jsonElement -> {
                    JsonObject item = jsonElement.getAsJsonObject().getAsJsonObject("_source");
                    result.add(item);
                }
        );

        return result;
    }

    public static class Builder {
        private String query;
        private String name;
        private URI uri;
        private RestTemplate restTemplate;

        public Builder() {
            this.restTemplate = new RestTemplate();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder uri(URI uri) {
            this.uri = uri;
            return this;
        }

        public Builder restTemplate(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
            return this;
        }

        public ElasticJob create() {

            Preconditions.checkNullOrEmpty(name, "Name can't be null or empty");
            Preconditions.checkNullOrEmpty(query, "Query can't be null or empty");
            Preconditions.checkNotNull(uri, "Uri can't be null");


            return new ElasticJob(this.name, this.query, this.uri, this.restTemplate);
        }
    }
}
