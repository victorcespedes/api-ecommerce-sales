package com.nttdata.quarkus.kafka;

import com.nttdata.quarkus.avro.VentaEvent;
import com.nttdata.quarkus.dto.kafka.VentaDto;
import com.nttdata.quarkus.repository.VentaRepository;
import com.nttdata.quarkus.service.VentaService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class VentaConsumer {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @ConfigProperty(name = "application.kafka-values.topic")
    String topicKafka;

    @Inject
    @Named("consumer")
    Consumer<String, VentaEvent> consumer;
    boolean running = true;

    @Inject
    VentaService ventaService;

    public void initialize(@Observes StartupEvent event) {
        consumer.subscribe(Collections.singletonList(topicKafka));
        executor.submit(() -> {
            while (running) {
                try {
                    var records = consumer.poll(Duration.ofSeconds(1));
                    if (!records.isEmpty()) {
                        records.forEach(record ->
                                executor.submit(() -> {
                                    processRecord(record);
                                }));
                    }
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        });
    }

    public void processRecord(ConsumerRecord<String, VentaEvent> record) {
        var ventaEvent = record.value();
        VentaDto venta = new VentaDto(ventaEvent.getSaleId(),
                ventaEvent.getCostumerId(),
                ventaEvent.getProductId(),
                ventaEvent.getStatus());
        ventaService.confirmingVenta(venta);
    }

}
