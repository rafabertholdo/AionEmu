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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\ScriptCompiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */