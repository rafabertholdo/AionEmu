package com.aionemu.commons.scripting.impl.javacompiler;

import com.aionemu.commons.scripting.ScriptClassLoader;
import com.aionemu.commons.utils.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.tools.JavaFileObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ScriptClassLoaderImpl extends ScriptClassLoader {
  private static final Logger log = Logger.getLogger(ScriptClassLoaderImpl.class);

  private final ClassFileManager classFileManager;

  ScriptClassLoaderImpl(ClassFileManager classFileManager) {
    super(new URL[0]);
    this.classFileManager = classFileManager;
  }

  ScriptClassLoaderImpl(ClassFileManager classFileManager, ClassLoader parent) {
    super(new URL[0], parent);
    this.classFileManager = classFileManager;
  }

  public ClassFileManager getClassFileManager() {
    return this.classFileManager;
  }

  public Set<String> getCompiledClasses() {
    Set<String> compiledClasses = this.classFileManager.getCompiledClasses().keySet();
    return Collections.unmodifiableSet(compiledClasses);
  }

  public Set<JavaFileObject> getClassesForPackage(String packageName) throws IOException {
    Set<JavaFileObject> result = new HashSet<JavaFileObject>();

    ClassLoader parent = getParent();
    if (parent instanceof ScriptClassLoaderImpl) {

      ScriptClassLoaderImpl pscl = (ScriptClassLoaderImpl) parent;
      result.addAll(pscl.getClassesForPackage(packageName));
    }

    for (String cn : this.classFileManager.getCompiledClasses().keySet()) {

      if (ClassUtils.isPackageMember(cn, packageName)) {

        BinaryClass bc = this.classFileManager.getCompiledClasses().get(cn);
        result.add(bc);
      }
    }

    for (String cn : this.libraryClasses) {

      if (ClassUtils.isPackageMember(cn, packageName)) {

        BinaryClass bc = new BinaryClass(cn);

        try {
          byte[] data = getRawClassByName(cn);
          OutputStream os = bc.openOutputStream();
          os.write(data);
        } catch (IOException e) {

          log.error("Error while loading class from package " + packageName, e);
          throw e;
        }
        result.add(bc);
      }
    }

    return result;
  }

  protected byte[] getRawClassByName(String name) throws IOException {
    URL resource = findResource(name.replace('.', '/').concat(".class"));
    InputStream is = null;
    byte[] clazz = null;

    try {
      is = resource.openStream();
      clazz = IOUtils.toByteArray(is);
    } catch (IOException e) {

      log.error("Error while loading class data", e);
      throw e;
    } finally {

      if (is != null) {

        try {

          is.close();
        } catch (IOException e) {

          log.error("Error while closing stream", e);
        }
      }
    }
    return clazz;
  }

  public byte[] getByteCode(String className) {
    BinaryClass bc = getClassFileManager().getCompiledClasses().get(className);
    byte[] b = new byte[(bc.getBytes()).length];
    System.arraycopy(bc.getBytes(), 0, b, 0, b.length);
    return b;
  }

  public Class<?> getDefinedClass(String name) {
    BinaryClass bc = this.classFileManager.getCompiledClasses().get(name);
    if (bc == null) {
      return null;
    }

    return bc.getDefinedClass();
  }

  public void setDefinedClass(String name, Class<?> clazz) {
    BinaryClass bc = this.classFileManager.getCompiledClasses().get(name);

    if (bc == null) {
      throw new IllegalArgumentException("Attempt to set defined class for class that was not compiled?");
    }

    bc.setDefinedClass(clazz);
  }
}
