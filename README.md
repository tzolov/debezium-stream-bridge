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

### 2. Use DockerCompose to run Kafka+UI, Rabbit+UI and CDC-MySQL containers.

from project's top folder run:

```
docker-compose -f ./src/test/resources/docker-compose-demo.yaml up
```

Then you can reach the 
	- Kafka-UI at: http://localhost:8080/topics
	- RabbitMQ UI at: http://localhost:15672

## 3. Start DebeziumStreamApplication

```
./mvnw spring-boot:run
```
 
Note: the [application.properties](https://github.com/tzolov/debezium-stream-bridge/blob/main/src/main/resources/config/application.yaml) configure the CDC  for the DebeziumStreamApplication

### 3.1 Explore the results

Use the `Kafka-UI` (http://localhost:8080/topics) and `RabbitMQ-UI` (http://localhost:15672/#/queues) to explore the CDC messages.
(for Rabbit you must create a new `cdc` queue manually and binding to the `input-cdc` exchange using the `#` routing key)

Use mysql client to connect to the MySQL and alter the inventory DB. Later changes will be logged in the Kafka and RabbitMQ.

(optional) Use mysql client to connected to the database and to create a debezium user with required credentials:

```
docker exec -it mysql mysql -uroot -pdebezium inventory
```

```
mysql> show tables;
+---------------------+
| Tables_in_inventory |
+---------------------+
| addresses           |
| customers           |
| geom                |
| orders              |
| products            |
| products_on_hand    |
+---------------------+

mysql> insert into customers values (1005, "Foo", "Bar", "foo@bar.net");
```

```json
{
	"schema": {
		"type": "struct"
		"fields": "[...]"
		"optional": false
		"name": "my_app_connector.inventory.customers.Value"
	},
	"payload": {
		"id": 1005
		"first_name": "Foo"
		"last_name": "Bar"
		"email": "foo@bar.net"
		"__name": "my-app-connector"
		"__db": "inventory"
		"__table": "customers"
		"__deleted": "false"
	}
}
```

## 4. Clean

- Stop the debezium stream app
- Stop the docker compose and run: `docker-compose -f ./src/test/resources/docker-compose-demo.yaml up` to clean the container.
- Remove the `src/test/resources/zk-single-kafka-single` folder.


---
One can start a test MySQL with CDC enabled like this:

```
docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw debezium/example-mysql:1.9.6.Final
```