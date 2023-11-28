package com.codetest.jwtstudy.config;

import com.codetest.jwtstudy.domain.users.Users;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String REFERENCE = "Bearer 토큰 값";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .securityContexts(List.of(securityContext()))
            .securitySchemes(List.of(bearerAuthSecurityScheme()))
            .ignoredParameterTypes(Users.class)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("jwt-study")
            .version("1.0.0")
            .description("jwt-study")
            .build();
    }

    private SecurityContext securityContext() {
        return springfox.documentation
            .spi.service.contexts
            .SecurityContext
            .builder()
            .securityReferences(defaultAuth())
            .operationSelector(operationContext -> true)
            .build();
    }

    private List defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference(REFERENCE, authorizationScopes));
    }


    private HttpAuthenticationScheme bearerAuthSecurityScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name(REFERENCE).build();
    }

}
