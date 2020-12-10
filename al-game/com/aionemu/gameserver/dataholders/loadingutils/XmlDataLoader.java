package com.aionemu.gameserver.dataholders.loadingutils;

import com.aionemu.gameserver.dataholders.StaticData;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class XmlDataLoader {
  private static final Logger log = Logger.getLogger(XmlDataLoader.class);

  private static final String XML_SCHEMA_FILE = "./data/static_data/static_data.xsd";

  private static final String CACHE_DIRECTORY = "./cache/";

  private static final String CACHE_XML_FILE = "./cache/static_data.xml";

  private static final String MAIN_XML_FILE = "./data/static_data/static_data.xml";

  public static final XmlDataLoader getInstance() {
    return SingletonHolder.instance;
  }

  private XmlDataLoader() {
  }

  public StaticData loadStaticData() {
    makeCacheDirectory();

    File cachedXml = new File("./cache/static_data.xml");
    File cleanMainXml = new File("./data/static_data/static_data.xml");

    mergeXmlFiles(cachedXml, cleanMainXml);

    try {
      JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
      Unmarshaller un = jc.createUnmarshaller();
      un.setSchema(getSchema());
      return (StaticData) un.unmarshal(new File("./cache/static_data.xml"));
    } catch (Exception e) {

      log.fatal("Error while loading static data", e);
      throw new Error("Error while loading static data", e);
    }
  }

  private Schema getSchema() {
    Schema schema = null;
    SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    try {
      schema = sf.newSchema(new File("./data/static_data/static_data.xsd"));
    } catch (SAXException saxe) {

      log.fatal("Error while getting schema", saxe);
      throw new Error("Error while getting schema", saxe);
    }

    return schema;
  }

  private void makeCacheDirectory() {
    File cacheDir = new File("./cache/");
    if (!cacheDir.exists()) {
      cacheDir.mkdir();
    }
  }

  private void mergeXmlFiles(File cachedXml, File cleanMainXml) throws Error {
    XmlMerger merger = new XmlMerger(cleanMainXml, cachedXml);

    try {
      merger.process();
    } catch (Exception e) {

      log.error("Error while merging xml files,e");
      throw new Error("Error while merging xml files", e);
    }
  }

  private static class SingletonHolder {
    protected static final XmlDataLoader instance = new XmlDataLoader();
  }
}
