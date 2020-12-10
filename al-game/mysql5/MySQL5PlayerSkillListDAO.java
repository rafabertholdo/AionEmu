package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.dao.PlayerSkillListDAO;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillList;
import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class MySQL5PlayerSkillListDAO extends PlayerSkillListDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerSkillListDAO.class);

    public static final String INSERT_QUERY = "INSERT INTO `player_skills` (`player_id`, `skillId`, `skillLevel`) VALUES (?,?,?)";

    public static final String UPDATE_QUERY = "UPDATE `player_skills` set skillLevel=? where player_id=? AND skillId=?";

    public static final String DELETE_QUERY = "DELETE FROM `player_skills` WHERE `player_id`=? AND skillId=?";

    public static final String SELECT_QUERY = "SELECT `skillId`, `skillLevel` FROM `player_skills` WHERE `player_id`=?";

    public SkillList loadSkillList(int playerId) {
        Map<Integer, SkillListEntry> skills = new HashMap<Integer, SkillListEntry>();
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT `skillId`, `skillLevel` FROM `player_skills` WHERE `player_id`=?");
            stmt.setInt(1, playerId);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {

                int id = rset.getInt("skillId");
                int lv = rset.getInt("skillLevel");

                skills.put(Integer.valueOf(id), new SkillListEntry(id, false, lv, PersistentState.UPDATED));
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore SkillList data for player: " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return new SkillList(skills);
    }

    public boolean storeSkills(Player player) {
        SkillListEntry[] skillsActive = player.getSkillList().getAllSkills();
        SkillListEntry[] skillsDeleted = player.getSkillList().getDeletedSkills();
        store(player, skillsActive);
        store(player, skillsDeleted);

        return true;
    }

    private void store(Player player, SkillListEntry[] skills) {
        for (int i = 0; i < skills.length; i++) {

            SkillListEntry skill = skills[i];
            switch (skill.getPersistentState()) {

                case NEW:
                    addSkill(player.getObjectId(), skill.getSkillId(), skill.getSkillLevel());
                    break;
                case UPDATE_REQUIRED:
                    updateSkill(player.getObjectId(), skill.getSkillId(), skill.getSkillLevel());
                    break;
                case DELETED:
                    deleteSkill(player.getObjectId(), skill.getSkillId());
                    break;
            }
            skill.setPersistentState(PersistentState.UPDATED);
        }
    }

    private void addSkill(final int playerId, final int skillId, final int skillLevel) {
        DB.insertUpdate("INSERT INTO `player_skills` (`player_id`, `skillId`, `skillLevel`) VALUES (?,?,?)",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, playerId);
                        stmt.setInt(2, skillId);
                        stmt.setInt(3, skillLevel);
                        stmt.execute();
                    }
                });
    }

    private void updateSkill(final int playerId, final int skillId, final int skillLevel) {
        DB.insertUpdate("UPDATE `player_skills` set skillLevel=? where player_id=? AND skillId=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, skillLevel);
                stmt.setInt(2, playerId);
                stmt.setInt(3, skillId);
                stmt.execute();
            }
        });
    }

    private void deleteSkill(final int playerId, final int skillId) {
        DB.insertUpdate("DELETE FROM `player_skills` WHERE `player_id`=? AND skillId=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
                stmt.setInt(2, skillId);
                stmt.execute();
            }
        });
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
