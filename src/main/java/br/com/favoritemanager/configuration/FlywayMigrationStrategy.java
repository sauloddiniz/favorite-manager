package br.com.favoritemanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayMigrationStrategy {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // Executar as migrações antes de qualquer validação ou inicialização
            flyway.migrate();
        };
    }

}
