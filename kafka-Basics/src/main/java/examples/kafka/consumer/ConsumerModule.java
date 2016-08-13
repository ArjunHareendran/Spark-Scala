package examples.kafka.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.beans.NameAndAge;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import examples.kafka.producer.ProducerModule;

public class ConsumerModule {

	Properties consumerproperties;
	final static Logger logger = Logger.getLogger(ConsumerModule.class);

	public static void main(String args[]) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		// Log4J Needs a properties file , what to do ??
		// Either you set a log4j in classpath(resource folder) or use
		// BasicConfigurator
		// BasicConfigurator.configure();

		InputStream consumerpropertiesfile = ProducerModule.class.getClassLoader()
				.getResourceAsStream("consumer.properties");
		Properties properties = new Properties();
		properties.load(consumerpropertiesfile);

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

		// List out all the topics that you want to read
		consumer.subscribe(Arrays.asList("fast-messages"));
		ObjectMapper objectMapper = new ObjectMapper();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
				NameAndAge data = objectMapper.readValue(record.value(), NameAndAge.class);
				System.out.println("The Name is  = " + data.getName());
			    System.out.println("The Age is = " + data.getAge());
		 	
			}
		}
	}

}
