package com.thanos.springboot.common.demo.lambda;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.Data;
import lombok.val;

import static java.util.stream.Collectors.toList;

/**
 * Field access demo that looks more like natural language
 * {@see https://dzone.com/articles/field-access-interfaces-for-more-readable-java-stream-code}
 *
 * @author peiheng.zph created on 18/2/9 上午11:34
 * @version 1.0
 */
public class FieldAccessDemo {

  private static final int STREAM_COUNT = 100;

  @Data
  class Person {
    private String name;
  }

  public static List<Person> autoCompleteByName(String term, int limit) {
    val allPersons = EnhancedRandom.randomListOf(STREAM_COUNT, Person.class);
    val found = allPersons.stream()
        .filter(person -> person.getName().startsWith(term))
        .limit(limit)
        .collect(toList());
    return found;
  }

  public static List<Person> autoCompleteByName1(String term, int limit) {
    val allPersons = EnhancedRandom.randomListOf(STREAM_COUNT, Person.class);
    val found = allPersons.stream()
        .filter(personName.startsWith(term))
        .limit(limit)
        .collect(toList());
    return found;
  }


  @FunctionalInterface
  public interface FieldAccess<HOST, TYPE> extends Function<HOST, TYPE> {
    default Predicate<HOST> is(TYPE value) {
      return host -> Objects.equals(this.apply(host), value);
    }

    default Predicate<HOST> isNot(TYPE value) {
      return host -> !Objects.equals(this.apply(host), value);
    }

    default Predicate<HOST> isNull() {
      return host -> Objects.isNull(this.apply(host));
    }

    default Predicate<HOST> isNotNull() {
      return host -> Objects.nonNull(this.apply(host));
    }
  }


  @FunctionalInterface
  public interface StringField<HOST> extends FieldAccess<HOST, String> {
    default Predicate<HOST> startsWith(String term) {
      return host -> this.apply(host).startsWith(term);
    }

    default Predicate<HOST> matches(String regex) {
      return host -> this.apply(host).matches(regex);
    }

    default Predicate<HOST> isEmpty() {
      return host -> this.apply(host).isEmpty();
    }
  }

  private static final StringField<Person> personName = Person::getName;


  public static void main(String[] args) {
    System.out.println(autoCompleteByName("a", 5));
    System.out.println(autoCompleteByName1("a", 5));
  }
}
