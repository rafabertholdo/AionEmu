package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.SpawnsData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class SaveSpawnData extends AdminCommand {
  private static Logger log = Logger.getLogger(SaveSpawnData.class);

  public SaveSpawnData() {
    super("save_spawn");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SAVESPAWNDATA) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    Schema schema = null;
    SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    try {
      schema = sf.newSchema(new File("./data/static_data/spawns/spawns.xsd"));
    } catch (SAXException e1) {

      log.error(e1.getCause());
      PacketSendUtility.sendMessage(admin, "Unexpected error occured during saving");

      return;
    }
    boolean isAllSave = (params.length == 1 && "all".equalsIgnoreCase(params[0]));

    SpawnsData spawnsData = DataManager.SPAWNS_DATA;
    for (WorldMapTemplate template : DataManager.WORLD_MAPS_DATA) {

      List<SpawnGroup> spawnsForWorld = null;
      if (isAllSave) {
        spawnsForWorld = spawnsData.getSpawnsForWorld(template.getMapId().intValue());
      } else {
        spawnsForWorld = spawnsData.getNewSpawnsForWorld(template.getMapId().intValue());
      }
      if (spawnsForWorld != null && spawnsForWorld.size() > 0) {

        SpawnsData data = new SpawnsData();
        data.getSpawnGroups().addAll(spawnsForWorld);

        File xml = new File("./data/static_data/spawns/new/" + template.getMapId() + ".xml");

        try {
          JAXBContext jc = JAXBContext.newInstance(new Class[] { SpawnsData.class });
          Marshaller marshaller = jc.createMarshaller();
          marshaller.setSchema(schema);
          marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
          marshaller.marshal(data, xml);
        } catch (JAXBException e) {

          log.error(e.getCause());
          PacketSendUtility.sendMessage(admin, "Unexpected error occured during saving");
          return;
        }
      }
    }
    PacketSendUtility.sendMessage(admin, "Spawn data was saved into /static_data/spawns/new folder");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\SaveSpawnData.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */