/*     */ package com.aionemu.gameserver.dataholders.loadingutils;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.StaticData;
/*     */ import java.io.File;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class XmlDataLoader
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(XmlDataLoader.class);
/*     */   
/*     */   private static final String XML_SCHEMA_FILE = "./data/static_data/static_data.xsd";
/*     */   
/*     */   private static final String CACHE_DIRECTORY = "./cache/";
/*     */   
/*     */   private static final String CACHE_XML_FILE = "./cache/static_data.xml";
/*     */   
/*     */   private static final String MAIN_XML_FILE = "./data/static_data/static_data.xml";
/*     */   
/*     */   public static final XmlDataLoader getInstance() {
/*  52 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XmlDataLoader() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticData loadStaticData() {
/*  66 */     makeCacheDirectory();
/*     */     
/*  68 */     File cachedXml = new File("./cache/static_data.xml");
/*  69 */     File cleanMainXml = new File("./data/static_data/static_data.xml");
/*     */     
/*  71 */     mergeXmlFiles(cachedXml, cleanMainXml);
/*     */ 
/*     */     
/*     */     try {
/*  75 */       JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
/*  76 */       Unmarshaller un = jc.createUnmarshaller();
/*  77 */       un.setSchema(getSchema());
/*  78 */       return (StaticData)un.unmarshal(new File("./cache/static_data.xml"));
/*     */     }
/*  80 */     catch (Exception e) {
/*     */       
/*  82 */       log.fatal("Error while loading static data", e);
/*  83 */       throw new Error("Error while loading static data", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Schema getSchema() {
/*  94 */     Schema schema = null;
/*  95 */     SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */ 
/*     */     
/*     */     try {
/*  99 */       schema = sf.newSchema(new File("./data/static_data/static_data.xsd"));
/*     */     }
/* 101 */     catch (SAXException saxe) {
/*     */       
/* 103 */       log.fatal("Error while getting schema", saxe);
/* 104 */       throw new Error("Error while getting schema", saxe);
/*     */     } 
/*     */     
/* 107 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeCacheDirectory() {
/* 113 */     File cacheDir = new File("./cache/");
/* 114 */     if (!cacheDir.exists()) {
/* 115 */       cacheDir.mkdir();
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
/*     */   private void mergeXmlFiles(File cachedXml, File cleanMainXml) throws Error {
/* 128 */     XmlMerger merger = new XmlMerger(cleanMainXml, cachedXml);
/*     */     
/*     */     try {
/* 131 */       merger.process();
/*     */     }
/* 133 */     catch (Exception e) {
/*     */       
/* 135 */       log.error("Error while merging xml files,e");
/* 136 */       throw new Error("Error while merging xml files", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 143 */     protected static final XmlDataLoader instance = new XmlDataLoader();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\loadingutils\XmlDataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */