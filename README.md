## Run the demo

### 1. Build debezium-boot-starter locally

Clone & Build the https://github.com/tzolov/debezium-boot-starter locally. This will register the following deps in your local maven repo:

```xml
</depencency>
	<groupId>com.logaritex.cdc.core</groupId>
	<artifactId>debezium-boot-starter</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</depencency>
```

### 2. Use DockerCompose to run Kafka, KafkaUI and MySQL-CDC

From within the `src/test/resources` folder run:

```
docker-compose -f ./docker-compose-demo.yaml up
```

Then you can reach the Kafka-UI at: http://localhost:8080/topics

## Run DebeziumStreamApplication

```
 ./mvnw spring-boot:run
 ```
 
Note: the [application.properties](https://github.com/tzolov/debezium-stream-bridge/blob/main/src/main/resources/application.properties) configure the CDC  for the DebeziumStreamApplication





---
One can start a test MySQL with CDC enabled like this:

```
docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw debezium/example-mysql:1.9.6.Final
```