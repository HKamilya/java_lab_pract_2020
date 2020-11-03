package ru.itis.javalab.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Cookie {
    String value;

    public Cookie(String value) {
        this.value = value;
    }
}
