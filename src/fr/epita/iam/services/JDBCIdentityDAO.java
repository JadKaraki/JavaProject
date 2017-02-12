/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.epita.iam.datamodel.Identity;

/**
 * @author tbrou
 *
 */
public class JDBCIdentityDAO {

	
	
	private Connection connection;
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/DERBY;create=true","USER","USER");
		System.out.println(connection.getSchema());
	}
	
	
	public void writeIdentity(Identity identity) throws SQLException {
		String insertStatement = "insert into IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		pstmt_insert.setString(1, identity.getDisplayName());
		pstmt_insert.setString(2, identity.getEmail());
		Date now = new Date();
		pstmt_insert.setDate(3, new java.sql.Date(now.getTime()));

		pstmt_insert.execute();

	}

	public List<Identity> readAll() throws SQLException {
		List<Identity> IDENTITY = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmt_select.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITY_ID"));
			String email = rs.getString("IDENTITY_EMAIL");
			Date birthDate = rs.getDate("IDENTITY_BIRTHDATE");
			Identity identity = new Identity(uid, displayName, email);
			IDENTITY.add(identity);
		}
		return IDENTITY;

	}
	
	public void UpdateID (Identity updateID) throws SQLException {
		
		String update = "UPDATE IDENTITIES set IDENTITY_DISPLAYNAME=?, IDENTITY_EMAIL=? WHERE IDENTITY_ID=?";
		PreparedStatement pstmt_update = connection.prepareStatement(update);
		pstmt_update.setString(1, updateID.getDisplayName());
		pstmt_update.setString(2, updateID.getEmail());
		pstmt_update.setString(3, updateID.getUid());

		
		pstmt_update.execute();

	}

	 
	public List<Identity> FindID (String Name) throws SQLException {
		List<Identity> IDENTITY = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES WHERE IDENTITY_DISPLAYNAME=?");
		 pstmt_select.setString(1, Name);
		ResultSet rs = pstmt_select.executeQuery();

		while (rs.next()) {
			
		
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITY_ID"));
			String email = rs.getString("IDENTITY_EMAIL");
			
			Identity identity = new Identity(uid, displayName, email);
			IDENTITY.add(identity);
		}
		
		pstmt_select.close();
		return IDENTITY;
	
	
	}
	
	
	
public void DeleteID (Identity updateID) throws SQLException {
		
		String delete = "DELETE FROM IDENTITIES WHERE IDENTITY_EMAIL=?";
				
		PreparedStatement pstmt_delete = connection.prepareStatement(delete);
		pstmt_delete.setString(1, updateID.getEmail());
		
		
		
		pstmt_delete.execute();

	
}

public boolean authenticate(String username, String password) throws SQLException {
	
	PreparedStatement pstmt_authenticate = connection.prepareStatement("SELECT USERNAME, PASSWORD FROM CREDENTIALS where username=?");
	
	pstmt_authenticate.setString(1, username);
		
	
	ResultSet output= pstmt_authenticate.executeQuery();
	
	while (output.next()) {
		
		return username.equals(output.getString("USERNAME")) && password.equals(output.getString("PASSWORD")); 
	}
	
	return false;


}
}
