package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.PetitionDAO;
import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.model.PetitionStatus;
import com.mysql.jdbc.exceptions.MySQLDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

public class MySQL5PetitionDAO extends PetitionDAO {
    private static final Logger log = Logger.getLogger(MySQL5PetitionDAO.class);

    public synchronized int getNextAvailableId() {
        Connection con = null;
        int result = 0;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT MAX(id) as nextid FROM petitions");
            ResultSet rset = stmt.executeQuery();
            rset.next();
            result = rset.getInt("nextid") + 1;
            stmt.close();
        } catch (Exception e) {

            log.error("Cannot get next available petition id", e);
            return 0;
        } finally {

            DatabaseFactory.close(con);
        }
        return result;
    }

    public Petition getPetitionById(int petitionId) {
        String query = "SELECT * FROM petitions WHERE id = ?";
        Connection con = null;
        Petition result = null;
        try {
            PetitionStatus status;
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, petitionId);
            ResultSet rset = stmt.executeQuery();
            if (!rset.next()) {
                throw new MySQLDataException();
            }

            String statusValue = rset.getString("status");

            if (statusValue.equals("PENDING")) {
                status = PetitionStatus.PENDING;
            } else if (statusValue.equals("IN_PROGRESS")) {
                status = PetitionStatus.IN_PROGRESS;
            } else {
                status = PetitionStatus.PENDING;
            }
            result = new Petition(rset.getInt("id"), rset.getInt("playerId"), rset.getInt("type"),
                    rset.getString("title"), rset.getString("message"), rset.getString("addData"),
                    status.getElementId());

            stmt.close();
        } catch (MySQLDataException mde) {

        } catch (Exception e) {

            log.error("Cannot get petition #" + petitionId, e);
        } finally {

            DatabaseFactory.close(con);
        }
        return result;
    }

    public Set<Petition> getPetitions() {
        String query = "SELECT * FROM petitions WHERE status = 'PENDING' OR status = 'IN_PROGRESS' ORDER BY id ASC";
        Connection con = null;
        Set<Petition> results = new HashSet<Petition>();

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                PetitionStatus status;
                String statusValue = rset.getString("status");

                if (statusValue.equals("PENDING")) {
                    status = PetitionStatus.PENDING;
                } else if (statusValue.equals("IN_PROGRESS")) {
                    status = PetitionStatus.IN_PROGRESS;
                } else {
                    status = PetitionStatus.PENDING;
                }
                Petition p = new Petition(rset.getInt("id"), rset.getInt("playerId"), rset.getInt("type"),
                        rset.getString("title"), rset.getString("message"), rset.getString("addData"),
                        status.getElementId());
                results.add(p);
            }
            stmt.close();
        } catch (Exception e) {

            log.error("Cannot get next available petition id", e);
            return null;
        } finally {

            DatabaseFactory.close(con);
        }
        return results;
    }

    public void deletePetition(int playerObjId) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM petitions WHERE playerId = ? AND (status = 'PENDING' OR status='IN_PROGRESS')");
            stmt.setInt(1, playerObjId);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Cannot delete petition", e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void insertPetition(Petition petition) {
        Connection con = null;
        String query = "INSERT INTO petitions (id, playerId, type, title, message, addData, time, status) VALUES(?,?,?,?,?,?,?,?)";

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, petition.getPetitionId());
            stmt.setInt(2, petition.getPlayerObjId());
            stmt.setInt(3, petition.getPetitionType().getElementId());
            stmt.setString(4, petition.getTitle());
            stmt.setString(5, petition.getContentText());
            stmt.setString(6, petition.getAdditionalData());
            stmt.setLong(7, (new Date()).getTime() / 1000L);
            stmt.setString(8, petition.getStatus().toString());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Cannot insert petition", e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void setReplied(int petitionId) {
        Connection con = null;
        String query = "UPDATE petitions SET status = 'REPLIED' WHERE id = ?";

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, petitionId);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Cannot set petition replied", e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
