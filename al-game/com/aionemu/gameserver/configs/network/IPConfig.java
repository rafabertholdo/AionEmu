/*     */ package com.aionemu.gameserver.configs.network;
/*     */ 
/*     */ import com.aionemu.commons.network.IPRange;
/*     */ import java.io.File;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class IPConfig
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(IPConfig.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CONFIG_FILE = "./config/network/ipconfig.xml";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final List<IPRange> ranges = new ArrayList<IPRange>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] defaultAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void load() {
/*     */     try {
/*  73 */       SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
/*  74 */       parser.parse(new File("./config/network/ipconfig.xml"), new DefaultHandler()
/*     */           {
/*     */ 
/*     */ 
/*     */             
/*     */             public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
/*     */             {
/*  81 */               if (qName.equals("ipconfig"))
/*     */               {
/*     */                 try
/*     */                 {
/*  85 */                   IPConfig.defaultAddress = InetAddress.getByName(attributes.getValue("default")).getAddress();
/*     */                 }
/*  87 */                 catch (UnknownHostException e)
/*     */                 {
/*  89 */                   throw new RuntimeException("Failed to resolve DSN for address: " + attributes.getValue("default"), e);
/*     */                 }
/*     */               
/*     */               }
/*  93 */               else if (qName.equals("iprange"))
/*     */               {
/*  95 */                 String min = attributes.getValue("min");
/*  96 */                 String max = attributes.getValue("max");
/*  97 */                 String address = attributes.getValue("address");
/*  98 */                 IPRange ipRange = new IPRange(min, max, address);
/*  99 */                 IPConfig.ranges.add(ipRange);
/*     */               }
/*     */             
/*     */             }
/*     */           });
/* 104 */     } catch (Exception e) {
/*     */       
/* 106 */       log.fatal("Critical error while parsing ipConfig", e);
/* 107 */       throw new Error("Can't load ipConfig", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<IPRange> getRanges() {
/* 118 */     return ranges;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getDefaultAddress() {
/* 128 */     return defaultAddress;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\network\IPConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */