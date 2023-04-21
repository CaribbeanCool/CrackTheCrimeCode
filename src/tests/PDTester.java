package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import criminals.Member;
import criminals.Organization;
import interfaces.List;
import lists.ArrayList;
import police.PoliceDepartment;

public class PDTester {
	
	private PoliceDepartment PD;
	
	@Before
	public void setup() {
		this.PD = new PoliceDepartment("Morgan");
	}
	/*
	 * Testing random digiroots
	 */
	@Test
	public void testDigiroot1() {
		
		int dr = this.PD.getDigiroot("1684616");
		assertEquals("Failed to return 5 as the digiroot of 1684616.", 5, dr);
		
	}
	@Test
	public void testDigiroot2() {
		int dr = this.PD.getDigiroot("15896745");
		assertEquals("Failed to return 9 as the digiroot of 15896745.", 9 , dr);
	}
	
	@Test
	public void testDigiroot3() {
		int dr = this.PD.getDigiroot("9878524");
		assertEquals("Failed to return 7 as the digiroot of 9878524.", 7, dr);
	}
	
	/**
	 * Test deciphering of message from Police Department. 
	 * 
	 * This gives basic organization info and reads Message1 in the
	 * case 1 folder.
	 */
	@Test
	public void testOrganizationDecypher1() throws IOException {
		this.PD.getCriminalOrganizations().add(new Organization("Asterism", 3));
		this.PD.getCriminalOrganizations().add(new Organization("Atlas", 3));
		this.PD.getCriminalOrganizations().add(new Organization("Canin", 1));
		String leader = this.PD.decipherMessage("inputFiles/case1/Flyers/Message1");
		assertEquals("Failed to identify the correct leader.", leader.toLowerCase(), "fox");
	}
	/**
	 * Test deciphering of message from Police Department. 
	 * 
	 * This gives basic organization info and reads Message2 in the
	 * case 1 folder.
	 */
	@Test
	public void testOrganizationDecypher2() throws IOException {
		this.PD.getCriminalOrganizations().add(new Organization("Asterism", 3));
		this.PD.getCriminalOrganizations().add(new Organization("Atlas", 3));
		this.PD.getCriminalOrganizations().add(new Organization("Canin", 1));
		String leader = this.PD.decipherMessage("inputFiles/case1/Flyers/Message2");
		assertEquals("Failed to identify the correct organization.", leader.toLowerCase(), "paris");
	}
	
	/**
	 * Test deciphering of message from Police Department. 
	 * 
	 * This gives basic organization info and reads Message4 in the
	 * case 2 folder.
	 */
	@Test
	public void testOrganizationDecypher3() throws IOException {
		this.PD.getCriminalOrganizations().add(new Organization("Canin", 1));
		this.PD.getCriminalOrganizations().add(new Organization("Menagerie", 4));
		String leader = this.PD.decipherMessage("inputFiles/case2/Flyers/Message4");
		assertEquals("Failed to identify the correct organization.", leader.toLowerCase(), "red panda");
	}
	
