package com.btaka.config;

import lombok.RequiredArgsConstructor;
import com.btaka.board.common.converter.StringToLocalDateTimeConverter;
import com.btaka.board.common.converter.LocalDateTimeToStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Configuration
@EnableReactiveMongoRepositories(basePackages = {"com.btaka"})
@EnableReactiveMongoAuditing
public class MongoConfig {

    private final MongoMappingContext mongoMappingContext;

    @Bean
    public MappingMongoConverter reactiveMappingMongoConverter() {
        MappingMongoConverter converter = new MappingMongoConverter(ReactiveMongoTemplate.NO_OP_REF_RESOLVER,
                mongoMappingContext);

        // 핵심은 이 부분으로, '_class' 필드를 제거하는 설정이다.
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    /*
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(stringLocalDateTimeConverter(), localDateTimeStringConverter()));
    }


     */
    @Bean
    public Converter<String, LocalDateTime> stringLocalDateTimeConverter() {
        return new StringToLocalDateTimeConverter();
    }

    @Bean
    public Converter<LocalDateTime, String> localDateTimeStringConverter() {
        return new LocalDateTimeToStringConverter();
    }
}
