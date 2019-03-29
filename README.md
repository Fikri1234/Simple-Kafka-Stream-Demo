# Simple-Kafka-Stream-Demo

1. Open cmd
2. Execute zkserver to run zookeeper server
3. Go to directory ${Kafka_Home}
4. Open cmd
5. Execute .\bin\windows\kafka-server-start.bat .\config\server.properties to run Broker server
6. Open cmd in the same directory to create streams-input as topic for REST and streams-output as topic for Kafka-Streams
7. Execute .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-input
8. Execute .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-output
9. To see data was sent from REST
10. Execute .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-input --from-beginning --consumer-property group.id=streams-consumer-group
11. Open cmd again in the same directory to see message has been streams from kafka
12. Execute .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-output --from-beginning  --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
13. Hit http//localhost:8091/api/topic/streams-input/producer/"your message"