	/**
	 * Tests if the organizations are being setup correctly. Specifically if the hierarchy is correct for
	 * the organization Canin in case 1.
	 */
	@Test
	public void testOrganizationSetup1() throws IOException {
		
		// Coyote
		Member coyote = new Member("Bruce Wilde", "Coyote", "Beta", "Wolf");
		// Coyote minions
		coyote.getUnderlings().add(new Member("Kenny Fields", "Schnauzer", "Omega", "Coyote"));
		coyote.getUnderlings().add(new Member("Hal Kennedy", "Basset Hound", "Omega", "Coyote"));
		
		// Great Dane
		Member great_dane = new Member("Joe King", "Great Dane", "Beta", "Wolf");
		// Great Dane minions
		great_dane.getUnderlings().add(new Member("Millie Jones", "Jackal", "Omega", "Great Dane"));
		great_dane.getUnderlings().add(new Member("Minerva Beverly", "Greyhound", "Omega", "Great Dane"));
		great_dane.getUnderlings().add(new Member("Ben Duo", "Husky", "Omega", "Great Dane"));
		
		// Fox
		Member fox = new Member("Ben Smith", "Fox", "Beta", "Wolf");
		// Fox minions
		fox.getUnderlings().add(new Member("Gary Larry", "Chihuahua", "Omega", "Fox"));
		fox.getUnderlings().add(new Member("Bary Larry", "Terrier", "Omega", "Fox"));
		fox.getUnderlings().add(new Member("Greg Johnson", "Bulldog", "Omega", "Fox"));
		fox.getUnderlings().add(new Member("Billy Child", "Dingo", "Omega", "Fox"));
		fox.getUnderlings().add(new Member("Gina Jones", "Poodle", "Omega", "Fox"));
		
		// Wolf
		Member expected_wolf = new Member("Gabriel Holmes", "Wolf", "Alpha", "");
		
		expected_wolf.getUnderlings().add(fox);
		expected_wolf.getUnderlings().add(great_dane);
		expected_wolf.getUnderlings().add(coyote);
		
		Member result_boss = null;
		
		PD.setUpOrganizations("inputFiles/case1");
		
		for(Organization org: PD.getCriminalOrganizations()) {
			if(org.getName().equals("Canin")) {
				result_boss = org.getBoss();
				System.out.println(org);
				break;
			}
		}
		if(result_boss == null)
			fail("Organization not found.");
		
		assertTrue("Canin organization is wrong.", compareOrganizations(result_boss, expected_wolf));
		
	}
	/**
	 * Tests if the organizations are being setup correctly. Specifically if the hierarchy is correct for
	 * the organization Asterismin case 1.
	 */
	@Test
	public void testOrganizationSetup2() throws IOException {
		
		// Virgo
		Member virgo = new Member("Ethan Mitchell", "Virgo", "Star", "Leo");
		// Virgo minions
		virgo.getUnderlings().add(new Member("Chloe Davis", "Cancer", "Planet", "Virgo"));
		Member capricorn = new Member("Lucas Anderson", "Capricorn", "Planet", "Virgo");
		virgo.getUnderlings().add(capricorn);
		Member aquarius = new Member("Ava Taylor", "Aquarius", "Planet", "Virgo");
		virgo.getUnderlings().add(aquarius);
		Member scorpio = new Member("Alexander Wright", "Scorpio", "Planet", "Virgo");
		virgo.getUnderlings().add(scorpio);
		
		// Capricorn minions
		capricorn.getUnderlings().add(new Member("Sophia Hernandez", "Taurus", "Moon", "Capricorn"));
		capricorn.getUnderlings().add(new Member("Benjamin Kim", "Sagittarius", "Moon", "Capricorn"));
		// Aquarius minions
		Member pisces = new Member("Isabella Lee", "Pisces", "Moon", "Aquarius");
		aquarius.getUnderlings().add(pisces);
		// Scorpio minions
		scorpio.getUnderlings().add(new Member("Jacob Jackson", "Libra", "Moon", "Scorpio"));
		// Pisces minions
		Member ares = new Member("Emma Patel", "Aries", "Satelite", "Pisces");
		pisces.getUnderlings().add(ares);
		// Ares minions
		ares.getUnderlings().add(new Member("Noah Nguyen", "Gemini", "Rock", "Aries"));
		
		// Leo
		Member expected_leo = new Member("Olivia Brown", "Leo", "Super Nova", "");
		
		expected_leo.getUnderlings().add(virgo);
		
		Member result_boss = null;
		
		PD.setUpOrganizations("inputFiles/case1");
		
		for(Organization org: PD.getCriminalOrganizations()) {
			if(org.getName().equals("Asterism")) {
				result_boss = org.getBoss();
				System.out.println(org);
				break;
			}
		}
		if(result_boss == null)
			fail("Organization not found.");
		assertTrue("Asterism organization is wrong.", compareOrganizations(result_boss, expected_leo));
		
	}
	/**
	 * Check that the Organization List created is in the correct order, otherwise the deciphering
	 * step will fail.
	 */
	@Test
	public void testOrganizationSetup3() throws IOException {
		PD.setUpOrganizations("inputFiles/case1");
		List<Organization> orgs = PD.getCriminalOrganizations();
		assertTrue("Criminal organizations aren't placed in the correct position. The files should be read in"
				+ " alphabetical order and added in that order as well.", orgs.get(0).getName().equals("Asterism") && 
				orgs.get(1).getName().equals("Atlas") && orgs.get(2).getName().equals("Canin"));
	}
	
