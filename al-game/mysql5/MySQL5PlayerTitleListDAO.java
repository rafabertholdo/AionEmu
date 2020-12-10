package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.PlayerTitleListDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Title;
import com.aionemu.gameserver.model.gameobjects.player.TitleList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL5PlayerTitleListDAO extends PlayerTitleListDAO {
    private static final String LOAD_QUERY = "SELECT `title_id` FROM `player_titles` WHERE `player_id`=?";
    private static final String INSERT_QUERY = "INSERT INTO `player_titles`(`player_id`,`title_id`) VALUES (?,?)";
    private static final String CHECK_QUERY = "SELECT `title_id` FROM `player_titles` WHERE `player_id`=? AND `title_id`=?";

    public TitleList loadTitleList(final int playerId) {
        final TitleList tl = new TitleList();

        DB.select("SELECT `title_id` FROM `player_titles` WHERE `player_id`=?", (ReadStH) new ParamReadStH() {

            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
            }

            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {

                    int id = rset.getInt("title_id");
                    tl.addTitle(id);
                }
            }
        });

        return tl;
    }

    public boolean storeTitles(Player player) {
        final int playerId = player.getObjectId();

        for (Title t : player.getTitleList().getTitles()) {

            DB.select("SELECT `title_id` FROM `player_titles` WHERE `player_id`=? AND `title_id`=?",
                    (ReadStH) new ParamReadStH() {

                        public void setParams(PreparedStatement stmt) throws SQLException {
                            stmt.setInt(1, playerId);
                            stmt.setInt(2, t.getTemplate().getTitleId());
                        }

                        public void handleRead(ResultSet rset) throws SQLException {
                            if (!rset.next()) {
                                DB.insertUpdate("INSERT INTO `player_titles`(`player_id`,`title_id`) VALUES (?,?)",
                                        new IUStH() {
                                            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                                                stmt.setInt(1, playerId);
                                                stmt.setInt(2, t.getTemplate().getTitleId());
                                                stmt.execute();
                                            }
                                        });
                            }
                        }
                    });
        }

        return true;
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerTitleListDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */