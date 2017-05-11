# meli-jobs

This is a Java Library that provide different mechanisms to create an execute different kinds of jobs.


# Table of Contents

- [Installation](#installation)
- [Manual](#manual)
    - [Job definition](#job-definition)
    - [Examples](#examples)
    
    
### Installation

Make sure you have [Maven](https://maven.apache.org/) and [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed.

```sh
	$ git clone https://github.com/juanromerameli/meli-jobs # or clone your own fork
	$ cd meli-jobs
	$ mvn clean install
```


### Manual

#### Job definition

We have the next Job implementations

* `ChainJob`
* `ElasticJob`
* `FamilyJob`
* `GroupJob`
* `InputDataJob`
* `RestRequestJob`
* `SaveFileJob`

### Example

#### Creating a ElasticJob

```java
ExecutorJobService executorJobService = new ExecutorJobService(Executors.newSingleThreadExecutor());
ElasticJob elasticJob = new ElasticJob.Builder().name("Get Shipped Shipments").uri("an uri").query("an elastic search query").create();
Future<JobResults> future = executorJobService.execute(elasticJob);
```

#### Creating jobs to search in elastic and save in a file

```java
ExecutorJobService executorJobService = new ExecutorJobService(Executors.newSingleThreadExecutor());

ElasticJob getShipmentsJob = new ElasticJob.Builder().name("Get Shipments").uri(elasticUri).query(shippedShipmentsQuery).create();
SaveFileJob savingDataJob = new SaveFileJob.Builder().name("Save data in a File").path(Paths.get("file.txt")).create();
ChainJob chainJob = new ChainJob.Builder().name("Get Shipments & Save in a File").job(getShipmentsJob).job(savingDataJob).create();

executorJobService.execute(chainJob);
```
#### Creating and executing jobs simultaneously

```java
ExecutorJobService executorJobService = new ExecutorJobService(Executors.newSingleThreadExecutor()) ;

ElasticJob getShipmentsJob = new ElasticJob.Builder().name("Get Shipments").uri("uri").query("get shipments query").create();
ElasticJob getCheckpointsJob = new ElasticJob.Builder().name("Get Chekpointz").uri("uri").query("get checkpoints query").create();
        
JobCollection jobs = new JobCollection();
jobs.add(getShipmentsJob);
jobs.add(getCheckpointsJob);
        
executorJobService.execute(jobs);
```