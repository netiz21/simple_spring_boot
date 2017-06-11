package com.thanos.springboot.common.demo.codegen;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Simple wrapper over javac to compile and class-load a single class
 */
public class SimpleJavaCompiler {

  private static final String CLASSPATH = System.getProperty("java.class.path");

  /**
   * A javac instance
   */
  private final JavaCompiler compiler;
  /**
   * The Standard file manager for the compiler
   */
  private final StandardJavaFileManager standardJavaFileManager;

  /**
   * We keep an initialized copy for each thread.
   */
  private static final ThreadLocal<SimpleJavaCompiler> BY_THREAD = new ThreadLocal<SimpleJavaCompiler>() {
    @Override
    protected SimpleJavaCompiler initialValue() {
      return new SimpleJavaCompiler();
    }
  };

  /**
   * Create a new compiler
   */
  private SimpleJavaCompiler() {
    compiler = ToolProvider.getSystemJavaCompiler();
    standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
  }

  /**
   * Compiles and class-loads a single class from the specified Java source code.
   * <p>
   * To amortize the cost of initializing a JavaCompiler, underlying instances are cached
   * on a per-thread basis. It is recommended that callers of this method reuse threads
   * for increased performance and reduced memory consumption.
   *
   * @param source a String containing Java source code to generate the class
   * @return the compiled and class-loaded class
   */
  public static <T> Class<? extends T> compile(Class<T> superClass, String source) {
    return BY_THREAD.get().compile0(superClass, source);
  }

  public static volatile boolean dump = Boolean.getBoolean("compiler.java.dump");

  public static volatile String dumpDir = System.getProperty("compiler.java.dump.dir");

  private static void dump(StringWriter sw, Class<?> compiledClass) {
    if (dump) {
      System.out.println(sw);
    }
    if (dumpDir != null) {
      try {
        Files.write(Paths.get(dumpDir, compiledClass.getCanonicalName().replace('.', File.separatorChar) + ".java"), sw.toString().getBytes());
      } catch (IOException e) {
        throw new RuntimeException("unable to dump source file", e);
      }
    }
  }


  /**
   * Compiles and class-loads a single class from the specified Java source code.
   *
   * @param source a String containing Java source code to generate the class
   * @return the compiled and class-loaded class
   */
  private <T> Class<? extends T> compile0(Class<T> superClass, String source) {
    final List<JavaFileObject> compilationUnits = Collections.singletonList(new SourceFile(toUri("Generated"), source));
    final FileManager fileManager = new FileManager(standardJavaFileManager);
    final StringWriter output = new StringWriter();
    final CompilationTask task = compiler.getTask(output, fileManager, null, Arrays.asList("-g", "-proc:none", "-classpath", CLASSPATH), null, compilationUnits);

    if (!task.call()) {
      throw new IllegalArgumentException("Compilation failed:\n" + output + "\n Source code: \n" + source);
    }

    int classCount = fileManager.output.size();
    // 2nd condition to work around the case where additional "GuardedBy" generated in TA Spark tagging
    if (classCount == 1 || (classCount > 0 && "CompiledStage"
        .equals(fileManager.output.get(0).getName()))) {
      return DiscardableClassLoader.classFromBytes(superClass, null, fileManager.output.get(0).outputStream.toByteArray());
    }
    throw new IllegalArgumentException("Compilation yielded an unexpected number of classes: " + classCount);
  }

  /**
   * Represents a source file whose contents is loaded from a String
   */
  private static class SourceFile extends SimpleJavaFileObject {
    private final String contents;

    private SourceFile(URI uri, String contents) {
      super(uri, Kind.SOURCE);
      this.contents = contents;
    }

    @Override
    public String getName() {
      return uri.getRawSchemeSpecificPart();
    }

    /**
     * Ignore the file name for public classes
     */
    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
      return true;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
      return contents;
    }
  }

  /**
   * A compiled class file. It's content is stored in a ByteArrayOutputStream.
   */
  private static class ClassFile extends SimpleJavaFileObject {
    private ByteArrayOutputStream outputStream;

    private ClassFile(URI uri) {
      super(uri, Kind.CLASS);
    }

    @Override
    public String getName() {
      return uri.getRawSchemeSpecificPart();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
      return outputStream = new ByteArrayOutputStream();
    }
  }

  /**
   * A simple file manager that collects written files in memory
   */
  private static class FileManager
      extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private List<ClassFile> output = new ArrayList<>();

    FileManager(StandardJavaFileManager target) {
      super(target);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
      final ClassFile file = new ClassFile(toUri(className));
      output.add(file);
      return file;
    }
  }

  private static URI toUri(String path) {
    try {
      return new URI(null, null, path, null);
    } catch (URISyntaxException e) {
      throw new RuntimeException("exception parsing uri", e);
    }
  }
}
