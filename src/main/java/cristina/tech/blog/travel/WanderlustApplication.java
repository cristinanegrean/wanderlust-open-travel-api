package cristina.tech.blog.travel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EntityScan(
        basePackageClasses = { WanderlustApplication.class, Jsr310JpaConverters.class }
)
public class WanderlustApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanderlustApplication.class, args);
    }
}
