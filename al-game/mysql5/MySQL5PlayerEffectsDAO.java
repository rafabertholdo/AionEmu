package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.PlayerEffectsDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class MySQL5PlayerEffectsDAO extends PlayerEffectsDAO {
    public static final String INSERT_QUERY = "INSERT INTO `player_effects` (`player_id`, `skill_id`, `skill_lvl`, `current_time`, `reuse_delay`) VALUES (?,?,?,?,?)";
    public static final String DELETE_QUERY = "DELETE FROM `player_effects` WHERE `player_id`=?";
    public static final String SELECT_QUERY = "SELECT `skill_id`, `skill_lvl`, `current_time`, `reuse_delay` FROM `player_effects` WHERE `player_id`=?";

    public void loadPlayerEffects(final Player player) {
        DB.select(
                "SELECT `skill_id`, `skill_lvl`, `current_time`, `reuse_delay` FROM `player_effects` WHERE `player_id`=?",
                (ReadStH) new ParamReadStH() {

                    public void setParams(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, player.getObjectId());
                    }

                    public void handleRead(ResultSet rset) throws SQLException {
                        while (rset.next()) {

                            int skillId = rset.getInt("skill_id");
                            int skillLvl = rset.getInt("skill_lvl");
                            int currentTime = rset.getInt("current_time");
                            long reuseDelay = rset.getLong("reuse_delay");

                            if (currentTime > 0) {
                                player.getEffectController().addSavedEffect(skillId, skillLvl, currentTime);
                            }
                            if (reuseDelay > System.currentTimeMillis()) {
                                player.setSkillCoolDown(skillId, reuseDelay);
                            }
                        }
                    }
                });
        player.getEffectController().broadCastEffects();
    }

    public void storePlayerEffects(final Player player) {
        deletePlayerEffects(player);
        Iterator<Effect> iterator = player.getEffectController().iterator();

        while (iterator.hasNext()) {

            final Effect effect = iterator.next();
            int elapsedTime = effect.getElapsedTime();

            if (elapsedTime < 60000) {
                continue;
            }
            DB.insertUpdate(
                    "INSERT INTO `player_effects` (`player_id`, `skill_id`, `skill_lvl`, `current_time`, `reuse_delay`) VALUES (?,?,?,?,?)",
                    new IUStH() {
                        public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                            stmt.setInt(1, player.getObjectId());
                            stmt.setInt(2, effect.getSkillId());
                            stmt.setInt(3, effect.getSkillLevel());
                            stmt.setInt(4, effect.getCurrentTime());

                            long reuseTime = player.getSkillCoolDown(effect.getSkillId());
                            player.removeSkillCoolDown(effect.getSkillId());

                            stmt.setLong(5, reuseTime);
                            stmt.execute();
                        }
                    });
        }

        Map<Integer, Long> cooldowns = player.getSkillCoolDowns();
        if (cooldowns != null) {
            for (Map.Entry<Integer, Long> entry : cooldowns.entrySet()) {

                final int skillId = ((Integer) entry.getKey()).intValue();
                final long reuseTime = ((Long) entry.getValue()).longValue();
                if (reuseTime - System.currentTimeMillis() < 60000L) {
                    continue;
                }
                DB.insertUpdate(
                        "INSERT INTO `player_effects` (`player_id`, `skill_id`, `skill_lvl`, `current_time`, `reuse_delay`) VALUES (?,?,?,?,?)",
                        new IUStH() {
                            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                                stmt.setInt(1, player.getObjectId());
                                stmt.setInt(2, skillId);
                                stmt.setInt(3, 0);
                                stmt.setInt(4, 0);
                                stmt.setLong(5, reuseTime);
                                stmt.execute();
                            }
                        });
            }
        }
    }

    private void deletePlayerEffects(final Player player) {
        DB.insertUpdate("DELETE FROM `player_effects` WHERE `player_id`=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, player.getObjectId());
                stmt.execute();
            }
        });
    }

    public boolean supports(String arg0, int arg1, int arg2) {
        return MySQL5DAOUtils.supports(arg0, arg1, arg2);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerEffectsDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */