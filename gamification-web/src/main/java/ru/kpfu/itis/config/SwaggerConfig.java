package ru.kpfu.itis.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.util.Constant;

/**
 * Created by timur on 25.06.15.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(new ApiInfo(
                        "API Gamification",
                        "Примеры запросов. API v1",
                        "Terms..",
                        "JetBrains email here",
                        "please read the license terms...",
                        "jetBrains.kpfu.ru"))
                .includePatterns(Constant.API_URI_PREFIX + "/.*", "/api-docs/.*");
    }

}
