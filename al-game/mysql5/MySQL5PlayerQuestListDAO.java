package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.dao.PlayerQuestListDAO;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MySQL5PlayerQuestListDAO extends PlayerQuestListDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerQuestListDAO.class);

    public static final String SELECT_QUERY = "SELECT `quest_id`, `status`, `quest_vars`, `complete_count` FROM `player_quests` WHERE `player_id`=?";

    public static final String UPDATE_QUERY = "UPDATE `player_quests` SET `status`=?, `quest_vars`=?, `complete_count`=? where `player_id`=? AND `quest_id`=?";

    public static final String DELETE_QUERY = "DELETE FROM `player_quests` WHERE `player_id`=? AND `quest_id`=?";

    public static final String INSERT_QUERY = "INSERT INTO `player_quests` (`player_id`, `quest_id`, `status`, `quest_vars`, `complete_count`) VALUES (?,?,?,?,?)";

    public QuestStateList load(Player player) {
        QuestStateList questStateList = new QuestStateList();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT `quest_id`, `status`, `quest_vars`, `complete_count` FROM `player_quests` WHERE `player_id`=?");
            stmt.setInt(1, player.getObjectId());
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {

                int questId = rset.getInt("quest_id");
                int questVars = rset.getInt("quest_vars");
                int completeCount = rset.getInt("complete_count");
                QuestStatus status = QuestStatus.valueOf(rset.getString("status"));
                QuestState questState = new QuestState(questId, status, questVars, completeCount);
                questState.setPersistentState(PersistentState.UPDATED);
                questStateList.addQuest(questId, questState);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore QuestStateList data for player: " + player.getObjectId() + " from DB: "
                    + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return questStateList;
    }

    public void store(Player player) {
        for (QuestState qs : player.getQuestStateList().getAllQuestState()) {

            switch (qs.getPersistentState()) {

                case NEW:
                    addQuest(player.getObjectId(), qs);
                    break;
                case UPDATE_REQUIRED:
                    updateQuest(player.getObjectId(), qs);
                    break;
            }
            qs.setPersistentState(PersistentState.UPDATED);
        }
    }

    private void addQuest(final int playerId, final QuestState qs) {
        DB.insertUpdate(
                "INSERT INTO `player_quests` (`player_id`, `quest_id`, `status`, `quest_vars`, `complete_count`) VALUES (?,?,?,?,?)",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, playerId);
                        stmt.setInt(2, qs.getQuestId());
                        stmt.setString(3, qs.getStatus().toString());
                        stmt.setInt(4, qs.getQuestVars().getQuestVars());
                        stmt.setInt(5, qs.getCompliteCount());
                        stmt.execute();
                    }
                });
    }

    private void updateQuest(final int playerId, final QuestState qs) {
        DB.insertUpdate(
                "UPDATE `player_quests` SET `status`=?, `quest_vars`=?, `complete_count`=? where `player_id`=? AND `quest_id`=?",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setString(1, qs.getStatus().toString());
                        stmt.setInt(2, qs.getQuestVars().getQuestVars());
                        stmt.setInt(3, qs.getCompliteCount());
                        stmt.setInt(4, playerId);
                        stmt.setInt(5, qs.getQuestId());
                        stmt.execute();
                    }
                });
    }

    public void deleteQuest(final int playerId, final int questId) {
        DB.insertUpdate("DELETE FROM `player_quests` WHERE `player_id`=? AND `quest_id`=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
                stmt.setInt(2, questId);
                stmt.execute();
            }
        });
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerQuestListDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
