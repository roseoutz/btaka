package com.btaka.common.dto;

import com.btaka.common.constant.Operation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SearchParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pagePerSize;
    private int currentPage;
    private Operation operation;
    private Map<String, String> paramMap = new HashMap<>();

    public SearchParam() {
    }

    public SearchParam(int pagePerSize, int currentPage, Map<String, String> paramMap) {
        this.pagePerSize = pagePerSize;
        this.currentPage = currentPage;
        this.operation = Operation.EQUAL;
        this.paramMap.putAll(paramMap);
    }

    public SearchParam(int pagePerSize, int currentPage, Operation operation, Map<String, String> paramMap) {
        this.pagePerSize = pagePerSize;
        this.currentPage = currentPage;
        this.operation = operation;
        this.paramMap.putAll(paramMap);
    }

    private SearchParam(Builder builder) {
        this.pagePerSize = builder.pagePerSize;
        this.currentPage = builder.currentPage;
        this.operation = builder.operation;
        this.paramMap.putAll(builder.paramMap);
    }

    public int getPagePerSize() {
        return this.pagePerSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public String getOperationValue() {
        return this.operation.getValue();
    }

    public String getParam(String key) {
        return this.paramMap.getOrDefault(key, "");
    }

    public Map<String, String> getParamMap() {
        return this.paramMap;
    }

    public static class Builder {
        private int pagePerSize;
        private int currentPage;
        private Operation operation;
        private final Map<String, String> paramMap = new HashMap<>();

        public Builder pagePerSize(int pagePerSize) {
            this.pagePerSize = pagePerSize;
            return this;
        }

        public Builder currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder param(String key, String value) {
            this.paramMap.put(key, value);
            return this;
        }

        public Builder operation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public Builder param(Map<String, String> paramMap) {
            this.paramMap.putAll(paramMap);
            return this;
        }

        public SearchParam build() {
            return new SearchParam(this);
        }
    }

}
