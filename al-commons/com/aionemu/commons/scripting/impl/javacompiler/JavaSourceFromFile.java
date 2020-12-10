package com.aionemu.commons.scripting.impl.javacompiler;

import java.io.File;
import java.io.IOException;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.apache.commons.io.FileUtils;































public class JavaSourceFromFile
  extends SimpleJavaFileObject
{
  public JavaSourceFromFile(File file, JavaFileObject.Kind kind) {
    super(file.toURI(), kind);
  }











  
  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
    return FileUtils.readFileToString(new File(toUri()));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\JavaSourceFromFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
