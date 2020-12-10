package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.LegionMemberDAO;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.legion.LegionMemberEx;
import com.aionemu.gameserver.model.legion.LegionRank;
import com.aionemu.gameserver.services.LegionService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class MySQL5LegionMemberDAO extends LegionMemberDAO {
    private static final Logger log = Logger.getLogger(MySQL5LegionMemberDAO.class);

    private static final String INSERT_LEGIONMEMBER_QUERY = "INSERT INTO legion_members(`legion_id`, `player_id`, `rank`) VALUES (?, ?, ?)";

    private static final String UPDATE_LEGIONMEMBER_QUERY = "UPDATE legion_members SET nickname=?, rank=?, selfintro=? WHERE player_id=?";

    private static final String SELECT_LEGIONMEMBER_QUERY = "SELECT * FROM legion_members WHERE player_id = ?";

    private static final String DELETE_LEGIONMEMBER_QUERY = "DELETE FROM legion_members WHERE player_id = ?";

    private static final String SELECT_LEGIONMEMBERS_QUERY = "SELECT player_id FROM legion_members WHERE legion_id = ?";

    private static final String SELECT_LEGIONMEMBEREX_QUERY = "SELECT players.name, players.exp, players.player_class, players.last_online, players.world_id, legion_members.* FROM players, legion_members WHERE id = ? AND players.id=legion_members.player_id";

    private static final String SELECT_LEGIONMEMBEREX2_QUERY = "SELECT players.id, players.exp, players.player_class, players.last_online, players.world_id, legion_members.* FROM players, legion_members WHERE name = ? AND players.id=legion_members.player_id";

    public boolean isIdUsed(int playerObjId) {
        PreparedStatement s = DB
                .prepareStatement("SELECT count(id) as cnt FROM legion_members WHERE ? = legion_members.player_id");

        try {
            s.setInt(1, playerObjId);
            ResultSet rs = s.executeQuery();
            rs.next();
            return (rs.getInt("cnt") > 0);
        } catch (SQLException e) {

            log.error("Can't check if name " + playerObjId + ", is used, returning possitive result", e);
            return true;
        } finally {

            DB.close(s);
        }
    }

    public boolean saveNewLegionMember(final LegionMember legionMember) {
        boolean success = DB.insertUpdate(
                "INSERT INTO legion_members(`legion_id`, `player_id`, `rank`) VALUES (?, ?, ?)", new IUStH() {
                    public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, legionMember.getLegion().getLegionId());
                        preparedStatement.setInt(2, legionMember.getObjectId());
                        preparedStatement.setString(3, legionMember.getRank().toString());
                        preparedStatement.execute();
                    }
                });
        return success;
    }

    public void storeLegionMember(final int playerId, final LegionMember legionMember) {
        DB.insertUpdate("UPDATE legion_members SET nickname=?, rank=?, selfintro=? WHERE player_id=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, legionMember.getNickname());
                stmt.setString(2, legionMember.getRank().toString());
                stmt.setString(3, legionMember.getSelfIntro());
                stmt.setInt(4, playerId);
                stmt.execute();
            }
        });
    }

    public LegionMember loadLegionMember(final int playerObjId) {
        if (playerObjId == 0) {
            return null;
        }
        final LegionMember legionMember = new LegionMember(playerObjId);

        boolean success = DB.select("SELECT * FROM legion_members WHERE player_id = ?", (ReadStH) new ParamReadStH() {
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
            }

            public void handleRead(ResultSet resultSet) {
                try {
                    resultSet.next();
                    int legionId = resultSet.getInt("legion_id");
                    legionMember.setRank(LegionRank.valueOf(resultSet.getString("rank")));
                    legionMember.setNickname(resultSet.getString("nickname"));
                    legionMember.setSelfIntro(resultSet.getString("selfintro"));

                    legionMember.setLegion(LegionService.getInstance().getLegion(legionId));
                } catch (SQLException sqlE) {

                    MySQL5LegionMemberDAO.log.debug("[DAO: MySQL5LegionMemberDAO] Player is not in a Legion");
                }
            }
        });

        if (success && legionMember.getLegion() != null) {
            return legionMember;
        }

        return null;
    }

    public LegionMemberEx loadLegionMemberEx(final int playerObjId) {
        final LegionMemberEx legionMemberEx = new LegionMemberEx(playerObjId);

        boolean success = DB.select(
                "SELECT players.name, players.exp, players.player_class, players.last_online, players.world_id, legion_members.* FROM players, legion_members WHERE id = ? AND players.id=legion_members.player_id",
                (ReadStH) new ParamReadStH() {
                    public void setParams(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, playerObjId);
                    }

                    public void handleRead(ResultSet resultSet) {
                        try {
                            resultSet.next();
                            legionMemberEx.setName(resultSet.getString("players.name"));
                            legionMemberEx.setExp(resultSet.getLong("players.exp"));
                            legionMemberEx
                                    .setPlayerClass(PlayerClass.valueOf(resultSet.getString("players.player_class")));
                            legionMemberEx.setLastOnline(resultSet.getTimestamp("players.last_online"));
                            legionMemberEx.setWorldId(resultSet.getInt("players.world_id"));

                            int legionId = resultSet.getInt("legion_members.legion_id");
                            legionMemberEx.setRank(LegionRank.valueOf(resultSet.getString("legion_members.rank")));
                            legionMemberEx.setNickname(resultSet.getString("legion_members.nickname"));
                            legionMemberEx.setSelfIntro(resultSet.getString("legion_members.selfintro"));

                            legionMemberEx.setLegion(LegionService.getInstance().getLegion(legionId));
                        } catch (SQLException sqlE) {

                            MySQL5LegionMemberDAO.log.debug("[DAO: MySQL5LegionMemberDAO] Player is not in a Legion");
                        }
                    }
                });

        if (success && legionMemberEx.getLegion() != null) {
            return legionMemberEx;
        }

        return null;
    }

    public LegionMemberEx loadLegionMemberEx(final String playerName) {
        final LegionMemberEx legionMember = new LegionMemberEx(playerName);

        boolean success = DB.select(
                "SELECT players.id, players.exp, players.player_class, players.last_online, players.world_id, legion_members.* FROM players, legion_members WHERE name = ? AND players.id=legion_members.player_id",
                (ReadStH) new ParamReadStH() {
                    public void setParams(PreparedStatement stmt) throws SQLException {
                        stmt.setString(1, playerName);
                    }

                    public void handleRead(ResultSet resultSet) {
                        try {
                            resultSet.next();
                            legionMember.setObjectId(resultSet.getInt("id"));
                            legionMember.setExp(resultSet.getLong("exp"));
                            legionMember.setPlayerClass(PlayerClass.valueOf(resultSet.getString("player_class")));
                            legionMember.setLastOnline(resultSet.getTimestamp("last_online"));
                            legionMember.setWorldId(resultSet.getInt("world_id"));

                            int legionId = resultSet.getInt("legion_id");
                            legionMember.setRank(LegionRank.valueOf(resultSet.getString("rank")));
                            legionMember.setNickname(resultSet.getString("nickname"));
                            legionMember.setSelfIntro(resultSet.getString("selfintro"));

                            legionMember.setLegion(LegionService.getInstance().getLegion(legionId));
                        } catch (SQLException sqlE) {

                            MySQL5LegionMemberDAO.log.debug("[DAO: MySQL5LegionMemberDAO] Player is not in a Legion");
                        }
                    }
                });

        if (success && legionMember.getLegion() != null) {
            return legionMember;
        }

        return null;
    }

    public ArrayList<Integer> loadLegionMembers(final int legionId) {
        final ArrayList<Integer> legionMembers = new ArrayList<Integer>();

        boolean success = DB.select("SELECT player_id FROM legion_members WHERE legion_id = ?",
                (ReadStH) new ParamReadStH() {
                    public void setParams(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, legionId);
                    }

                    public void handleRead(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                int playerObjId = resultSet.getInt("player_id");
                                legionMembers.add(Integer.valueOf(playerObjId));
                            }

                        } catch (SQLException sqlE) {

                            MySQL5LegionMemberDAO.log.error(
                                    "[DAO: MySQL5LegionMemberDAO] No players in Legion. DELETE Legion Id: " + legionId);
                        }
                    }
                });

        if (success && legionMembers.size() > 0) {
            return legionMembers;
        }

        return null;
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }

    public void deleteLegionMember(int playerObjId) {
        PreparedStatement statement = DB.prepareStatement("DELETE FROM legion_members WHERE player_id = ?");

        try {
            statement.setInt(1, playerObjId);
        } catch (SQLException e) {

            log.error("Some crap, can't set int parameter to PreparedStatement", e);
        }
        DB.executeUpdateAndClose(statement);
    }

    public int[] getUsedIDs() {
        return null;
    }
}
