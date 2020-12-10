package com.aionemu.gameserver.dataholders.loadingutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
































































public class XmlMerger
{
  private static final Logger logger = Logger.getLogger(XmlMerger.class);
  
  private final File baseDir;
  
  private final File sourceFile;
  
  private final File destFile;
  
  private final File metaDataFile;
  private XMLInputFactory inputFactory = XMLInputFactory.newInstance();
  private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
  private XMLEventFactory eventFactory = XMLEventFactory.newInstance();








  
  public XmlMerger(File source, File target) {
    this(source, target, source.getParentFile());
  }









  
  public XmlMerger(File source, File target, File baseDir) {
    this.baseDir = baseDir;
    
    this.sourceFile = source;
    this.destFile = target;
    
    this.metaDataFile = new File(target.getParent(), target.getName() + ".properties");
  }









  
  public void process() throws Exception {
    logger.debug("Processing " + this.sourceFile + " files into " + this.destFile);
    
    if (!this.sourceFile.exists()) {
      throw new FileNotFoundException("Source file " + this.sourceFile.getPath() + " not found.");
    }
    boolean needUpdate = false;
    
    if (!this.destFile.exists()) {
      
      logger.debug("Dest file not found - creating new file");
      needUpdate = true;
    }
    else if (!this.metaDataFile.exists()) {
      
      logger.debug("Meta file not found - creating new file");
      needUpdate = true;
    }
    else {
      
      logger.debug("Dest file found - checking file modifications");
      needUpdate = checkFileModifications();
    } 
    
    if (needUpdate) {
      
      logger.debug("Modifications found. Updating...");
      
      try {
        doUpdate();
      }
      catch (Exception e) {
        
        FileUtils.deleteQuietly(this.destFile);
        FileUtils.deleteQuietly(this.metaDataFile);
        throw e;
      }
    
    } else {
      
      logger.debug("Files are up-to-date");
    } 
  }











  
  private boolean checkFileModifications() throws Exception {
    long destFileTime = this.destFile.lastModified();
    
    if (this.sourceFile.lastModified() > destFileTime) {
      
      logger.debug("Source file was modified ");
      return true;
    } 
    
    Properties metadata = restoreFileModifications(this.metaDataFile);
    
    if (metadata == null) {
      return true;
    }
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    
    SAXParser parser = parserFactory.newSAXParser();
    
    TimeCheckerHandler handler = new TimeCheckerHandler(this.baseDir, metadata);
    
    parser.parse(this.sourceFile, handler);
    
    return handler.isModified();
  }










  
  private void doUpdate() throws XMLStreamException, IOException {
    XMLEventReader reader = null;
    XMLEventWriter writer = null;
    
    Properties metadata = new Properties();

    
    try {
      writer = this.outputFactory.createXMLEventWriter(new BufferedWriter(new FileWriter(this.destFile, false)));
      reader = this.inputFactory.createXMLEventReader(new FileReader(this.sourceFile));
      
      while (reader.hasNext()) {
        
        XMLEvent xmlEvent = reader.nextEvent();
        
        if (xmlEvent.isStartElement() && isImportQName(xmlEvent.asStartElement().getName())) {
          
          processImportElement(xmlEvent.asStartElement(), writer, metadata);
          
          continue;
        } 
        if (xmlEvent.isEndElement() && isImportQName(xmlEvent.asEndElement().getName())) {
          continue;
        }
        if (xmlEvent instanceof javax.xml.stream.events.Comment) {
          continue;
        }
        if (xmlEvent.isCharacters() && (
          xmlEvent.asCharacters().isWhiteSpace() || xmlEvent.asCharacters().isIgnorableWhiteSpace())) {
          continue;
        }
        writer.add(xmlEvent);
        
        if (xmlEvent.isStartDocument()) {
          writer.add(this.eventFactory.createComment("\nThis file is machine-generated. DO NOT MODIFY IT!\n"));
        }
      } 
      
      storeFileModifications(metadata, this.metaDataFile);
    }
    finally {
      
      if (writer != null)
        try { writer.close(); } catch (Exception ignored) {} 
      if (reader != null) {
        try { reader.close(); } catch (Exception ignored) {}
      }
    } 
  }
  
  private boolean isImportQName(QName name) {
    return "import".equals(name.getLocalPart());
  }
  
