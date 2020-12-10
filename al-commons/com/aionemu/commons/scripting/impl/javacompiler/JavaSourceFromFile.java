package com.aionemu.commons.scripting.impl.javacompiler;

import java.io.File;
import java.io.IOException;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.apache.commons.io.FileUtils;

public class JavaSourceFromFile extends SimpleJavaFileObject {
  public JavaSourceFromFile(File file, JavaFileObject.Kind kind) {
    super(file.toURI(), kind);
  }

  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
    return FileUtils.readFileToString(new File(toUri()));
  }
}
