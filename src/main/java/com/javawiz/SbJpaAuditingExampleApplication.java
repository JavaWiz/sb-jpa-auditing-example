package com.javawiz;

import com.javawiz.entity.Article;
import com.javawiz.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
@Slf4j
public class SbJpaAuditingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbJpaAuditingExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ArticleRepository repository) {
        return (args) -> {
            Article article = Article.builder().content("test article").build();
            repository.save(article);

            Optional<Article> optArticle = repository.findById(article.getId());
            Article loadedArticle = optArticle.get();
            log.info("{}", optArticle.get());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            loadedArticle.setContent("modified content");
            repository.save(loadedArticle);

            log.info("{}", repository.findById(loadedArticle.getId()).get());
        };
    }
}
