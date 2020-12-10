/*    */ package com.aionemu.gameserver.utils.xml;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.StaticData;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.xml.bind.JAXBContext;
/*    */ import javax.xml.bind.SchemaOutputResolver;
/*    */ import javax.xml.transform.Result;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchemaGen
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 33 */     final File baseDir = new File("./data/static_data");
/*    */     
/*    */     class MySchemaOutputResolver
/*    */       extends SchemaOutputResolver
/*    */     {
/*    */       public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException
/*    */       {
/* 40 */         return new StreamResult(new File(baseDir, "static_data1.xsd"));
/*    */       }
/*    */     };
/* 43 */     JAXBContext context = JAXBContext.newInstance(new Class[] { StaticData.class });
/* 44 */     context.generateSchema(new MySchemaOutputResolver());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\xml\SchemaGen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */