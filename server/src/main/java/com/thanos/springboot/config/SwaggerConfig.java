package com.thanos.springboot.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Add swagger config<br/>
 * Access /{contextPath}/swagger-ui.html and /{contextPath}/v2/api-docs to use
 * {@see http://springfox.github.io/springfox/docs/current/}
 *
 * @author peiheng.zph created on 17/4/12 下午5:09
 * @version 1.0
 */
@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {
  private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

  private static final String PROPERTY_PROFILE = "profile";
  private static final String PROD_KEYWORD = "online";

  @Bean
  public Docket userApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(enableByEnv())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.thanos.springboot"))
        .paths(PathSelectors.regex("/user/.*"))
        .build()
        .apiInfo(metaApiInfo());
  }

  /**
   * Enable or disable by current environment<br/>
   * Currently only disable when in prod env
   */
  private boolean enableByEnv() {
    String profile = System.getenv(PROPERTY_PROFILE);
    boolean enable = StringUtils.isEmpty(profile) || !isProdEnv(profile);

    logger.info("Current profile is {}, set spring fox enabled {}", profile, enable);
    return enable;
  }

  private boolean isProdEnv(String profile) {
    return profile.contains(PROD_KEYWORD);
  }

  private ApiInfo metaApiInfo() {
    return new ApiInfo(
        "Spring Boot REST API",
        "Spring Boot REST API for User",
        "1.0",
        "Terms of service",
        ApiInfo.DEFAULT_CONTACT,
        "Apache License Version 2.0",
        "https://www.apache.org/licenses/LICENSE-2.0");
  }

}
