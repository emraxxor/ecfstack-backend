package hu.emraxxor.fstack.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import hu.emraxxor.fstack.demo.storage.StorageProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Configuration
@ConfigurationProperties(prefix = "picture")
@Data
@EqualsAndHashCode(callSuper = false)
public class PictureStorageProperties extends StorageProperties {

}
