package com.thanos.springboot.common.demo.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author peiheng.zph created on 17/6/20 下午5:19
 * @version 1.0
 */
@SupportedAnnotationTypes("com.thanos.springboot.common.demo.annotation.Shine")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ShineProcessor extends AbstractProcessor {

  public ShineProcessor() {
    super();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element elem : roundEnv.getElementsAnnotatedWith(Shine.class)) {
      Shine shine = elem.getAnnotation(Shine.class);
      String message = "annotation found in " + elem.getSimpleName();
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
    return true; // no further processing of this annotation type
  }
}
