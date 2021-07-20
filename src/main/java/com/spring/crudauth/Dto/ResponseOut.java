package com.spring.crudauth.Dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOut {
    private Map<String, Object> data;

    @JsonProperty("api_status")
    private ApiStatusOut apistatus;


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ApiStatusOut getApistatus() {
        return apistatus;
    }

    public void setApistatus(ApiStatusOut apistatus) {
        this.apistatus = apistatus;
    }
}
