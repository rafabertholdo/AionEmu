package com.aionemu.commons.scripting;

import java.io.File;

public interface ScriptCompiler {
  void setParentClassLoader(ScriptClassLoader paramScriptClassLoader);

  void setLibraires(Iterable<File> paramIterable);

  CompilationResult compile(String paramString1, String paramString2);

  CompilationResult compile(String[] paramArrayOfString1, String[] paramArrayOfString2) throws IllegalArgumentException;

  CompilationResult compile(Iterable<File> paramIterable);

  String[] getSupportedFileTypes();
}
