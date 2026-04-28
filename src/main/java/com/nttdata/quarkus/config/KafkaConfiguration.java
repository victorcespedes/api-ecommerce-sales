package com.nttdata.quarkus.config;

import com.nttdata.quarkus.avro.VentaEvent;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.Properties;


@Singleton
public class KafkaConfiguration {

    @ConfigProperty(name = "application.kafka-values.server")
    String bootstrapServers;

    @ConfigProperty(name = "application.kafka-values.schema")
    String bootstrapSchema;

    @Produces
    @Named("producer")
    public Producer<String, VentaEvent> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(
                AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                bootstrapSchema
        );
        props.put(
                AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS,
                true
        );
        props.put(
                AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION,
                true
        );
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        return new KafkaProducer<>(props);
    }

    @Produces
    @Named("consumer")
    public Consumer<String, VentaEvent> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(
                AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                bootstrapSchema
        );
        props.put(
                AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS,
                true
        );
        props.put(
                AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION,
                true
        );
        props.putIfAbsent(ConsumerConfig.GROUP_ID_CONFIG,
                "venta-topic-cg-01");
        props.putIfAbsent(ConsumerConfig.CLIENT_ID_CONFIG,
                "venta-topic-client" + "-" + getHostname());
        props.putIfAbsent(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        props.putIfAbsent(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                false);
        props.putIfAbsent(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                500);
        props.putIfAbsent(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
                true);

        return new KafkaConsumer<>(props);
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Exception";
        }
    }

}
