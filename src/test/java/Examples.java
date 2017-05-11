import com.meli.job.*;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by jromera on 9/5/17.
 */
public class Examples {

    private URI elasticUri = new URI("http://www.google.com");
    private String shippedShipmentsQuery = "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"shipment.status\":\"shipped\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";

    private ExecutorJobService executorJobService = new ExecutorJobService(Executors.newSingleThreadExecutor());


    public Examples() throws URISyntaxException {
    }

    @Test
    public void createElasticJob() {
        try {
            ElasticJob getShipmentsJob = new ElasticJob.Builder().name("Get Shipments").uri(elasticUri).query(shippedShipmentsQuery).create();
            executorJobService.execute(getShipmentsJob).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createElasticJobAndSaveInFile() {
        try {
            ElasticJob getShipmentsJob = new ElasticJob.Builder().name("Get Shipments").uri(elasticUri).query(shippedShipmentsQuery).create();
            SaveFileJob savingDataJob = new SaveFileJob.Builder().name("Save data in a File").path(Paths.get("file.txt")).create();
            ChainJob chainJob = new ChainJob.Builder().name("Get Shipments & Save in a File").job(getShipmentsJob).job(savingDataJob).create();

            executorJobService.execute(chainJob).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAndExecuteJobsSimultaneously() {
        try {

            ElasticJob getShipmentsJob = new ElasticJob.Builder().name("Get Shipments").uri(elasticUri).query("get shipments query").create();
            ElasticJob getCheckpointsJob = new ElasticJob.Builder().name("Get Checkpoints").uri(elasticUri).query("get checkpoints query").create();

            JobCollection jobs = new JobCollection();
            jobs.add(getShipmentsJob);
            jobs.add(getCheckpointsJob);

            executorJobService.execute(jobs);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
