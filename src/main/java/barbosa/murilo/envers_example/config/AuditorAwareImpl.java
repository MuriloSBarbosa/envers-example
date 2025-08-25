package barbosa.murilo.envers_example.config;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditorAwareImpl {

    @Bean
    AuditorAware<String> auditorProvider() {
        // In a real app, get from SecurityContext
        return () -> Optional.of("user@example.com");
    }
}
