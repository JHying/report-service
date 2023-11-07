package tw.hyin.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import tw.hyin.java.utils.config.SwaggerCreater;

/**
 * @author H-yin on 2021.
 * @Description ./swagger-ui/#/
 */
@Configuration
@EnableOpenApi //取代@EnableSwagger2
@Import(SpringDataRestConfiguration.class)//取得 spring-data-rest 自動產生的 API
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return SwaggerCreater.createSwaggerDefault("報表下載 DEMO", "1.0");
    }

}
