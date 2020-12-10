package admincommands;

import com.aionemu.gameserver.GameServerError;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_PACKET;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;

public class AdvSendFakeServerPacket extends AdminCommand {
    private static final Logger logger = Logger.getLogger(AdvSendFakeServerPacket.class);

    private static final File FOLDER = new File("./data/packets");

    private Unmarshaller unmarshaller;

    public AdvSendFakeServerPacket() {
        super("send");

        try {
            this.unmarshaller = JAXBContext.newInstance(new Class[] { Packets.class, Packet.class, Part.class })
                    .createUnmarshaller();
        } catch (Exception e) {

            throw new GameServerError("Failed to initialize unmarshaller.", e);
        }
    }

    public void executeCommand(Player admin, String[] params) {
        Packets packetsTemplate;
        if (admin.getAccessLevel() < AdminConfig.COMMAND_ADVSENDFAKESERVERPACKET) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        if (params.length != 1) {

            PacketSendUtility.sendMessage(admin, "Example: //send [file] ");

            return;
        }
        String mappingName = params[0];
        Player target = getTargetPlayer(admin);

        File packetsData = new File(FOLDER, mappingName + ".xml");

        if (!packetsData.exists()) {

            PacketSendUtility.sendMessage(admin, "Mapping with name " + mappingName + " not found");

            return;
        }

        try {
            packetsTemplate = (Packets) this.unmarshaller.unmarshal(packetsData);
        } catch (JAXBException e) {

            logger.error("Unmarshalling error", e);

            return;
        }
        if (packetsTemplate.getPackets().isEmpty()) {

            PacketSendUtility.sendMessage(admin, "No packets to send.");

            return;
        }
        send(admin, target, packetsTemplate);
    }

  private void send(Player sender, final Player target, Packets packets) {
    String senderObjectId = String.valueOf(sender.getObjectId());
    String targetObjectId = String.valueOf(target.getObjectId());
    
    int packetIndex = 0;
    for (Packet packetTemplate : packets) {


      
      final SM_CUSTOM_PACKET packet = new SM_CUSTOM_PACKET(packetTemplate.getOpcode());
      
      for (Part part : packetTemplate.getParts()) {
        
        SM_CUSTOM_PACKET.PacketElementType byCode = SM_CUSTOM_PACKET.PacketElementType.getByCode(part.getType());
        
        String value = part.getValue();
        
        if (value.indexOf("${objectId}") != -1)
          value = value.replace("${objectId}", targetObjectId); 
        if (value.indexOf("${senderObjectId}") != -1)
          value = value.replace("${senderObjectId}", senderObjectId); 
        if (value.indexOf("${targetObjectId}") != -1) {
          value = value.replace("${targetObjectId}", targetObjectId);
        }
        if (part.getRepeatCount() == 1) {
          
          packet.addElement(byCode, value);
          
          continue;
        } 
        for (int i = 0; i < part.getRepeatCount(); i++) {
          packet.addElement(byCode, value);
        }
      } 
      
      ThreadPoolManager.getInstance().schedule(new Runnable()
          {

            
            public void run()
            {
              PacketSendUtility.sendPacket(target, (AionServerPacket)packet);
            }
          }packetIndex * packets.getDelay());
      
      packetIndex++;
    } 
  }

    private Player getTargetPlayer(Player admin) {
        if (admin.getTarget() instanceof Player) {
            return (Player) admin.getTarget();
        }
        return admin;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "packets")
    private static class Packets implements Iterable<Packet> {
        @XmlElement(name = "packet")
        private List<AdvSendFakeServerPacket.Packet> packets = new ArrayList<AdvSendFakeServerPacket.Packet>();

        @XmlAttribute(name = "delay")
        private long delay = -1L;

        public long getDelay() {
            return this.delay;
        }

        public List<AdvSendFakeServerPacket.Packet> getPackets() {
            return this.packets;
        }

        public boolean add(AdvSendFakeServerPacket.Packet packet) {
            return this.packets.add(packet);
        }

        public Iterator<AdvSendFakeServerPacket.Packet> iterator() {
            return this.packets.iterator();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Packets");
            sb.append("{delay=").append(this.delay);
            sb.append(", packets=").append(this.packets);
            sb.append('}');
            return sb.toString();
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "packet")
    private static class Packet {
        @XmlElement(name = "part")
        private Collection<AdvSendFakeServerPacket.Part> parts = new ArrayList<AdvSendFakeServerPacket.Part>();

        @XmlAttribute(name = "opcode")
        private String opcode = "-1";

        public int getOpcode() {
            return Integer.decode(this.opcode).intValue();
        }

        public Collection<AdvSendFakeServerPacket.Part> getParts() {
            return this.parts;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Packet");
            sb.append("{opcode=").append(this.opcode);
            sb.append(", parts=").append(this.parts);
            sb.append('}');
            return sb.toString();
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "part")
    private static class Part {
        @XmlAttribute(name = "type", required = true)
        private String type = null;

        @XmlAttribute(name = "value", required = true)
        private String value = null;

        @XmlAttribute(name = "repeat", required = true)
        private int repeatCount = 1;

        public char getType() {
            return this.type.charAt(0);
        }

        public String getValue() {
            return this.value;
        }

        public int getRepeatCount() {
            return this.repeatCount;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Part");
            sb.append("{type='").append(this.type).append('\'');
            sb.append(", value='").append(this.value).append('\'');
            sb.append(", repeatCount=").append(this.repeatCount);
            sb.append('}');
            return sb.toString();
        }
    }
}
