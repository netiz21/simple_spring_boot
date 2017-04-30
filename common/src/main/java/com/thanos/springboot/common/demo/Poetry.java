package com.thanos.springboot.common.demo;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * JavaPoet{@see https://github.com/square/javapoet} demo
 * <p>
 * Created by solarknight on 2017/4/30.
 */
public class Poetry {

  public static String newPoetry() {
    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, "args")
        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
        .build();
    return javaFile.toString();
  }

  public static void main(String[] args) {
    System.out.println(newPoetry());
  }
}
