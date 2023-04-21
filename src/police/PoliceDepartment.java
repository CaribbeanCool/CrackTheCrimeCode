package police;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import criminals.Member;
import criminals.Organization;
import interfaces.List;
import lists.ArrayList;
import lists.SinglyLinkedList;

public class PoliceDepartment {
	private List<Organization> criminalOrganizations;
	private String captain;
	private int totalArrests = 0; // to use in the case report

	/**
	 * Creates an instance of PoliceDepartment.
	 * It receives the name of the Captain as a parameter.
	 * 
	 * @param captain - (String) Name of the captain of the Police Department
	 */

	public PoliceDepartment(String captain) {
		this.criminalOrganizations = new ArrayList<>();
		this.captain = captain;
	}

	/**
	 * A getter for the private List containing the different organizations
	 * @return List of Criminal Organizations that the Police Department
	 *         has on record.
	 */
	public List<Organization> getCriminalOrganizations() {
		return criminalOrganizations;
	}

	/**
	 * Reads the criminal organization files in the specified folder and adds them
	 * to the list of criminal organizations.
	 *
	 * This method reads each file in the folder specified by the case folder
	 * parameter, which should contain the criminal
	 * organization files. The files cannot be assumed to be in alphabetical order
	 * by filename. For each file, this method creates a new Organization
	 * using the file's absolute path,
	 * and adds the object to the criminalOrganizations list.
	 * 
	 * @param caseFolder (String) the path to the case folder containing the criminal
	 *                   organization
	 *                   files
	 * @throws IOException
	 */
	public void setUpOrganizations(String caseFolder) throws IOException {
		// Get the folder with the organization files
		File organizationFolder = new File(caseFolder + "/CriminalOrganizations");
		File[] messages = organizationFolder.listFiles();
		Arrays.sort(messages); // Sorting the files in alphabetical order

		// Loop through each file in the folder (in alphabetical order), create an
		// Organization object for each file, and add it to the list
		for (File file : messages) {
			Organization newOrg = new Organization(file.getAbsolutePath());
			criminalOrganizations.add(newOrg);
		}
	}

