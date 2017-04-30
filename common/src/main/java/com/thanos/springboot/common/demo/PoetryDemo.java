package com.thanos.springboot.common.demo;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.thanos.springboot.common.demo.codegen.SimpleJavaCompiler;

import javax.lang.model.element.Modifier;
import java.util.function.Supplier;

/**
 * JavaPoet{@see https://github.com/square/javapoet} demo
 * <p>
 * Created by solarknight on 2017/4/30.
 */
public class PoetryDemo {

  private static final String POETRY = "A million stars up in the sky\n" +
      "one shines brighter I can't deny\n" +
      "A love so precious a love so true\n" +
      "a love that comes from me to you\n";

  public static String silentPoet() {
    MethodSpec recite = MethodSpec.methodBuilder("get")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .returns(String.class)
        .addStatement("return $S", POETRY)
        .build();

    TypeSpec poet = TypeSpec.classBuilder("Poet")
        .addSuperinterface(ParameterizedTypeName.get(Supplier.class, String.class))
        .addModifiers(Modifier.PUBLIC)
        .addMethod(recite)
        .build();

    JavaFile javaFile = JavaFile.builder("com.thanos.example", poet)
        .build();
    return javaFile.toString();
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    Class<? extends Supplier> cls = SimpleJavaCompiler.compile(Supplier.class, PoetryDemo.silentPoet());
    Supplier<String> poet = cls.newInstance();
    System.out.println(poet.get());
  }
}
