package P2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class ReizigerDaoImpl extends OracleBaseDao implements ReizigerDao {
	
public List<Reiziger> findAll() throws SQLException {
		
		Connection conn = super.getConnection();
		
		String getReizigers = "select * from reiziger";
		
		PreparedStatement pstmt1 = conn.prepareStatement(getReizigers);
		
		ResultSet rs = pstmt1.executeQuery();
		
		int id;
		String voorletters;
		String tussenvoegsels;
		String achternaam;
		Date geboortedatum;

		
		List<Reiziger> reizigers = new ArrayList<>();
		
		while (rs.next()) { 
			
			ovChipkaartDao OV = new OvChipkaartDaoImpl();
			List<OvChipkaart> ov1 = OV.findByReiziger(rs.getInt("reizigerid"));
			
			id = rs.getInt("reizigerid");
			voorletters = rs.getString("voorletters");
			tussenvoegsels = rs.getString("tussenvoegsel");
			achternaam = rs.getString("achternaam");
			geboortedatum = rs.getDate("geboortedatum");
			
			Reiziger r = new Reiziger(id, voorletters, tussenvoegsels, achternaam, geboortedatum,ov1);
			reizigers.add(r);
	
		}
		
		return reizigers;
		
	}

public List<Reiziger> findByGBdatum(String GBdatum) throws SQLException {
	
	Connection conn = super.getConnection();
	
	String getReizigers = "select * from reiziger where geboortedatum = ?";
	
	PreparedStatement pstmt1 = conn.prepareStatement(getReizigers);
	
	pstmt1.setString(1, GBdatum);
	
	ResultSet rs = pstmt1.executeQuery();
	
	int id;
	String voorletters;
	String tussenvoegsels;
	String achternaam;
	Date geboortedatum;
	
	List<Reiziger> reizigers = new ArrayList<>();
	
	while (rs.next()) {   
		ovChipkaartDao OV = new OvChipkaartDaoImpl();
		List<OvChipkaart> ov1 = OV.findByReiziger(rs.getInt("reizigerid"));
		
		id = rs.getInt("reizigerid");
		voorletters = rs.getString("voorletters");
		tussenvoegsels = rs.getString("tussenvoegsel");
		achternaam = rs.getString("achternaam");
		geboortedatum = rs.getDate("geboortedatum");
		
		Reiziger r = new Reiziger(id, voorletters, tussenvoegsels, achternaam, geboortedatum,ov1);
		reizigers.add(r);

		
		
	}
	
	return reizigers;
	
}

public Reiziger update(Reiziger reiziger) throws SQLException {
	
	Connection conn = super.getConnection();
	
	String updateReiziger = "update reiziger set voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? where reizigerid = ?";
	
	PreparedStatement pstmt1 = conn.prepareStatement(updateReiziger);
	
	pstmt1.setString(1, reiziger.getVoorletters());
	pstmt1.setString(2, reiziger.getTussenvoegsels());
	pstmt1.setString(3, reiziger.getAchternaam());
	pstmt1.setDate(4, reiziger.getGbdatum());
	pstmt1.setInt(5, reiziger.getReizigerID());
	
	if (pstmt1.executeUpdate() > 0) {
		
		return reiziger;
			
	}
	
	return null;
}

public Reiziger save(Reiziger reiziger) throws SQLException {
	
	Connection conn = super.getConnection();
	
	String saveReiziger = "insert into REIZIGER values (?, ?, ?, ?, ?)";
	
	PreparedStatement pstmt1 = conn.prepareStatement(saveReiziger);
	
	pstmt1.setInt(1, reiziger.getReizigerID());
	pstmt1.setString(2, reiziger.getVoorletters());
	pstmt1.setString(3, reiziger.getTussenvoegsels());
	pstmt1.setString(4, reiziger.getAchternaam());
	pstmt1.setDate(5, reiziger.getGbdatum());
	
	int count = pstmt1.executeUpdate();
	
	if (count > 0) {
		
		return reiziger;
		
	}
	
	return null;
	
}

/*

public boolean delete(Reiziger reiziger) throws SQLException {
	Connection c = super.getConnection();
    boolean result;
    @SuppressWarnings("unused")
	boolean result1;
    PreparedStatement pstmt1 = c.prepareStatement("DELETE FROM ov_chipkaart WHERE reizigerid=?");
    PreparedStatement pstmt2 = c.prepareStatement("DELETE FROM reiziger WHERE reizigerid=?");
    pstmt1.setInt(1, reiziger.getReizigerID());
    pstmt2.setInt(1, reiziger.getReizigerID());
    result = pstmt1.executeUpdate() > 0;
    result1 = pstmt2.executeUpdate() > 0;
    c.close();
    return result;

   
	
}
*/

public boolean delete(Reiziger reiziger)throws SQLException {
	Connection conn = super.getConnection();
	ovChipkaartDao ovDao = new OvChipkaartDaoImpl();
	ovDao.delete(ovDao.findByReiziger(reiziger.getReizigerID()));
    boolean success = false;
   
    PreparedStatement stmt = conn.prepareStatement("DELETE FROM reiziger WHERE reizigerid=?");
    stmt.setInt(1, reiziger.getReizigerID());

        if(stmt.executeUpdate() > 0) {
            success = true;
           
        }
       

        return success;

}






@Override
public void closeConnection(Connection conn) throws SQLException {
	conn.close();
	
}

@Override
public Reiziger getById(int reizigerID) throws SQLException {
Connection conn = super.getConnection();
	
	String getReizigers = "select * from reiziger where reizigerid = ?";
	
	PreparedStatement pstmt1 = conn.prepareStatement(getReizigers);
	
	pstmt1.setInt(1, reizigerID);
	
	ResultSet rs = pstmt1.executeQuery();
	
	int id;
	String voorletters;
	String tussenvoegsels;
	String achternaam;
	Date geboortedatum;
	
	Reiziger reiziger = null;
	
	while (rs.next()) {   
		
		id = rs.getInt("reizigerid");
		voorletters = rs.getString("voorletters");
		tussenvoegsels = rs.getString("tussenvoegsel");
		achternaam = rs.getString("achternaam");
		geboortedatum = rs.getDate("geboortedatum");
		
		reiziger = new Reiziger(id, voorletters, tussenvoegsels, achternaam, geboortedatum);
	
	}
	
	return reiziger;
	
};




}
