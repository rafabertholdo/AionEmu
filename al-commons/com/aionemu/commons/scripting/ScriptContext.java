package com.aionemu.commons.scripting;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import java.io.File;
import java.util.Collection;

public interface ScriptContext {
  void init();

  void shutdown();

  void reload();

  File getRoot();

  CompilationResult getCompilationResult();

  boolean isInitialized();

  void setLibraries(Iterable<File> paramIterable);

  Iterable<File> getLibraries();

  ScriptContext getParentScriptContext();

  Collection<ScriptContext> getChildScriptContexts();

  void addChildScriptContext(ScriptContext paramScriptContext);

  void setClassListener(ClassListener paramClassListener);

  ClassListener getClassListener();

  void setCompilerClassName(String paramString);

  String getCompilerClassName();

  boolean equals(Object paramObject);

  int hashCode();

  void finalize() throws Throwable;
}
