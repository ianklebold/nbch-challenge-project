package com.nbch.challenge.app.security.constructors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleGrantedAuthorityJsonConstructor {
    @JsonCreator
    public SimpleGrantedAuthorityJsonConstructor(@JsonProperty("authority") String role) {

    }
}
