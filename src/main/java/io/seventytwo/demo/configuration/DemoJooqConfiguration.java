package io.seventytwo.demo.configuration;

import org.jooq.conf.RenderNameCase;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoJooqConfiguration {

    @Bean
    public DefaultConfigurationCustomizer configurationCustomizer() {
        return context -> context.settings()
                .withRenderNameCase(RenderNameCase.LOWER);
    }
}
