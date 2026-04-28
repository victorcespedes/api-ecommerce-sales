package com.nttdata.quarkus.kafka;

import com.nttdata.quarkus.avro.VentaEvent;
import com.nttdata.quarkus.dto.kafka.VentaDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class VentaProducer {


    @ConfigProperty(name = "application.kafka-values.topic")
    String topicKafka;


    @Inject
    @Named("producer")
    Producer<String, VentaEvent> producer;

    public void sendVenta(VentaDto request){

        VentaEvent event = new VentaEvent(request.saleId(), request.costumerId(),
                request.productId(), request.status());

        ProducerRecord producerRecord =  new ProducerRecord<>(
                topicKafka, event);
        producer.send(producerRecord);

    }

}
