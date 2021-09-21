package app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "controllers", "services", "request.sources" })
@EnableJpaRepositories("repositories")
@EntityScan("dto")
@EnableTransactionManagement
public class AppConfig {

}