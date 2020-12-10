package com.aionemu.gameserver.configs.network;

import com.aionemu.commons.network.IPRange;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IPConfig {
  private static final Logger log = Logger.getLogger(IPConfig.class);

  private static final String CONFIG_FILE = "./config/network/ipconfig.xml";

  private static final List<IPRange> ranges = new ArrayList<IPRange>();

  private static byte[] defaultAddress;

  public static void load() {
    try {
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      parser.parse(new File("./config/network/ipconfig.xml"), new DefaultHandler() {

        public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
          if (qName.equals("ipconfig")) {
            try {
              IPConfig.defaultAddress = InetAddress.getByName(attributes.getValue("default")).getAddress();
            } catch (UnknownHostException e) {
              throw new RuntimeException("Failed to resolve DSN for address: " + attributes.getValue("default"), e);
            }

          } else if (qName.equals("iprange")) {
            String min = attributes.getValue("min");
            String max = attributes.getValue("max");
            String address = attributes.getValue("address");
            IPRange ipRange = new IPRange(min, max, address);
            IPConfig.ranges.add(ipRange);
          }

        }
      });
    } catch (Exception e) {

      log.fatal("Critical error while parsing ipConfig", e);
      throw new Error("Can't load ipConfig", e);
    }
  }

  public static List<IPRange> getRanges() {
    return ranges;
  }

  public static byte[] getDefaultAddress() {
    return defaultAddress;
  }
}
