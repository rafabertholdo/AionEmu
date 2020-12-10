package com.aionemu.commons.scripting.impl.javacompiler;

import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import org.apache.log4j.Logger;

public class ErrorListener implements DiagnosticListener<JavaFileObject> {
  private static final Logger log = Logger.getLogger(ErrorListener.class);

  public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Java Compiler ").append(diagnostic.getKind()).append(": ")
        .append(diagnostic.getMessage(Locale.ENGLISH)).append("\n").append("Source: ")
        .append(((JavaFileObject) diagnostic.getSource()).getName()).append("\n").append("Line: ")
        .append(diagnostic.getLineNumber()).append("\n").append("Column: ").append(diagnostic.getColumnNumber());

    log.error(buffer.toString());
  }
}
