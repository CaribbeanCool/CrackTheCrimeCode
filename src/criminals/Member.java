package criminals;

import interfaces.List;
import lists.DoublyLinkedList;

public class Member {
	
	private String name;
	private String nickname;
	private List<Member>underlings;
	private String rank;
	private String boss;
	private boolean arrested;
	
	
	/**
	 * Creates a member of a criminal organization, by receiving all their
	 * information at once.
	 * @param name - (String) Legal name of the member
	 * @param nickname - (String) Name of the member within the organization. (Main identifier)
	 * @param rank - (String) Rank within the organization
	 * @param boss - (String) Name of member who is their supervisor. 
	 */
	public Member(String name, String nickname, String rank, String boss) {
		
		this.name = name;
		this.nickname = nickname;
		this.rank = rank;
		this.boss = boss;
		this.underlings = new DoublyLinkedList<Member>();
		this.arrested = false;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<Member> getUnderlings() {
		return underlings;
	}
	public void setUnderlings(List<Member> underlings) {
		this.underlings = underlings;
	}
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getBoss() {
		return boss;
	}
	public void setBoss(String boss) {
		this.boss = boss;
	}

	public boolean isArrested() {
		return arrested;
	}
	public void setArrested(boolean arrested) {
		this.arrested = arrested;
	}
	/**
	 * Returns whether two members are equal. 
	 * 
	 * Members are equal if they have the same nickname, since these are unique with the organization.
	 * Also organizations don't have similar nickname systems.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Member) {
			return this.getNickname().equals(((Member) obj).getNickname());
		}
		return false;
	}
	@Override
	public String toString() {
		return (this.isArrested()? "(Arrested) ": "") + "Name: " + this.name + " aka " + this.nickname+ (this.getBoss().equals("")? " THE BOSS": " | Worked under: " + this.boss);
	}
	
	
	

}
