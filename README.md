# Simple-Kafka-Stream-Demo

Open cmd
Execute zkserver to run zookeeper server
Go to directory ${Kafka_Home}
Open cmd
Execute .\bin\windows\kafka-server-start.bat .\config\server.properties to run Broker server
Open cmd in the same directory to create streams-input as topic for REST and streams-output as topic for Kafka-Streams
Execute .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-input
Execute .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-output
To see data was sent from REST
Execute .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-input --from-beginning --consumer-property group.id=streams-consumer-group
Open cmd again in the same directory to see message has been streams from kafka
Execute .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-output --from-beginning  --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
Hit http//localhost:8091/api/topic/streams-input/producer/"your message"
