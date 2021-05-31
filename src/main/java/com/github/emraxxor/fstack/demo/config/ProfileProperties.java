package com.github.emraxxor.fstack.demo.config;

import com.github.emraxxor.fstack.demo.storage.StorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Configuration
@ConfigurationProperties(prefix = "profile")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProfileProperties extends StorageProperties {

}
