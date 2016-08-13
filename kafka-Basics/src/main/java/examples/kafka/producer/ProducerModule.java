package examples.kafka.producer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * 
 *
 */
public class ProducerModule {
	Properties properties;
	final static Logger logger = Logger.getLogger(ProducerModule.class);

	public static void main(String[] args) throws IOException {

		Integer linenumber = 1;

		// Log4J Needs a properties file , what to do ??
		// Either you set a log4j in classpath(resource folder) or use
		// BasicConfigurator
		// BasicConfigurator.configure();

		logger.debug("Fetching inputstream for properties file");
		InputStream producerpropertiesfile = ProducerModule.class.getClassLoader().getResourceAsStream("producer.properties");
		Properties properties = new Properties();
		logger.debug("Loading Properties file");
		properties.load(producerpropertiesfile);
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		BufferedReader br = new BufferedReader(new FileReader(new File("/home/hadoop/Documents/input.json")));
		logger.debug("Reading the input file");

		String data = br.readLine();
		while (data != null) {
			logger.debug("Line number is " + linenumber.toString());
			logger.debug("data is " + data.toString());
			producer.send(new ProducerRecord<String, String>("fast-messages", linenumber.toString(), data));
			linenumber++;
			data = br.readLine();
			producer.flush();
		}
		br.close();
		producer.close();
	}
}
