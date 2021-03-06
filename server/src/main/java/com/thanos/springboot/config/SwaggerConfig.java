package com.thanos.springboot.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
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
  private static final String BASE_PACKAGE = "com.thanos.springboot.web";

  private static final String PROPERTY_PROFILE = "profile";
  private static final String TEST_KEYWORD = "test";
  private static final String DOC_EXPANSION = "list";

  @Bean
  public UiConfiguration uiConfig() {
    return new UiConfiguration(
        null, DOC_EXPANSION, "alpha", "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, null
    );
  }

  @Bean
  public Docket userApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(enableByEnv())
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .build()
        .apiInfo(metaApiInfo());
  }

  /**
   * Enable or disable by current environment<br/>
   * Currently only disable when in prod env
   */
  private boolean enableByEnv() {
    String profile = System.getProperty(PROPERTY_PROFILE);
    boolean enable = StringUtils.isEmpty(profile) || isTestEnv(profile);

    logger.info("Current profile is {}, enable spring fox {}", profile, enable);
    return enable;
  }

  private boolean isTestEnv(String profile) {
    return profile.equalsIgnoreCase("dev") || profile.contains(TEST_KEYWORD);
  }

  private ApiInfo metaApiInfo() {
    return new ApiInfo(
        "UC API",
        "UC重构接口文档",
        "1.0",
        "Terms of service",
        ApiInfo.DEFAULT_CONTACT,
        "Apache License Version 2.0",
        "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
  }
}
