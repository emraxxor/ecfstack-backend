package hu.emraxxor.fstack.demo.config;

import lombok.SneakyThrows;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * 
 * @author Attila Barna
 *
 */
@Configuration
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Value("${spring.data.elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${spring.data.elasticsearch.port}")
    private String elasticSearchPort;

    
    @Bean
    @NonNull
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration 
            = ClientConfiguration.builder()
                .connectedTo(elasticSearchHost + ":" + elasticSearchPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
    
    
    @Override
    @NonNull
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(DateToStringConverter.INSTANCE, StringToDateConverter.INSTANCE));
    }

    @WritingConverter
    enum DateToStringConverter implements Converter<LocalDateTime, String> {
        INSTANCE;
    	
        @Override
        public String convert(LocalDateTime date) {
            return date.format(formatter);
        }
    }

    @ReadingConverter
    enum StringToDateConverter implements Converter<String, LocalDateTime> {
        INSTANCE;
    	
    	
        @Override
        @SneakyThrows
        public LocalDateTime convert(@NonNull String s) {
			return LocalDateTime.parse(s, formatter);
        }
    }


}
