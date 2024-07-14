package org.consumerPedido.config;

import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.consumerPedido.PedidoAvro;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String KAFKA_CONTAINER_FACTORY = "kafkaContainerFactory";

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServers;

    @Value("${spring.kafka.schema-registry-url}")
    private String schemaRegistry;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offsetReset;

    @Bean
    public ConsumerFactory<String, PedidoAvro> pedidoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = KAFKA_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, PedidoAvro> pedidoConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PedidoAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pedidoConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }

}
