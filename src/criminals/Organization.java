package criminals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import interfaces.List;
import interfaces.MemberLambda;
import lists.DoublyLinkedList;

public class Organization {
	
	private String name;
	private Member boss;
	private int leaderKey;
	
	
/**
 * Creates an instance of a criminal organization. It receives the path to a file that contains 
 * the information related to the organization. This then calls the organizationSetup() method 
 * the handle the rest for the work.
 * @param fileName - (String) Path to the file with organization information.
 * @throws IOException
 */
	public Organization(String fileName) throws IOException {
		this.setUpOrganization(fileName);
	}

	/**
	 * Creates an Organization with the bare minimum. It assigns its
	 * name and the key associated with the organization.
	 * @param name
	 * @param key
	 */
	public Organization(String name, int key) {
		this.name = name;
		this.leaderKey = key;
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeaderKey() {
		return leaderKey;
	}

	public void setLeaderKey(int leaderKey) {
		this.leaderKey = leaderKey;
	}

	public Member getBoss() {
		return boss;
	}

	public void setBoss(Member boss) {
		this.boss = boss;
	}
	
	/**
	 * 
	 * @param organizationFile - (String) Path to the file with organization information.
	 * @return
	 * @throws IOException
	 */
	public Organization setUpOrganization(String organizationFile) throws IOException {

		BufferedReader br;
		br = new BufferedReader(new FileReader(organizationFile));
		
		// Get organization name in the first line of the file
		this.setName(br.readLine());
		// Get the organizations key in the second line
		this.setLeaderKey(Integer.parseInt(br.readLine()));
		// List for keeping track of the already added members
		List<Member> org_members = new DoublyLinkedList<Member>();		
		// Add boss to organization
		String[] member = br.readLine().split(",");
		Member newMember = new Member(member[0], member[1], member[2], "");
		this.setBoss(newMember);
		org_members.add(newMember);
		// Will hold the current line in the file
		String line = "";
		// Get other members
		while((line = br.readLine()) != null) {
			// Get new member information
			member = line.split(",");
			newMember = new Member(member[0], member[1], member[2], member[3]);
			org_members.add(newMember);
			// Check how is the boss of this new member
			for(int i = org_members.size() - 1; i >= 0; i--) {
				Member potetialBoss = org_members.get(i);
				// If found add this member to the bosses' underling List 
				if(potetialBoss.getNickname().equals(newMember.getBoss())) {
					potetialBoss.getUnderlings().add(newMember);
					break;
				}
			}
		}
		br.close();
		return this;						    		
	}
	
	@Override
	public String toString() {
		// Pass every member of the organization to a single List
		List<Member> L = organizationTraversal( (M) -> true );
		// Keep track of rank being printed
		String curr_rank = "";
		
		String org_print = "Organization: " + this.getName() + "\n";
		// Print each member of the organization grouping them by rank.
		for(Member m: L) {
			if(!m.getRank().equals(curr_rank)) {
				org_print += "\nRank: " + m.getRank() + "\n";
				curr_rank = m.getRank();
			}
			org_print += m + "\n";
		}
		return org_print;
	}
	/**
	 * Returns a List with all the members of the organization that comply with the 
	 * given lambda function.
	 * 
	 * If the function always returns true, it returns a List with every member in the organization.
	 *  
	 * @param lambda - (LambdaMember) lambda function that fill filter out members. If it returns true the member
	 * gets added to the List.
	 * @return
	 */
	public List<Member> organizationTraversal(MemberLambda lambda) {
		// List to hold members
		List<Member> L = new DoublyLinkedList<Member>();
		// Add boss if they qualify
		if(lambda.filterMembers(this.getBoss()))
			L.add(this.getBoss());
		// Traverse rest of organization
		recTraversal(this.getBoss(), L, lambda);
		return L;
	}
	/**
	 * Recursively traverses through the member hierarchy. It's sort-of a depth first traversal.
	 * @param m
	 * @param output
	 * @param lambda
	 */
	private void recTraversal(Member m, List<Member> output, MemberLambda lambda) {
		if(m.getUnderlings().isEmpty())
			return;
		for(Member m1: m.getUnderlings()) {
			if(lambda.filterMembers(m1))
				output.add(m1);
		}
		for(Member m1: m.getUnderlings()) {
			recTraversal(m1, output, lambda);
		}
	}
}
