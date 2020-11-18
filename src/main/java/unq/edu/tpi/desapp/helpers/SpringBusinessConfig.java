package unq.edu.tpi.desapp.helpers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringBusinessConfig {

    public SpringBusinessConfig() {
        super();
    }
    // Nothing else to be configured here: component scanning will do everything needed
}