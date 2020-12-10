package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.WorldMapType;

public class GoTo extends AdminCommand {
    public GoTo() {
        super("goto");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_GOTO) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

            return;
        }
        if (params == null || params.length < 1) {

            PacketSendUtility.sendMessage(admin, "syntax //goto <location>");

            return;
        }
        if (params[0].toLowerCase().equals("poeta")) {

            TeleportService.teleportTo(admin, WorldMapType.POETA.getId(), 806.0F, 1242.0F, 119.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Poeta.");

        } else if (params[0].toLowerCase().equals("verteron")) {

            TeleportService.teleportTo(admin, WorldMapType.VERTERON.getId(), 1643.0F, 1500.0F, 119.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Verteron.");

        } else if (params[0].toLowerCase().equals("eltnen")) {

            TeleportService.teleportTo(admin, WorldMapType.ELTNEN.getId(), 343.0F, 2724.0F, 264.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Eltnen.");

        } else if (params[0].toLowerCase().equals("theobomos")) {

            TeleportService.teleportTo(admin, WorldMapType.THEOMOBOS.getId(), 1398.0F, 1557.0F, 31.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Theobomos.");

        } else if (params[0].toLowerCase().equals("heiron")) {

            TeleportService.teleportTo(admin, WorldMapType.HEIRON.getId(), 2540.0F, 343.0F, 411.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Heiron.");

        } else if (params[0].toLowerCase().equals("sanctum")) {

            TeleportService.teleportTo(admin, WorldMapType.SANCTUM.getId(), 1322.0F, 1511.0F, 568.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Sanctum.");

        } else if (params[0].toLowerCase().equals("ishalgen")) {

            TeleportService.teleportTo(admin, WorldMapType.ISHALGEN.getId(), 529.0F, 2449.0F, 281.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Ishalgen.");

        } else if (params[0].toLowerCase().equals("altgard")) {

            TeleportService.teleportTo(admin, WorldMapType.ALTGARD.getId(), 1748.0F, 1807.0F, 254.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Altgard.");

        } else if (params[0].toLowerCase().equals("morheim")) {

            TeleportService.teleportTo(admin, WorldMapType.MORHEIM.getId(), 308.0F, 2274.0F, 449.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Morheim.");

        } else if (params[0].toLowerCase().equals("brusthonin")) {

            TeleportService.teleportTo(admin, WorldMapType.BRUSTHONIN.getId(), 2917.0F, 2421.0F, 15.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Brusthonin.");

        } else if (params[0].toLowerCase().equals("beluslan")) {

            TeleportService.teleportTo(admin, WorldMapType.BELUSLAN.getId(), 325.0F, 336.0F, 229.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Beluslan.");

        } else if (params[0].toLowerCase().equals("pandaemonium")) {

            TeleportService.teleportTo(admin, WorldMapType.PANDAEMONIUM.getId(), 1679.0F, 1400.0F, 195.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Pandaemonium.");

        } else if (params[0].toLowerCase().equals("abyss1")) {

            TeleportService.teleportTo(admin, 400010000, 2867.0F, 1034.0F, 1528.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Latis Plazza bottom Elyos.");

        } else if (params[0].toLowerCase().equals("abyss2")) {

            TeleportService.teleportTo(admin, 400010000, 1078.0F, 2839.0F, 1636.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Russet Plazza bottom Asmodians.");

        } else if (params[0].toLowerCase().equals("abyss3")) {

            TeleportService.teleportTo(admin, 400010000, 1596.0F, 2952.0F, 2943.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Top Asmodians.");

        } else if (params[0].toLowerCase().equals("abyss4")) {

            TeleportService.teleportTo(admin, 400010000, 2054.0F, 660.0F, 2843.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Top Elyos.");

        } else if (params[0].toLowerCase().equals("abyssfortress")) {

            TeleportService.teleportTo(admin, 400010000, 2130.0F, 1925.0F, 2322.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Core Fortress.");

        } else if (params[0].toLowerCase().equals("senza")) {

            TeleportService.teleportTo(admin, 300010000, 270.0F, 200.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Senza Nome.");

        } else if (params[0].toLowerCase().equals("karamatis1")) {

            TeleportService.teleportTo(admin, 300010000, 270.0F, 200.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Karamatis 1.");

        } else if (params[0].toLowerCase().equals("karamatis2")) {

            TeleportService.teleportTo(admin, 300010000, 270.0F, 200.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Karamatis 2.");

        } else if (params[0].toLowerCase().equals("aerdina")) {

            TeleportService.teleportTo(admin, 300010000, 270.0F, 200.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Aerdina.");

        } else if (params[0].toLowerCase().equals("gerania")) {

            TeleportService.teleportTo(admin, 300010000, 270.0F, 200.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Gerania.");

        } else if (params[0].toLowerCase().equals("lepharist")) {

            TeleportService.teleportTo(admin, 310050000, 225.0F, 244.0F, 133.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Lerpharist Secret Labratory.");

        } else if (params[0].toLowerCase().equals("fragment")) {

            TeleportService.teleportTo(admin, 310070000, 247.0F, 249.0F, 1392.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Fragment of Darkness.");

        } else if (params[0].toLowerCase().equals("sanctumarena")) {

            TeleportService.teleportTo(admin, 310080000, 275.0F, 242.0F, 159.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Sanctum Underground Arena.");

        } else if (params[0].toLowerCase().equals("idratu")) {

            TeleportService.teleportTo(admin, 310090000, 562.0F, 335.0F, 1015.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Idratu Fortress.");

        } else if (params[0].toLowerCase().equals("azoturan")) {

            TeleportService.teleportTo(admin, 310100000, 458.0F, 428.0F, 1039.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Azoturan.");

        } else if (params[0].toLowerCase().equals("ataxiar1")) {

            TeleportService.teleportTo(admin, 320010000, 229.0F, 237.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Narsass 1.");

        } else if (params[0].toLowerCase().equals("ataxiar2")) {

            TeleportService.teleportTo(admin, 320020000, 229.0F, 237.0F, 206.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Narsass 2.");

        } else if (params[0].toLowerCase().equals("bregirun")) {

            TeleportService.teleportTo(admin, 320030000, 264.0F, 214.0F, 210.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Bregirun.");

        } else if (params[0].toLowerCase().equals("nidalber")) {

            TeleportService.teleportTo(admin, 320040000, 264.0F, 214.0F, 210.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Nidalber.");

        } else if (params[0].toLowerCase().equals("skytemple")) {

            TeleportService.teleportTo(admin, 320050000, 177.0F, 229.0F, 536.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Inside of the Sky Temple of Arkanis.");

        } else if (params[0].toLowerCase().equals("space")) {

            TeleportService.teleportTo(admin, 320070000, 246.0F, 246.0F, 125.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Space of Destiny.");

        } else if (params[0].toLowerCase().equals("trinielarena")) {

            TeleportService.teleportTo(admin, 320090000, 275.0F, 239.0F, 159.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Triniel Underground Arena.");

        } else if (params[0].toLowerCase().equals("firetemple")) {

            TeleportService.teleportTo(admin, 320100000, 144.0F, 312.0F, 123.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Fire Temple.");

        } else if (params[0].toLowerCase().equals("reshanta")) {

            TeleportService.teleportTo(admin, 400010000, 951.0F, 936.0F, 1667.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Reshanta.");

        } else if (params[0].toLowerCase().equals("prison1")) {

            TeleportService.teleportTo(admin, 510010000, 256.0F, 256.0F, 49.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to LF Prison.");

        } else if (params[0].toLowerCase().equals("prison2")) {

            TeleportService.teleportTo(admin, 520010000, 256.0F, 256.0F, 49.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to DF Prison.");

        } else if (params[0].toLowerCase().equals("test1")) {

            TeleportService.teleportTo(admin, 900100000, 196.0F, 187.0F, 20.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Test Giant Monster.");

        } else if (params[0].toLowerCase().equals("test2")) {

            TeleportService.teleportTo(admin, 900020000, 144.0F, 136.0F, 20.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Test Basic.");

        } else if (params[0].toLowerCase().equals("test3")) {

            TeleportService.teleportTo(admin, 900030000, 228.0F, 171.0F, 49.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Test Server.");
        } else if (params[0].toLowerCase().equals("steelrake")) {

            TeleportService.teleportTo(admin, 300100000, 237.0F, 505.0F, 949.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Steel Rake.");

        } else if (params[0].toLowerCase().equals("kaisinel")) {

            TeleportService.teleportTo(admin, 110020000, 2155.0F, 1567.0F, 1205.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Kaisinel.");
        } else if (params[0].toLowerCase().equals("marchutan")) {

            TeleportService.teleportTo(admin, 120020000, 1557.0F, 1429.0F, 266.0F, 0);
            PacketSendUtility.sendMessage(admin, "Teleported to Marchutan.");
        } else {

            PacketSendUtility.sendMessage(admin, "Target location was not found!");
        }
    }
}
