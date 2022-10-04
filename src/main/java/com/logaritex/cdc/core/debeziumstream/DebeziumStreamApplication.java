/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logaritex.cdc.core.debeziumstream;

import java.util.function.Consumer;

import io.debezium.engine.ChangeEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DebeziumStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebeziumStreamApplication.class, args);
	}

	@Bean
	public Consumer<ChangeEvent<String, String>> mySourceRecordConsumer(StreamBridge streamBridge) {
		return new Consumer<ChangeEvent<String, String>>() {
			@Override
			public void accept(ChangeEvent<String, String> changeEvent) {
				System.out.println("[CDC Event]: " + changeEvent.toString());
				String body = changeEvent.value();
				streamBridge.send("mySourceRecordConsumer-in-1", body);
			}
		};
	}
}
