package com.aionemu.commons.scripting.impl.javacompiler;

import com.sun.tools.javac.file.BaseFileObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import javax.tools.JavaFileObject;

public class BinaryClass extends BaseFileObject {
  private final String name;
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  private Class<?> definedClass;

  protected BinaryClass(String name) {
    super(null);
    this.name = name;
  }

  public URI toUri() {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public String getName() {
    return this.name + ".class";
  }

  public InputStream openInputStream() throws IOException {
    return new ByteArrayInputStream(this.baos.toByteArray());
  }

  public OutputStream openOutputStream() throws IOException {
    return this.baos;
  }

  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
    throw new UnsupportedOperationException();
  }

  public Writer openWriter() throws IOException {
    throw new UnsupportedOperationException();
  }

  public long getLastModified() {
    return 0L;
  }

  public boolean delete() {
    return false;
  }

  protected String inferBinaryName(Iterable<? extends File> path) {
    return this.name;
  }

  public boolean isNameCompatible(String simpleName, JavaFileObject.Kind kind) {
    return JavaFileObject.Kind.CLASS.equals(kind);
  }

  public byte[] getBytes() {
    return this.baos.toByteArray();
  }

  public Class<?> getDefinedClass() {
    return this.definedClass;
  }

  public void setDefinedClass(Class<?> definedClass) {
    this.definedClass = definedClass;
  }
}
