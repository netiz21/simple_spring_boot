package com.thanos.springboot.common;

/**
 * Auto detect runtime spring profile
 *
 * @author solarknight created on 17/7/25 上午11:43
 * @version 1.0
 */
public class SpringProfileDetector {

  private static final String SPRING_PROFILES_FROM_PROPERTY = "spring.profiles.active";
  private static final String SPRING_PROFILES_FROM_ENV = "SPRING_PROFILES_ACTIVE";
  private static final String PROPERTY_PROFILE = "profile";
  private static final String PROPERTY_PROFILE_UPPER = "PROFILE";
  private static final String DEFAULT_PROFILE = "dev";

  public static String detectProfile() {
    String profile = getFromProfile();

    if (profile == null) {
      profile = getFromSpringBoot();
    }

    if (profile == null) {
      profile = DEFAULT_PROFILE;
    }

    return profile;
  }

  private static String getFromProfile() {
    String profile = System.getProperty(PROPERTY_PROFILE);
    if (profile == null) {
      return System.getProperty(PROPERTY_PROFILE_UPPER);
    }
    return profile;
  }

  private static String getFromSpringBoot() {
    String profile = System.getProperty(SPRING_PROFILES_FROM_PROPERTY);
    if (profile == null) {
      return System.getenv(SPRING_PROFILES_FROM_ENV);
    }
    return profile;
  }
}
