/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.SQLException;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.JDBCIdentityDAO;

/**
 * @author tbrou
 *
 */
public class JDBCTest {
	
	
	
	public static void main(String[] args) throws SQLException {
		
		JDBCIdentityDAO dao = new JDBCIdentityDAO();

		System.out.println(dao.readAll());
		dao.writeIdentity(new Identity(null, "quentin decayeux", "qdeca@qdeca.com"));
		System.out.println(dao.readAll());
		
		
		
	}


}
