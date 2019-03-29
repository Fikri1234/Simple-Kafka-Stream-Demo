package com.example.project.Configuration;

import java.util.Arrays;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamsConfiguration {
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
	
	private KafkaStreams kafkaStreams;
	
	String outputTopic = "streams-output";
	String inputTopic = "streams-input";
	final Serde<String> stringSerde = Serdes.String();
	final Serde<Long> longSerde = Serdes.Long();
	
	@Bean
    public KafkaStreams kafkaStreams(KafkaProperties kafkaProperties,
                                     @Value("${spring.application.name}") String appName) {
        final Properties props = new Properties();

        // inject SSL related properties
        props.putAll(kafkaProperties.getSsl().buildProperties());
        props.putAll(kafkaProperties.getProperties());
        
        // stream config centric ones
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appName);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, stringSerde.getClass().getName());
        props.put(StreamsConfig.STATE_DIR_CONFIG, "Simple-Kafka-Streams-Demo-Log");

        kafkaStreams = new KafkaStreams(kafkaStreamTopology(), props);
        kafkaStreams.start();

        return kafkaStreams;
    }

    @Bean
    public Topology kafkaStreamTopology() {
    	
    	// build stream and fetching data
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> textLines = streamsBuilder.stream(inputTopic, Consumed.with(stringSerde, stringSerde));

        KTable<String, Long> wordCounts = textLines
            .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
            .groupBy((key, value) -> value)
            .count();

        wordCounts.toStream().to(outputTopic, Produced.with(stringSerde, longSerde));
        
        return streamsBuilder.build();
    }
    
    @PreDestroy
    public void closeStream() {
    	kafkaStreams.close();
    }

}
