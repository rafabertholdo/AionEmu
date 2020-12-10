package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.PlayerRecipesDAO;
import com.aionemu.gameserver.model.gameobjects.player.RecipeList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import org.apache.log4j.Logger;

public class MySQL5PlayerRecipesDAO extends PlayerRecipesDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerRecipesDAO.class);

    private static final String SELECT_QUERY = "SELECT `recipe_id` FROM player_recipes WHERE `player_id`=?";

    private static final String ADD_QUERY = "INSERT INTO player_recipes (`player_id`, `recipe_id`) VALUES (?, ?)";

    private static final String DELETE_QUERY = "DELETE FROM player_recipes WHERE `player_id`=? AND `recipe_id`=?";

    public RecipeList load(final int playerId) {
        final HashSet<Integer> recipeList = new HashSet<Integer>();
        DB.select("SELECT `recipe_id` FROM player_recipes WHERE `player_id`=?", (ReadStH) new ParamReadStH() {
            public void setParams(PreparedStatement ps) throws SQLException {
                ps.setInt(1, playerId);
            }

            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    recipeList.add(Integer.valueOf(rs.getInt("recipe_id")));
                }
            }
        });
        return new RecipeList(recipeList);
    }

    public boolean addRecipe(final int playerId, final int recipeId) {
        return DB.insertUpdate("INSERT INTO player_recipes (`player_id`, `recipe_id`) VALUES (?, ?)", new IUStH() {
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, playerId);
                ps.setInt(2, recipeId);
                ps.execute();
            }
        });
    }

    public boolean deleteRecipe(int playerId, int recipeId) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("DELETE FROM player_recipes WHERE `player_id`=? AND `recipe_id`=?");

            stmt.setInt(1, playerId);
            stmt.setInt(2, recipeId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerRecipesDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
