package com.meli.job;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by jromera on 12/5/17.
 */
public class ElasticJobTest {


    @Test
    public void createJob() {
        ElasticJob elasticJob = null;

        try {
            elasticJob = new ElasticJob.Builder().name("test").uri(new URI("http://www.google.com")).query("query").create();
        } catch (URISyntaxException e) {
            assert false;
        }

        assertNotNull(elasticJob);
    }

    @Test
    public void createJobWithErrors() {
        ElasticJob elasticJob = null;

        //without name
        try {
            elasticJob = new ElasticJob.Builder().uri(new URI("http://www.google.com")).query("query").create();
        } catch (URISyntaxException e) {
            assert false;
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name can't be null or empty");
        }

        assertNull(elasticJob);

        //without uri
        try {
            elasticJob = new ElasticJob.Builder().name("test").query("query").create();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Uri can't be null");
        }

        assertNull(elasticJob);

        //without query
        try {
            elasticJob = new ElasticJob.Builder().name("test").uri(new URI("http://www.google.com")).create();
        } catch (URISyntaxException e) {
            assert false;
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Query can't be null or empty");
        }

        assertNull(elasticJob);
    }


    @Test
    public void executeJob() {

        URI uri = null;
        try {
            uri = new URI("http://www.google.com");
        } catch (URISyntaxException e) {
            assert false;
        }

        String query = "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"shipment.substatus\":\"picked_up\"}},{\"term\":{\"shipment.status\":\"ready_to_ship\"}},{\"term\":{\"shipment.site_id\":\"MLA\"}},{\"range\":{\"shipment.date_created\":{\"gt\":\"now-1d/d\",\"lt\":\"now/d\"}}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":2,\"sort\":[],\"facets\":{}}";

        JsonParser parser = new JsonParser();
        JsonObject body = parser.parse("{\"took\":75,\"timed_out\":false,\"_shards\":{\"total\":107,\"successful\":107,\"failed\":0},\"hits\":{\"total\":542,\"max_score\":13.95294,\"hits\":[{\"_index\":\"shipments\",\"_type\":\"shipment\",\"_id\":\"26185279912\",\"_score\":13.95294,\"_source\":{\"id\":26185279912,\"mode\":\"me2\",\"created_by\":\"receiver\",\"order_id\":1372127250,\"order_cost\":2250,\"site_id\":\"MLA\",\"status\":\"ready_to_ship\",\"substatus\":\"picked_up\",\"status_history\":{\"date_cancelled\":null,\"date_delivered\":null,\"date_first_visit\":null,\"date_handling\":\"2017-05-11T18:24:37.000-04:00\",\"date_not_delivered\":null,\"date_ready_to_ship\":\"2017-05-11T18:24:37.000-04:00\",\"date_shipped\":null,\"date_returned\":null},\"date_created\":\"2017-05-11T18:24:27.000-04:00\",\"last_updated\":\"2017-05-12T09:23:54.000-04:00\",\"tracking_number\":\"26185279912\",\"tracking_method\":\"Colecta Normal\",\"service_id\":311,\"carrier_info\":{\"pickup_list_id\":null},\"sender_id\":93477946,\"sender_address\":{\"id\":154533462,\"address_line\":\"Tapalque 6700\",\"street_name\":\"Tapalque\",\"street_number\":\"6700\",\"comment\":\"esquina cañada de gomez\",\"zip_code\":\"1440\",\"city\":{\"id\":null,\"name\":\"Mataderos\"},\"state\":{\"id\":\"AR-C\",\"name\":\"Capital Federal\"},\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"},\"neighborhood\":{\"id\":null,\"name\":null},\"municipality\":{\"id\":null,\"name\":null},\"agency\":null,\"types\":[\"billing\",\"default_selling_address\",\"shipping\"],\"latitude\":-34.66,\"longitude\":-58.51,\"geolocation_type\":\"RANGE_INTERPOLATED\"},\"receiver_id\":254469595,\"receiver_address\":{\"id\":270359105,\"address_line\":\"Belgrano 849\",\"street_name\":\"Belgrano\",\"street_number\":\"849\",\"comment\":null,\"zip_code\":\"2424\",\"city\":{\"id\":null,\"name\":\"devoto\"},\"state\":{\"id\":\"AR-X\",\"name\":\"Córdoba\"},\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"},\"neighborhood\":{\"id\":null,\"name\":null},\"municipality\":{\"id\":null,\"name\":null},\"agency\":null,\"types\":null,\"latitude\":null,\"longitude\":null,\"geolocation_type\":null,\"receiver_name\":\"Santiago Dittrich\",\"receiver_phone\":\"3564337625\"},\"shipping_items\":[{\"id\":\"MLA617263530\",\"description\":\"Juego Optica Fiat Palio Siena 2001 2002 2003 2004 2005 2006\",\"dimensions\":\"30.0x30.0x30.0,3000.0\",\"quantity\":1}],\"shipping_option\":{\"id\":407725365,\"shipping_method_id\":73328,\"name\":\"Normal a domicilio\",\"currency_id\":\"ARS\",\"cost\":289.99,\"list_cost\":289.99,\"estimated_handling_limit\":{\"date\":\"2017-05-12T00:00:00.000-03:00\"},\"estimated_delivery_final\":{\"date\":\"2017-06-19T00:00:00.000-03:00\"},\"estimated_delivery_limit\":{\"date\":\"2017-06-05T00:00:00.000-03:00\"},\"estimated_delivery_extended\":{\"date\":\"2017-05-24T00:00:00.000-03:00\"},\"estimated_delivery_time\":{\"type\":\"known\",\"date\":\"2017-05-19T00:00:00.000-03:00\",\"unit\":\"hour\",\"offset\":{\"date\":null,\"shipping\":null},\"time_frame\":{\"from\":null,\"to\":null},\"pay_before\":null,\"shipping\":120,\"handling\":24}},\"comments\":null,\"date_first_printed\":\"2017-05-12T07:02:14.000-04:00\",\"market_place\":\"MELI\",\"return_details\":null,\"type\":\"forward\",\"application_id\":null,\"return_tracking_number\":null,\"cost_components\":{\"special_discount\":0,\"loyal_discount\":0,\"compensation\":0},\"picking_type\":\"cross_docking\",\"logistic_type\":\"cross_docking\",\"substatus_history\":[{\"substatus\":\"picked_up\",\"status\":\"ready_to_ship\",\"date\":\"2017-05-12T09:23:43.000-04:00\"}],\"delay\":[]}},{\"_index\":\"shipments\",\"_type\":\"shipment\",\"_id\":\"26184993388\",\"_score\":13.95294,\"_source\":{\"id\":26184993388,\"mode\":\"me2\",\"created_by\":\"receiver\",\"order_id\":1371668584,\"order_cost\":650,\"site_id\":\"MLA\",\"status\":\"ready_to_ship\",\"substatus\":\"picked_up\",\"status_history\":{\"date_cancelled\":null,\"date_delivered\":null,\"date_first_visit\":null,\"date_handling\":\"2017-05-11T10:49:04.000-04:00\",\"date_not_delivered\":null,\"date_ready_to_ship\":\"2017-05-11T10:49:05.000-04:00\",\"date_shipped\":null,\"date_returned\":null},\"date_created\":\"2017-05-11T09:46:24.000-04:00\",\"last_updated\":\"2017-05-12T09:39:39.000-04:00\",\"tracking_number\":\"26184993388\",\"tracking_method\":\"Colecta Normal\",\"service_id\":311,\"carrier_info\":{\"pickup_list_id\":null},\"sender_id\":19009036,\"sender_address\":{\"id\":101232131,\"address_line\":\"Scalabrini Ortiz 561\",\"street_name\":\"Scalabrini Ortiz\",\"street_number\":\"561\",\"comment\":\"Referencia Porton Negro Entre Entre Acevedo y Aguirre\",\"zip_code\":\"1414\",\"city\":{\"id\":null,\"name\":\"Villa Crespo\"},\"state\":{\"id\":\"AR-C\",\"name\":\"Capital Federal\"},\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"},\"neighborhood\":{\"id\":null,\"name\":null},\"municipality\":{\"id\":null,\"name\":null},\"agency\":null,\"types\":[\"billing\",\"default_buying_address\",\"default_selling_address\",\"shipping\"],\"latitude\":-34.6,\"longitude\":-58.44,\"geolocation_type\":\"APPROXIMATE\"},\"receiver_id\":57467770,\"receiver_address\":{\"id\":10023527,\"address_line\":\"COCHINOCA 384\",\"street_name\":\"COCHINOCA\",\"street_number\":\"384\",\"comment\":null,\"zip_code\":\"4610\",\"city\":{\"id\":null,\"name\":\"PERICO\"},\"state\":{\"id\":\"AR-Y\",\"name\":\"Jujuy\"},\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"},\"neighborhood\":{\"id\":null,\"name\":null},\"municipality\":{\"id\":null,\"name\":null},\"agency\":null,\"types\":null,\"latitude\":null,\"longitude\":null,\"geolocation_type\":null,\"receiver_name\":\"JUAN VIRGILIO TORREJON\",\"receiver_phone\":\"3885841433\"},\"shipping_items\":[{\"id\":\"MLA647464331\",\"description\":\"Carry Disk Dock Station Nisuta Discos Ide Y Sata Usb 2.0\",\"dimensions\":\"10.0x18.0x25.0,250.0\",\"quantity\":1}],\"shipping_option\":{\"id\":442591849,\"shipping_method_id\":73328,\"name\":\"Normal a domicilio\",\"currency_id\":\"ARS\",\"cost\":0,\"list_cost\":229.99,\"estimated_handling_limit\":{\"date\":\"2017-05-12T00:00:00.000-03:00\"},\"estimated_delivery_final\":{\"date\":\"2017-06-16T00:00:00.000-03:00\"},\"estimated_delivery_limit\":{\"date\":\"2017-06-02T00:00:00.000-03:00\"},\"estimated_delivery_extended\":{\"date\":\"2017-05-23T00:00:00.000-03:00\"},\"estimated_delivery_time\":{\"type\":\"known\",\"date\":\"2017-05-18T00:00:00.000-03:00\",\"unit\":\"hour\",\"offset\":{\"date\":null,\"shipping\":null},\"time_frame\":{\"from\":null,\"to\":null},\"pay_before\":null,\"shipping\":96,\"handling\":24}},\"comments\":null,\"date_first_printed\":\"2017-05-11T11:34:04.000-04:00\",\"market_place\":\"MELI\",\"return_details\":null,\"type\":\"forward\",\"application_id\":null,\"return_tracking_number\":null,\"cost_components\":{\"special_discount\":0,\"loyal_discount\":0,\"compensation\":0},\"picking_type\":\"cross_docking\",\"logistic_type\":\"cross_docking\",\"substatus_history\":[{\"substatus\":\"picked_up\",\"status\":\"ready_to_ship\",\"date\":\"2017-05-12T09:37:56.000-04:00\"}],\"delay\":[]}}]}}").getAsJsonObject();
        ResponseEntity<JsonObject> response = new ResponseEntity<>(body, HttpStatus.OK);

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForEntity(uri, query, JsonObject.class)).thenReturn(response);


        ElasticJob elasticJob = new ElasticJob.Builder().name("test").uri(uri).query(query).restTemplate(restTemplate).create();
        List<Object> result = elasticJob.call().getData();

        assertEquals(result.size(), 2);
        assertTrue(result.get(0) instanceof JsonObject);
        assertTrue(result.get(1) instanceof JsonObject);
    }

    @Test
    public void executeJobWithErrors() {
        URI uri = null;
        try {
            uri = new URI("http://www.google.com");
        } catch (URISyntaxException e) {
            assert false;
        }

        String query = "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"shipment.substatus\":\"picked_up\"}},{\"term\":{\"shipment.status\":\"ready_to_ship\"}},{\"term\":{\"shipment.site_id\":\"MLA\"}},{\"range\":{\"shipment.date_created\":{\"gt\":\"now-1d/d\",\"lt\":\"now/d\"}}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":2,\"sort\":[],\"facets\":{}}";
        JsonObject body = new JsonObject();

        ResponseEntity<JsonObject> response = new ResponseEntity<>(body, HttpStatus.OK);

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForEntity(uri, query, JsonObject.class)).thenReturn(response);

        ElasticJob elasticJob = new ElasticJob.Builder().name("test").uri(uri).query(query).restTemplate(restTemplate).create();


        boolean error = false;
        JobResults result = null;
        try {
            result = elasticJob.call();
        } catch (Exception e) {
            error = true;
        }

        assertTrue(error);
        assertNull(result);
    }

}
