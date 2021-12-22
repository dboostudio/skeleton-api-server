package studio.dboo.api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.Example;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
//@EnableSwagger2
public class Swagger2Config {

    // http://localhost:8080/swagger-ui/index.html

    @Bean
    public Docket api(){
        List<Response> responseList = new ArrayList<>();


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()

                .useDefaultResponseMessages(true);
    }

    private ApiInfo apiInfo() {
        String description = "API Doc";
        return new ApiInfoBuilder()
                .title("API")
                .version("0.0.1")
                .description(description)
                .build();
    }
}
