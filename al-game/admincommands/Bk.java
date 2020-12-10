package admincommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.ShutdownHook;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class Bk extends AdminCommand {
    ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
    private static final Logger log = Logger.getLogger(ShutdownHook.class);
    private String bookmark_name = "";

    Player admin;

    public Bk() {
        super("bk");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_BK) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        if (params == null || params.length < 1) {

            PacketSendUtility.sendMessage(admin, "syntax //bk <add|del|tele|list>");

            return;
        }

        this.admin = admin;

        if (params[0].equals("add")) {

            try {
                this.bookmark_name = params[1].toLowerCase();
                if (isBookmarkExists(this.bookmark_name)) {

                    PacketSendUtility.sendMessage(admin, "Bookmark " + this.bookmark_name + " already exists !");

                    return;
                }
                final float x = admin.getX();
                final float y = admin.getY();
                final float z = admin.getZ();
                final int char_id = admin.getObjectId();
                final int world_id = admin.getWorldId();

                DB.insertUpdate(
                        "INSERT INTO bookmark (`name`,`char_id`, `x`, `y`, `z`,`world_id` ) VALUES (?, ?, ?, ?, ?, ?)",
                        new IUStH() {

                            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                                ps.setString(1, Bk.this.bookmark_name);
                                ps.setInt(2, char_id);
                                ps.setFloat(3, x);
                                ps.setFloat(4, y);
                                ps.setFloat(5, z);
                                ps.setInt(6, world_id);
                                ps.execute();
                            }
                        });

                PacketSendUtility.sendMessage(admin,
                        "Bookmark " + this.bookmark_name + " sucessfully added to your bookmark list!");

                updateInfo();
            } catch (Exception e) {

                PacketSendUtility.sendMessage(admin, "syntax //bk <add|del|tele> <bookmark name>");

                return;
            }
        } else if (params[0].equals("del")) {

            Connection con = null;

            try {
                this.bookmark_name = params[1].toLowerCase();
                con = DatabaseFactory.getConnection();

                PreparedStatement statement = con.prepareStatement("DELETE FROM bookmark WHERE name = ?");
                statement.setString(1, this.bookmark_name);
                statement.executeUpdate();
                statement.close();
            } catch (Exception e) {

                PacketSendUtility.sendMessage(admin, "syntax //bk <add|del|tele> <bookmark name>");

                return;
            } finally {
                DatabaseFactory.close(con);
                PacketSendUtility.sendMessage(admin,
                        "Bookmark " + this.bookmark_name + " sucessfully removed from your bookmark list!");

                updateInfo();
            }

        } else if (params[0].equals("tele")) {

            try {

                if (params[1].equals("") || params[1] == null) {

                    PacketSendUtility.sendMessage(admin, "syntax //bk <add|del|tele> <bookmark name>");

                    return;
                }
                updateInfo();

                this.bookmark_name = params[1].toLowerCase();
                Bookmark tele_bk = null;

                try {
                    tele_bk = selectByName(this.bookmark_name);
                } finally {

                    if (tele_bk != null) {
                        TeleportService.teleportTo(admin, tele_bk.getWorld_id(), tele_bk.getX(), tele_bk.getY(),
                                tele_bk.getZ(), 0);

                        PacketSendUtility.sendMessage(admin,
                                "Teleported to bookmark " + tele_bk.getName() + " location");
                    }

                }

            } catch (Exception e) {

                PacketSendUtility.sendMessage(admin, "syntax //bk <add|del|tele> <bookmark name>");

                return;
            }
        } else if (params[0].equals("list")) {

            updateInfo();
            PacketSendUtility.sendMessage(admin, "=====Bookmark list begin=====");
            for (Bookmark b : this.bookmarks) {
                PacketSendUtility.sendMessage(admin,
                        " = " + b.getName() + " =  -  ( " + b.getX() + " ," + b.getY() + " ," + b.getZ() + " )");
            }

            PacketSendUtility.sendMessage(admin, "=====Bookmark list end=======");
        }
    }

    public void updateInfo() {
        this.bookmarks.clear();

        DB.select("SELECT * FROM `bookmark` where char_id= ?", (ReadStH) new ParamReadStH() {
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, Bk.this.admin.getObjectId());
            }

            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {

                    String name = rset.getString("name");
                    float x = rset.getFloat("x");
                    float y = rset.getFloat("y");
                    float z = rset.getFloat("z");
                    int world_id = rset.getInt("world_id");
                    Bk.this.bookmarks.add(new Bookmark(x, y, z, world_id, name));
                }
            }
        });
    }

    public Bookmark selectByName(String bk_name) {
        for (Bookmark b : this.bookmarks) {

            if (b.getName().equals(bk_name)) {
                return b;
            }
        }
        return null;
    }

    public boolean isBookmarkExists(String bk_name) {
        Connection con = null;
        int bkcount = 0;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement statement = con
                    .prepareStatement("SELECT count(id) as bkcount FROM bookmark WHERE ? = name AND char_id = ?");
            statement.setString(1, bk_name);
            statement.setInt(2, this.admin.getObjectId());
            ResultSet rset = statement.executeQuery();
            while (rset.next())
                bkcount = rset.getInt("bkcount");
            rset.close();
            statement.close();
        } catch (Exception e) {

            log.error("Error in reading db", e);
        } finally {

            DatabaseFactory.close(con);
        }
        return (bkcount > 0);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Bk.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
