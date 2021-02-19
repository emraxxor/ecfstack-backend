package hu.emraxxor.fstack.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "profile")
@Data
public class ProfileProperties {
	private String storage;
}
