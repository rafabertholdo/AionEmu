package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.PortalData;
import com.aionemu.gameserver.dataholders.QuestScriptsData;
import com.aionemu.gameserver.dataholders.QuestsData;
import com.aionemu.gameserver.dataholders.SkillData;
import com.aionemu.gameserver.dataholders.SpawnsData;
import com.aionemu.gameserver.dataholders.StaticData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class Reload extends AdminCommand {
  private static final Logger log = Logger.getLogger(Reload.class);

  public Reload() {
    super("reload");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_RELOAD) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length != 1) {

      PacketSendUtility.sendMessage(admin, "syntax //reload <quest | skill | portal | spawn>");
      return;
    }
    if (params[0].equals("quest")) {

      File xml = new File("./data/static_data/quest_data/quest_data.xml");
      File dir = new File("./data/static_data/quest_script_data");

      try {
        QuestEngine.getInstance().shutdown();
        JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
        Unmarshaller un = jc.createUnmarshaller();
        un.setSchema(getSchema("./data/static_data/static_data.xsd"));
        QuestsData newQuestData = (QuestsData) un.unmarshal(xml);
        QuestsData questsData = DataManager.QUEST_DATA;
        questsData.setQuestsData(newQuestData.getQuestsData());
        QuestScriptsData questScriptsData = DataManager.QUEST_SCRIPTS_DATA;
        questScriptsData.getData().clear();
        for (File file : listFiles(dir, true)) {

          QuestScriptsData data = (QuestScriptsData) un.unmarshal(file);
          if (data != null && data.getData() != null)
            questScriptsData.getData().addAll(data.getData());
        }
        QuestEngine.getInstance().load();
      } catch (Exception e) {

        PacketSendUtility.sendMessage(admin, "Quest reload failed!");
        log.error(e);
      } finally {

        PacketSendUtility.sendMessage(admin, "Quest reload Success!");
      }

    } else if (params[0].equals("skill")) {

      File dir = new File("./data/static_data/skills");

      try {
        JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
        Unmarshaller un = jc.createUnmarshaller();
        un.setSchema(getSchema("./data/static_data/static_data.xsd"));
        List<SkillTemplate> newTemplates = new ArrayList<SkillTemplate>();
        for (File file : listFiles(dir, true)) {

          SkillData data = (SkillData) un.unmarshal(file);
          if (data != null)
            newTemplates.addAll(data.getSkillTemplates());
        }
        DataManager.SKILL_DATA.setSkillTemplates(newTemplates);
      } catch (Exception e) {

        PacketSendUtility.sendMessage(admin, "Skill reload failed!");
        log.error(e);
      } finally {

        PacketSendUtility.sendMessage(admin, "Skill reload Success!");
      }

    } else if (params[0].equals("portal")) {

      File dir = new File("./data/static_data/portals");

      try {
        JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
        Unmarshaller un = jc.createUnmarshaller();
        un.setSchema(getSchema("./data/static_data/static_data.xsd"));
        List<PortalTemplate> newTemplates = new ArrayList<PortalTemplate>();
        for (File file : listFiles(dir, true)) {

          PortalData data = (PortalData) un.unmarshal(file);
          if (data != null && data.getPortals() != null)
            newTemplates.addAll(data.getPortals());
        }
        DataManager.PORTAL_DATA.setPortals(newTemplates);
      } catch (Exception e) {

        PacketSendUtility.sendMessage(admin, "Portal reload failed!");
        log.error(e);
      } finally {

        PacketSendUtility.sendMessage(admin, "Portal reload Success!");
      }

    } else if (params[0].equals("spawn")) {

      File dir = new File("./data/static_data/spawns");

      try {
        JAXBContext jc = JAXBContext.newInstance(new Class[] { StaticData.class });
        Unmarshaller un = jc.createUnmarshaller();
        un.setSchema(getSchema("./data/static_data/static_data.xsd"));
        List<SpawnGroup> newTemplates = new ArrayList<SpawnGroup>();
        for (File file : listFiles(dir, true)) {

          SpawnsData data = (SpawnsData) un.unmarshal(file);
          if (data != null && data.getSpawnGroups() != null)
            newTemplates.addAll(data.getSpawnGroups());
        }
        DataManager.SPAWNS_DATA.setSpawns(newTemplates);
      } catch (Exception e) {

        PacketSendUtility.sendMessage(admin, "Spawn reload failed!");
        log.error(e);
      } finally {

        PacketSendUtility.sendMessage(admin, "Spawn reload finished");
      }
    } else {

      PacketSendUtility.sendMessage(admin, "syntax //reload <quest | skill | portal | spawn>");
    }
  }

  private Schema getSchema(String xml_schema) {
    Schema schema = null;
    SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    try {
      schema = sf.newSchema(new File(xml_schema));
    } catch (SAXException saxe) {

      throw new Error("Error while getting schema", saxe);
    }

    return schema;
  }

  private Collection<File> listFiles(File root, boolean recursive) {
    IOFileFilter dirFilter = recursive ? FileFilterUtils.makeSVNAware(HiddenFileFilter.VISIBLE) : null;

    return FileUtils.listFiles(root,
        FileFilterUtils.andFileFilter(
            FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter(FileFilterUtils.prefixFileFilter("new")),
                FileFilterUtils.suffixFileFilter(".xml")),
            HiddenFileFilter.VISIBLE),
        dirFilter);
  }
}
