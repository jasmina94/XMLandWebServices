package com.ftn.model.environment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by JELENA on 25.6.2017.
 */
@Component
@ConfigurationProperties(prefix = "environment")
@Data
public class EnvironmentProperties {

    private String pib;

    private String bankUrl;
}
