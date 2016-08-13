package com.example;

import java.io.IOException;

import examples.kafka.consumer.ConsumerModule;
import examples.kafka.producer.ProducerModule;

public class Driver {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Must have either 'producer' or 'consumer' as argument");
		}
		switch (args[0]) {
		case "producer":
			ProducerModule.main(args);
			break;
		case "consumer":
			ConsumerModule.main(args);
			break;
		default:
			throw new IllegalArgumentException("Don't know how to do " + args[0]);
		}
	}
}
