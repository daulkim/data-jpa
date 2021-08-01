package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

/**
 * @EnableJpaAuditing(modifyOnCreate = false)
 * -> 처음 등록 시 modified는 null로 들어감
 */
@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 실제로는 security 적용시 session에서 id꺼내와서 저장
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
