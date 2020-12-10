package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.DropListDAO;
import com.aionemu.gameserver.model.drop.DropList;
import com.aionemu.gameserver.model.drop.DropTemplate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL5DropListDAO extends DropListDAO {
    public static final String SELECT_QUERY = "SELECT * FROM `droplist`";

    public DropList load() {
        final DropList dropList = new DropList();

        DB.select("SELECT * FROM `droplist`", (ReadStH) new ParamReadStH() {
            public void setParams(PreparedStatement stmt) throws SQLException {
            }

            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {

                    int mobId = rset.getInt("mobId");
                    int itemId = rset.getInt("itemId");
                    int min = rset.getInt("min");
                    int max = rset.getInt("max");
                    float chance = rset.getFloat("chance");

                    if (chance > 0.0F) {

                        DropTemplate dropTemplate = new DropTemplate(mobId, itemId, min, max, chance);
                        dropList.addDropTemplate(mobId, dropTemplate);
                    }
                }
            }
        });

        return dropList;
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5DropListDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
