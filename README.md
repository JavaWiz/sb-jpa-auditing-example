### Spring Data JPA - Implementing Basic Auditing by using @CreatedDate and @LastModifiedDate
Spring Data provides following annotations to keep track of who created or changed an entity and when the change happened.
* @CreatedDate
* @LastModifiedDate
* @CreatedBy
* @LastModifiedBy

1. This example shows the use of first two annotations. We have to do following things:
In entity class, create date fields and annotate them with `@CreatedDate` and `@LastModifiedDate`. The types of these fields can be type `Joda-Time`, `DateTime`, legacy Java Date and Calendar, JDK8 date and time types, and long or Long.

2. Use following on our entity class.
```
@EntityListeners(AuditingEntityListener.class)
```
`@EntityListeners` is JPA specific annotation.
`AuditingEntityListener.class` is Spring Data specific class which implements the listener to capture auditing information on persisting and updating entities.

3. Annotate our configuration class with @EnableJpaAuditing which enables annotation based auditing.

### Entity
```
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateModified;
}
```

### Repository
```
public interface ArticleRepository extends CrudRepository<Article, Long> {}
```

### Java Configuration
```
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {}
```
After all this configuration, once we run our app we can see in log,
```
2023-10-17T20:20:23.340+05:30  INFO 16224 --- [           main] c.j.SbJpaAuditingExampleApplication      : Article(id=1, content=test article, dateCreated=2023-10-17T20:20:23.259392, dateModified=2023-10-17T20:20:23.259392)
2023-10-17T20:20:25.374+05:30  INFO 16224 --- [           main] c.j.SbJpaAuditingExampleApplication      : Article(id=1, content=modified content, dateCreated=2023-10-17T20:20:23.259392, dateModified=2023-10-17T20:20:25.355639)
```