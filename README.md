
- Make sure that the https://github.com/tzolov/debezium-boot-starter is cloned and build locally.

- Start testMySQL with CDC enabled:

```
docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw debezium/example-mysql:1.9.6.Final
```

- Start Kafka + Kowl

From within the `src/test/resources` folder run:

```
docker-compose -f ./docker-compose-kafka.yaml up
```

Then you can reach the Kafka ui at: http://localhost:8080/topics

- Run the DebeziumStreamApplication application.