	/**
	 * The code gets the digiroot from the first line of the file.
	 * Then it gets the leaderKey by using digiroot - 1 as index in the list of
	 * organizations.
	 * After that, the code "decyphers" by reading each line, ignoring the "---" and
	 * the type of content,
	 * and in each line it separates it into an array, and we grab the first letter
	 * of the 'ith word, and add all letters onto a string, revealing the nickname of
	 * the leader.
	 * 
	 * @param caseFolder - (String) Path to the flyer that has the hidden message.
	 * @return The nickname of the leader of the operation
	 * @throws IOException
	 */
	public String decipherMessage(String caseFolder) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(caseFolder));
		String leader = "";
		String line = br.readLine();
		int digiroot = getDigiroot(line) - 1; // the index of the Organization
		int leaderKey = criminalOrganizations.get(digiroot).getLeaderKey(); // Gives me the leader_key of the organization
		while (!(line = br.readLine()).equals("--")) {
			if (line.endsWith(":") || line.endsWith("?")) {
				continue;
			}
			String[] words = line.split(" ");
			if (words.length < leaderKey) { // if the leader_key index is OutOfBounds...
				leader += " ";
				continue;
			}
			char letter = words[leaderKey - 1].charAt(0);
			leader += letter;
		}
		br.close();
		return leader;
	}

	/**
	 * Calculates the digital root (digiroot) of the number received. The number is
	 * received as a String since it makes processing each individual number a bit *
	 * easier.
	 *
	 * @param numbers - (String) The number to calculate the digiroot
	 * @return the digiroot
	 */
	public int getDigiroot(String numbers) {
		int sum = 0;
		for (int i = 0; i < numbers.length(); i++) {
			if (numbers.charAt(i) == '#') {
				continue;
			}
			int digit = Character.getNumericValue(numbers.charAt(i));
			sum += digit;
		}
		if (sum < 10) {
			return sum;
		} else {
			String temp = Integer.toString(sum);
			return getDigiroot(temp);
		}
	}

	/**
	 * Arrests the criminal organization leader and all their underlings.
	 *
	 * This method searches for the criminal organization leader with the given
	 * nickname (ignoring case) in the list of criminal
	 * organizations, and arrests them and all their underlings. If multiple leaders
	 * have the same nickname, only the first one found
	 * will be arrested. The method uses a recursive helper method recArrest to
	 * arrest all the underlings of the leader.
	 *
	 * @param leader - (String) the nickname of the criminal organization leader to arrest
	 */
	public void arrest(String leader) {
		// Create lists to store the underlings of the leader and the organization
		// containing the leader
		List<Member> underlingsOfLeader = new SinglyLinkedList<>();
		List<Member> listContainingLeader = new SinglyLinkedList<>();

		// Search for the leader in each criminal organization
		for (int i = 0; i < criminalOrganizations.size(); i++) {
			// Get a list containing the leader (if it exists) from the current organization. 
			// Put both names in uppercase for case_mismatch problems.
			listContainingLeader = criminalOrganizations.get(i)
					.organizationTraversal((M) -> M.getNickname().toUpperCase().equals(leader.toUpperCase()));

			// If the list is not empty, we found the leader and can proceed with the arrest
			if (!listContainingLeader.isEmpty()) {
				// Get the leader's underlings and arrest the leader
				underlingsOfLeader = listContainingLeader.get(0).getUnderlings();
				listContainingLeader.get(0).setArrested(true);
				totalArrests++;

				// Recursively arrest all the leader's underlings
				recArrest(underlingsOfLeader);
				break;
			}
		}
	}

	/**
	 * This method recursively arrests members of a criminal organization.
	 * It takes in a list of underlings, and if the list is empty, it returns 0.
	 * Otherwise, it arrests each underling in the list who is not already arrested,
	 * and recursively calls itself on the underling's underlings list with the most
	 * members.
	 * 
	 * @param underlings - (List) A list of underlings to arrest.
	 * @return Nothing. It's for the recursion
	 */
	private int recArrest(List<Member> underlings) {
		if (underlings.isEmpty()) {
			return 0;
		}
		int max = 0;
		int index = 0;
		for (int i = 0; i < underlings.size(); i++) {
			if (!underlings.get(i).isArrested()) {
				totalArrests++;
				underlings.get(i).setArrested(true);
			}
			if (underlings.get(i).getUnderlings().size() > max) {
				max = underlings.get(i).getUnderlings().size();
				index = i;
			}
		}
		return recArrest(underlings.get(index).getUnderlings());
	}

	/**
	 * Writes a police report to a file with the given file path.
	 * The police report includes the case report header, the name of the captain in
	 * charge
	 * of the operation, the total number of arrests made, and the current status of
	 * each
	 * criminal organization. If a criminal organization's boss has been arrested,
	 * the organization is considered DISSOLVED.
	 * 
	 * @param filePath - (String) Path to the file where the police report will be
	 *                 written.
	 * @throws IOException
	 */
	public void policeReport(String filePath) throws IOException {
		FileWriter writer = new FileWriter(new File(filePath));
		try {
			writer.write("CASE REPORT ");
			writer.write("\n\nIn charge of Operation: " + captain);
			writer.write("\n\nTotal arrests made: " + totalArrests);
			writer.write("\n\nCurrent Status of Criminal Organizations:\n");
			for (int i = 0; i < criminalOrganizations.size(); i++) {
				if (criminalOrganizations.get(i).getBoss().isArrested())
					writer.write("\nDISSOLVED");
				writer.write("\n" + criminalOrganizations.get(i).toString());
				writer.write("---");
			}
			writer.write("\nEND OF REPORT");
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}
}