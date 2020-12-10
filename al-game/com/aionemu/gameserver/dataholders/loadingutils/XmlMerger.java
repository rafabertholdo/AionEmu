/*     */ package com.aionemu.gameserver.dataholders.loadingutils;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*     */ import org.apache.commons.io.filefilter.HiddenFileFilter;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlMerger
/*     */ {
/* 101 */   private static final Logger logger = Logger.getLogger(XmlMerger.class);
/*     */   
/*     */   private final File baseDir;
/*     */   
/*     */   private final File sourceFile;
/*     */   
/*     */   private final File destFile;
/*     */   
/*     */   private final File metaDataFile;
/* 110 */   private XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/* 111 */   private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
/* 112 */   private XMLEventFactory eventFactory = XMLEventFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XmlMerger(File source, File target) {
/* 123 */     this(source, target, source.getParentFile());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XmlMerger(File source, File target, File baseDir) {
/* 136 */     this.baseDir = baseDir;
/*     */     
/* 138 */     this.sourceFile = source;
/* 139 */     this.destFile = target;
/*     */     
/* 141 */     this.metaDataFile = new File(target.getParent(), target.getName() + ".properties");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process() throws Exception {
/* 154 */     logger.debug("Processing " + this.sourceFile + " files into " + this.destFile);
/*     */     
/* 156 */     if (!this.sourceFile.exists()) {
/* 157 */       throw new FileNotFoundException("Source file " + this.sourceFile.getPath() + " not found.");
/*     */     }
/* 159 */     boolean needUpdate = false;
/*     */     
/* 161 */     if (!this.destFile.exists()) {
/*     */       
/* 163 */       logger.debug("Dest file not found - creating new file");
/* 164 */       needUpdate = true;
/*     */     }
/* 166 */     else if (!this.metaDataFile.exists()) {
/*     */       
/* 168 */       logger.debug("Meta file not found - creating new file");
/* 169 */       needUpdate = true;
/*     */     }
/*     */     else {
/*     */       
/* 173 */       logger.debug("Dest file found - checking file modifications");
/* 174 */       needUpdate = checkFileModifications();
/*     */     } 
/*     */     
/* 177 */     if (needUpdate) {
/*     */       
/* 179 */       logger.debug("Modifications found. Updating...");
/*     */       
/*     */       try {
/* 182 */         doUpdate();
/*     */       }
/* 184 */       catch (Exception e) {
/*     */         
/* 186 */         FileUtils.deleteQuietly(this.destFile);
/* 187 */         FileUtils.deleteQuietly(this.metaDataFile);
/* 188 */         throw e;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 193 */       logger.debug("Files are up-to-date");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkFileModifications() throws Exception {
/* 209 */     long destFileTime = this.destFile.lastModified();
/*     */     
/* 211 */     if (this.sourceFile.lastModified() > destFileTime) {
/*     */       
/* 213 */       logger.debug("Source file was modified ");
/* 214 */       return true;
/*     */     } 
/*     */     
/* 217 */     Properties metadata = restoreFileModifications(this.metaDataFile);
/*     */     
/* 219 */     if (metadata == null) {
/* 220 */       return true;
/*     */     }
/* 222 */     SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/*     */     
/* 224 */     SAXParser parser = parserFactory.newSAXParser();
/*     */     
/* 226 */     TimeCheckerHandler handler = new TimeCheckerHandler(this.baseDir, metadata);
/*     */     
/* 228 */     parser.parse(this.sourceFile, handler);
/*     */     
/* 230 */     return handler.isModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doUpdate() throws XMLStreamException, IOException {
/* 244 */     XMLEventReader reader = null;
/* 245 */     XMLEventWriter writer = null;
/*     */     
/* 247 */     Properties metadata = new Properties();
/*     */ 
/*     */     
/*     */     try {
/* 251 */       writer = this.outputFactory.createXMLEventWriter(new BufferedWriter(new FileWriter(this.destFile, false)));
/* 252 */       reader = this.inputFactory.createXMLEventReader(new FileReader(this.sourceFile));
/*     */       
/* 254 */       while (reader.hasNext()) {
/*     */         
/* 256 */         XMLEvent xmlEvent = reader.nextEvent();
/*     */         
/* 258 */         if (xmlEvent.isStartElement() && isImportQName(xmlEvent.asStartElement().getName())) {
/*     */           
/* 260 */           processImportElement(xmlEvent.asStartElement(), writer, metadata);
/*     */           
/*     */           continue;
/*     */         } 
/* 264 */         if (xmlEvent.isEndElement() && isImportQName(xmlEvent.asEndElement().getName())) {
/*     */           continue;
/*     */         }
/* 267 */         if (xmlEvent instanceof javax.xml.stream.events.Comment) {
/*     */           continue;
/*     */         }
/* 270 */         if (xmlEvent.isCharacters() && (
/* 271 */           xmlEvent.asCharacters().isWhiteSpace() || xmlEvent.asCharacters().isIgnorableWhiteSpace())) {
/*     */           continue;
/*     */         }
/* 274 */         writer.add(xmlEvent);
/*     */         
/* 276 */         if (xmlEvent.isStartDocument()) {
/* 277 */           writer.add(this.eventFactory.createComment("\nThis file is machine-generated. DO NOT MODIFY IT!\n"));
/*     */         }
/*     */       } 
/*     */       
/* 281 */       storeFileModifications(metadata, this.metaDataFile);
/*     */     }
/*     */     finally {
/*     */       
/* 285 */       if (writer != null)
/* 286 */         try { writer.close(); } catch (Exception ignored) {} 
/* 287 */       if (reader != null) {
/* 288 */         try { reader.close(); } catch (Exception ignored) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isImportQName(QName name) {
/* 294 */     return "import".equals(name.getLocalPart());
/*     */   }
/*     */   
/* 297 */   private static final QName qNameFile = new QName("file");
/* 298 */   private static final QName qNameSkipRoot = new QName("skipRoot");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   private static final QName qNameRecursiveImport = new QName("recursiveImport");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processImportElement(StartElement element, XMLEventWriter writer, Properties metadata) throws XMLStreamException, IOException {
/* 315 */     File file = new File(this.baseDir, getAttributeValue(element, qNameFile, null, "Attribute 'file' is missing or empty."));
/*     */     
/* 317 */     if (!file.exists()) {
/* 318 */       throw new FileNotFoundException("Missing file to import:" + file.getPath());
/*     */     }
/* 320 */     boolean skipRoot = Boolean.valueOf(getAttributeValue(element, qNameSkipRoot, "false", null)).booleanValue();
/* 321 */     boolean recImport = Boolean.valueOf(getAttributeValue(element, qNameRecursiveImport, "true", null)).booleanValue();
/*     */     
/* 323 */     if (file.isFile()) {
/*     */       
/* 325 */       importFile(file, skipRoot, writer, metadata);
/*     */     }
/*     */     else {
/*     */       
/* 329 */       logger.debug("Processing dir " + file);
/*     */       
/* 331 */       Collection<File> files = listFiles(file, recImport);
/*     */       
/* 333 */       for (File childFile : files)
/*     */       {
/* 335 */         importFile(childFile, skipRoot, writer, metadata);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<File> listFiles(File root, boolean recursive) {
/* 343 */     IOFileFilter dirFilter = recursive ? FileFilterUtils.makeSVNAware(HiddenFileFilter.VISIBLE) : null;
/*     */     
/* 345 */     return FileUtils.listFiles(root, FileFilterUtils.andFileFilter(FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter(FileFilterUtils.prefixFileFilter("new")), FileFilterUtils.suffixFileFilter(".xml")), HiddenFileFilter.VISIBLE), dirFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAttributeValue(StartElement element, QName name, String def, String onErrorMessage) throws XMLStreamException {
/* 368 */     Attribute attribute = element.getAttributeByName(name);
/*     */     
/* 370 */     if (attribute == null) {
/*     */       
/* 372 */       if (def == null) {
/* 373 */         throw new XMLStreamException(onErrorMessage, element.getLocation());
/*     */       }
/* 375 */       return def;
/*     */     } 
/*     */     
/* 378 */     return attribute.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void importFile(File file, boolean skipRoot, XMLEventWriter writer, Properties metadata) throws XMLStreamException, IOException {
/* 396 */     logger.debug("Appending file " + file);
/* 397 */     metadata.setProperty(file.getPath(), makeHash(file));
/*     */     
/* 399 */     XMLEventReader reader = null;
/*     */ 
/*     */     
/*     */     try {
/* 403 */       reader = this.inputFactory.createXMLEventReader(new FileReader(file));
/*     */       
/* 405 */       QName firstTagQName = null;
/*     */       
/* 407 */       while (reader.hasNext())
/*     */       {
/* 409 */         XMLEvent event = reader.nextEvent();
/*     */ 
/*     */         
/* 412 */         if (event.isStartDocument() || event.isEndDocument()) {
/*     */           continue;
/*     */         }
/* 415 */         if (event instanceof javax.xml.stream.events.Comment) {
/*     */           continue;
/*     */         }
/* 418 */         if (event.isCharacters())
/*     */         {
/* 420 */           if (event.asCharacters().isWhiteSpace() || event.asCharacters().isIgnorableWhiteSpace()) {
/*     */             continue;
/*     */           }
/*     */         }
/*     */         
/* 425 */         if (firstTagQName == null && event.isStartElement()) {
/*     */           
/* 427 */           firstTagQName = event.asStartElement().getName();
/*     */           
/* 429 */           if (skipRoot) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 435 */           StartElement old = event.asStartElement();
/*     */           
/* 437 */           event = this.eventFactory.createStartElement(old.getName(), old.getAttributes(), (Iterator)null);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 442 */         if (event.isEndElement() && skipRoot && event.asEndElement().getName().equals(firstTagQName)) {
/*     */           continue;
/*     */         }
/*     */         
/* 446 */         writer.add(event);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 451 */       if (reader != null) {
/* 452 */         try { reader.close(); } catch (Exception ignored) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TimeCheckerHandler
/*     */     extends DefaultHandler
/*     */   {
/*     */     private File basedir;
/*     */     private Properties metadata;
/*     */     private boolean isModified = false;
/*     */     private Locator locator;
/*     */     
/*     */     private TimeCheckerHandler(File basedir, Properties metadata) {
/* 467 */       this.basedir = basedir;
/* 468 */       this.metadata = metadata;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDocumentLocator(Locator locator) {
/* 474 */       this.locator = locator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 480 */       if (this.isModified || !"import".equals(qName)) {
/*     */         return;
/*     */       }
/* 483 */       String value = attributes.getValue(XmlMerger.qNameFile.getLocalPart());
/*     */       
/* 485 */       if (value == null) {
/* 486 */         throw new SAXParseException("Attribute 'file' is missing", this.locator);
/*     */       }
/* 488 */       File file = new File(this.basedir, value);
/*     */       
/* 490 */       if (!file.exists())
/*     */       {
/* 492 */         throw new SAXParseException("Imported file not found. file=" + file.getPath(), this.locator);
/*     */       }
/* 494 */       if (file.isFile() && checkFile(file)) {
/*     */         
/* 496 */         this.isModified = true;
/*     */         
/*     */         return;
/*     */       } 
/* 500 */       if (file.isDirectory()) {
/*     */         
/* 502 */         String rec = attributes.getValue(XmlMerger.qNameRecursiveImport.getLocalPart());
/*     */         
/* 504 */         Collection<File> files = XmlMerger.listFiles(file, (rec == null) ? true : Boolean.valueOf(rec).booleanValue());
/*     */         
/* 506 */         for (File childFile : files) {
/*     */           
/* 508 */           if (checkFile(childFile)) {
/*     */             
/* 510 */             this.isModified = true;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean checkFile(File file) {
/* 519 */       String data = this.metadata.getProperty(file.getPath());
/*     */       
/* 521 */       if (data == null) {
/* 522 */         return true;
/*     */       }
/*     */       
/*     */       try {
/* 526 */         String hash = XmlMerger.makeHash(file);
/*     */         
/* 528 */         if (!data.equals(hash)) {
/* 529 */           return true;
/*     */         }
/* 531 */       } catch (IOException e) {
/*     */         
/* 533 */         XmlMerger.logger.warn("File varification error. File: " + file.getPath() + ", location=" + this.locator.getLineNumber() + ":" + this.locator.getColumnNumber(), e);
/*     */         
/* 535 */         return true;
/*     */       } 
/*     */       
/* 538 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isModified() {
/* 543 */       return this.isModified;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Properties restoreFileModifications(File file) {
/* 549 */     if (!file.exists() || !file.isFile()) {
/* 550 */       return null;
/*     */     }
/* 552 */     FileReader reader = null;
/*     */ 
/*     */     
/*     */     try {
/* 556 */       Properties props = new Properties();
/*     */       
/* 558 */       reader = new FileReader(file);
/*     */       
/* 560 */       props.load(reader);
/*     */       
/* 562 */       return props;
/*     */     }
/* 564 */     catch (IOException e) {
/*     */       
/* 566 */       logger.debug("File modfications restoring error. ", e);
/* 567 */       return null;
/*     */     }
/*     */     finally {
/*     */       
/* 571 */       IOUtils.closeQuietly(reader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeFileModifications(Properties props, File file) throws IOException {
/* 578 */     FileWriter writer = null;
/*     */     
/*     */     try {
/* 581 */       writer = new FileWriter(file, false);
/* 582 */       props.store(writer, " This file is machine-generated. DO NOT EDIT!");
/*     */     }
/* 584 */     catch (IOException e) {
/*     */       
/* 586 */       logger.error("Failed to store file modification data.");
/* 587 */       throw e;
/*     */     }
/*     */     finally {
/*     */       
/* 591 */       IOUtils.closeQuietly(writer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String makeHash(File file) throws IOException {
/* 605 */     return String.valueOf(FileUtils.checksumCRC32(file));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\loadingutils\XmlMerger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */