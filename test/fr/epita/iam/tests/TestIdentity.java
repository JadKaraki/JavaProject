/**
 * 
 */
package fr.epita.iam.tests;

import fr.epita.iam.datamodel.Identity;

/**
 * @author tbrou
 *
 */
public class TestIdentity {
	
	
	public static void main(String[] args) {
		Identity identity = new Identity("0","Thomas Broussard", "tbr@tbr.com");
		System.out.println(identity);
	}
}