	/**
	 * Test the arresting process. Check that the correct people are arrested when we decipher Message1 form case 1.
	 */
	@Test
	public void testArrest1() throws IOException {
		PD.setUpOrganizations("inputFiles/case1");
		String leader = this.PD.decipherMessage("inputFiles/case1/Flyers/Message1");
		PD.arrest(leader);
		List<Member> orgMembers = PD.getCriminalOrganizations().get(2).organizationTraversal((M) -> true);
		List<Member> expected = new ArrayList<Member>(6);
		expected.add(new Member("Ben Smith", "Fox", "Beta", "Wolf"));
		expected.add(new Member("Gary Larry", "Chihuahua", "Omega", "Fox"));
		expected.add(new Member("Bary Larry", "Terrier", "Omega", "Fox"));
		expected.add(new Member("Greg Johnson", "Bulldog", "Omega", "Fox"));
		expected.add(new Member("Billy Child", "Dingo", "Omega", "Fox"));
		expected.add(new Member("Gina Jones", "Poodle", "Omega", "Fox"));
		
		int count = 0;
		for(Member m: orgMembers) {
			if(expected.contains(m) && m.isArrested()) {
				count++;
			}
		}
		System.out.println(orgMembers);
		assertEquals("Failed arrest operation. Did not arrest the correct amount of people or didn't arrest the correct people.", expected.size(), count);
	}
	/**
	 * Test the arresting process. Check that the correct people are arrested when we decipher Message5 form case 2.
	 */
	@Test
	public void testArrest2() throws IOException {
		PD.setUpOrganizations("inputFiles/case2");
		String leader = this.PD.decipherMessage("inputFiles/case2/Flyers/Message5");
		PD.arrest(leader);
		List<Member> orgMembers = PD.getCriminalOrganizations().get(1).organizationTraversal((M) -> true);
		List<Member> expected = new ArrayList<Member>(6);
		expected.add(new Member("Elizabeth Wilson", "Gorilla", "B", "Elephant"));
		expected.add(new Member("John Smith", "Tiger", "C", "Gorilla"));
		expected.add(new Member("Alexander Taylor", "Cheetah", "D", "Tiger"));
		expected.add(new Member("William Lee", "Red Panda", "D", "Tiger"));
		
		int count = 0;
		for(Member m: orgMembers) {
			if(expected.contains(m) && m.isArrested()) {
				count++;
			}
		}
		System.out.println(orgMembers);
		assertEquals("Failed arrest operation. Did not arrest the correct amount of people or didn't arrest the correct people.", expected.size(), count);
	}

	/**
	 * Auxiliary method for comparing the hierarchy in the organizations.
	 * @param boss_expected: Member with the expected information
	 * @param boss_test: Member with the information generated by the PoliceDepartment
	 * @return
	 */
	private boolean compareOrganizations(Member boss_expected, Member boss_test) {
		if(!boss_expected.equals(boss_test))
			return false;
		return recCompare(boss_expected, boss_test.getUnderlings());
	}
	/**
	 * Recursive method for coparing the hierarchy of the organizations.
	 * @param boss
	 * @param expected_lackys
	 * @return
	 */
	private boolean recCompare(Member boss, List<Member> expected_lackys) {
		if(boss.getUnderlings().size() == expected_lackys.size()) {
			for(Member m: expected_lackys) {
				if(!boss.getUnderlings().contains(m))
					return false;
			}
			for(Member m: boss.getUnderlings()) {
				boolean correct = true;
				for(Member m2: expected_lackys) {
					if(m.equals(m2))
						correct = recCompare(m, m2.getUnderlings());
				}
				if(!correct)
					return false;
			}
			return true;
		}
			
		return false;
	}

}
