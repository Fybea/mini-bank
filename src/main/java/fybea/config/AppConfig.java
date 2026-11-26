package fybea.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("fybea")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
