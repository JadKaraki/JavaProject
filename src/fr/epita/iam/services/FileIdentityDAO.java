/**
 * 
 */
package fr.epita.iam.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;

/**
 * @author tbrou
 *
 */
public class FileIdentityDAO {

	private PrintWriter printer;
	private Scanner scanner;

	/**
	 * @throws IOException
	 * 
	 */
	public FileIdentityDAO() throws IOException {
		File file = new File("tests.txt");
		if (!file.exists()) {
			System.out.println("the file does not exist");
			file.createNewFile();
		}

		this.printer = new PrintWriter(file);
		this.scanner = new Scanner(file);
	}

	/**
	 * @param printer
	 * @param identity
	 */
	public void writeIdentity(Identity identity) {
		this.printer.println("-----Identity:Begin------");
		this.printer.println(identity.getDisplayName());
		this.printer.println(identity.getEmail());
		this.printer.println(identity.getUid());
		this.printer.println("-----Identity:End------");
		this.printer.flush();
	}

	public List<Identity> readAll() {
		List<Identity> results = new ArrayList<>();
		while (this.scanner.hasNext()) {
			// First line : delimiter
			this.scanner.nextLine();
			String displayName = this.scanner.nextLine();
			String email = this.scanner.nextLine();
			String uid = this.scanner.nextLine();

			// Last line : delimiter
			this.scanner.nextLine();
			Identity readIdentity = new Identity(uid, displayName, email);
			results.add(readIdentity);
		}
		return results;
	}

}