  private static final QName qNameFile = new QName("file");
  private static final QName qNameSkipRoot = new QName("skipRoot");




  
  private static final QName qNameRecursiveImport = new QName("recursiveImport");








  
  private void processImportElement(StartElement element, XMLEventWriter writer, Properties metadata) throws XMLStreamException, IOException {
    File file = new File(this.baseDir, getAttributeValue(element, qNameFile, null, "Attribute 'file' is missing or empty."));
    
    if (!file.exists()) {
      throw new FileNotFoundException("Missing file to import:" + file.getPath());
    }
    boolean skipRoot = Boolean.valueOf(getAttributeValue(element, qNameSkipRoot, "false", null)).booleanValue();
    boolean recImport = Boolean.valueOf(getAttributeValue(element, qNameRecursiveImport, "true", null)).booleanValue();
    
    if (file.isFile()) {
      
      importFile(file, skipRoot, writer, metadata);
    }
    else {
      
      logger.debug("Processing dir " + file);
      
      Collection<File> files = listFiles(file, recImport);
      
      for (File childFile : files)
      {
        importFile(childFile, skipRoot, writer, metadata);
      }
    } 
  }


  
  private static Collection<File> listFiles(File root, boolean recursive) {
    IOFileFilter dirFilter = recursive ? FileFilterUtils.makeSVNAware(HiddenFileFilter.VISIBLE) : null;
    
    return FileUtils.listFiles(root, FileFilterUtils.andFileFilter(FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter(FileFilterUtils.prefixFileFilter("new")), FileFilterUtils.suffixFileFilter(".xml")), HiddenFileFilter.VISIBLE), dirFilter);
  }



















  
  private String getAttributeValue(StartElement element, QName name, String def, String onErrorMessage) throws XMLStreamException {
    Attribute attribute = element.getAttributeByName(name);
    
    if (attribute == null) {
      
      if (def == null) {
        throw new XMLStreamException(onErrorMessage, element.getLocation());
      }
      return def;
    } 
    
    return attribute.getValue();
  }














  
  private void importFile(File file, boolean skipRoot, XMLEventWriter writer, Properties metadata) throws XMLStreamException, IOException {
    logger.debug("Appending file " + file);
    metadata.setProperty(file.getPath(), makeHash(file));
    
    XMLEventReader reader = null;

    
    try {
      reader = this.inputFactory.createXMLEventReader(new FileReader(file));
      
      QName firstTagQName = null;
      
      while (reader.hasNext())
      {
        XMLEvent event = reader.nextEvent();

        
        if (event.isStartDocument() || event.isEndDocument()) {
          continue;
        }
        if (event instanceof javax.xml.stream.events.Comment) {
          continue;
        }
        if (event.isCharacters())
        {
          if (event.asCharacters().isWhiteSpace() || event.asCharacters().isIgnorableWhiteSpace()) {
            continue;
          }
        }
        
        if (firstTagQName == null && event.isStartElement()) {
          
          firstTagQName = event.asStartElement().getName();
          
          if (skipRoot) {
            continue;
          }


          
          StartElement old = event.asStartElement();
          
          event = this.eventFactory.createStartElement(old.getName(), old.getAttributes(), (Iterator)null);
        } 


        
        if (event.isEndElement() && skipRoot && event.asEndElement().getName().equals(firstTagQName)) {
          continue;
        }
        
        writer.add(event);
      }
    
    } finally {
      
      if (reader != null) {
        try { reader.close(); } catch (Exception ignored) {}
      }
    } 
  }

  
  private static class TimeCheckerHandler
    extends DefaultHandler
  {
    private File basedir;
    private Properties metadata;
    private boolean isModified = false;
    private Locator locator;
    
    private TimeCheckerHandler(File basedir, Properties metadata) {
      this.basedir = basedir;
      this.metadata = metadata;
    }


    
    public void setDocumentLocator(Locator locator) {
      this.locator = locator;
    }


    
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (this.isModified || !"import".equals(qName)) {
        return;
      }
      String value = attributes.getValue(XmlMerger.qNameFile.getLocalPart());
      
      if (value == null) {
        throw new SAXParseException("Attribute 'file' is missing", this.locator);
      }
      File file = new File(this.basedir, value);
      
      if (!file.exists())
      {
        throw new SAXParseException("Imported file not found. file=" + file.getPath(), this.locator);
      }
      if (file.isFile() && checkFile(file)) {
        
        this.isModified = true;
        
        return;
      } 
      if (file.isDirectory()) {
        
        String rec = attributes.getValue(XmlMerger.qNameRecursiveImport.getLocalPart());
        
        Collection<File> files = XmlMerger.listFiles(file, (rec == null) ? true : Boolean.valueOf(rec).booleanValue());
        
        for (File childFile : files) {
          
          if (checkFile(childFile)) {
            
            this.isModified = true;
            return;
          } 
        } 
      } 
    }

    
    private boolean checkFile(File file) {
      String data = this.metadata.getProperty(file.getPath());
      
      if (data == null) {
        return true;
      }
      
      try {
        String hash = XmlMerger.makeHash(file);
        
        if (!data.equals(hash)) {
          return true;
        }
      } catch (IOException e) {
        
        XmlMerger.logger.warn("File varification error. File: " + file.getPath() + ", location=" + this.locator.getLineNumber() + ":" + this.locator.getColumnNumber(), e);
        
        return true;
      } 
      
      return false;
    }

    
    public boolean isModified() {
      return this.isModified;
    }
  }

  
  private Properties restoreFileModifications(File file) {
    if (!file.exists() || !file.isFile()) {
      return null;
    }
    FileReader reader = null;

    
    try {
      Properties props = new Properties();
      
      reader = new FileReader(file);
      
      props.load(reader);
      
      return props;
    }
    catch (IOException e) {
      
      logger.debug("File modfications restoring error. ", e);
      return null;
    }
    finally {
      
      IOUtils.closeQuietly(reader);
    } 
  }


  
  private void storeFileModifications(Properties props, File file) throws IOException {
    FileWriter writer = null;
    
    try {
      writer = new FileWriter(file, false);
      props.store(writer, " This file is machine-generated. DO NOT EDIT!");
    }
    catch (IOException e) {
      
      logger.error("Failed to store file modification data.");
      throw e;
    }
    finally {
      
      IOUtils.closeQuietly(writer);
    } 
  }









  
  private static String makeHash(File file) throws IOException {
    return String.valueOf(FileUtils.checksumCRC32(file));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\loadingutils\XmlMerger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
