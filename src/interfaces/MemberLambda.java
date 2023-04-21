package interfaces;

import criminals.Member;
/**
 * Functional Interface whose method, filterMembers, receives an
 * object type Member and return true or false. The main objective
 * of the method should be to compare one or more properties of a 
 * Member to an expected value or values.
 * @author Gretchen
 *
 */
@FunctionalInterface
public interface MemberLambda {
	public boolean filterMembers(Member M);
}
