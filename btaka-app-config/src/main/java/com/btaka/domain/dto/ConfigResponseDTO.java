package com.btaka.domain.dto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigResponseDTO {

    private boolean success;
    private String error;
    private String errorMessage;
    private int statusCode;
    private Map<String ,Object> configs;

    private ConfigResponseDTO(Builder builder) {
        this.success = builder.success;
        this.error = builder.error;
        this.errorMessage = builder.errorMessage;
        this.statusCode = builder.statusCode;
        this.configs = builder.configs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean success = true;
        private String error;
        private String errorMessage;
        private int statusCode = 200;
        private final Map<String ,Object> configs = new ConcurrentHashMap<>();

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder set(ConfigDTO configDTO) {
            configs.put(configDTO.getKey(), configDTO.getValue());
            return this;
        }

        public Builder set(List<ConfigDTO> configDTOS) {
            configDTOS.forEach(dto -> configs.put(dto.getKey(), dto.getValue()));
            return this;
        }

        public ConfigResponseDTO build() {
            return new ConfigResponseDTO (this);
        }
    }
}
