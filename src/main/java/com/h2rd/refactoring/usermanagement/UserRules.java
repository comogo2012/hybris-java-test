package com.h2rd.refactoring.usermanagement;

/**
 * Encapsulates business rules around Users in the UserDao.
 * Checks a candidate user against the (presumably valid) UserDao to make sure
 * that the requirements are met.
 * 
 * 
 * @author Corey Morgan
 *
 */
public class UserRules {

	
	/**
	 * Business Requirements
     *   ---------------------
     * The user's email is a unique identifier and should be handled accordingly.
     * A user should have at least one role.
	 */
	
	public boolean isUserValid(User candidate, UserDao dao) {
		boolean userNameValid = isUserNameValid(candidate, dao);
		boolean userEmailValid = isUserEmailValid(candidate, dao);
		boolean userEmailStatus = isEmailUnique(candidate, dao);
		boolean hasRole = userHasRole(candidate, dao);
		return userNameValid && userEmailValid & userEmailStatus && hasRole;
	}
	
	
	public boolean isUserNameValid(User candidate, UserDao dao) {
		return (candidate != null) && (candidate.getName() != null) && 
			(candidate.getName().trim().length() > 0);
	}
	
	public boolean isUserEmailValid(User candidate, UserDao dao) {
		return (candidate != null) && (candidate.getEmail() != null) &&
			(candidate.getEmail().trim().length() > 0);
	}
	/**
	 * Return true iff user name is valid and candidate's email is unique in dao.
	 * Matching user(s) within dao are ignored.
	 */
	public boolean isEmailUnique(User candidate, UserDao dao) {
		boolean result;
		if (!isUserNameValid(candidate,dao))
			result = false;
		else {
			String userName = candidate.getName().trim();
			result = true;  // assume okay until/unless proven otherwise
			if (dao != null && dao.getUsers() != null) {
    			for (User user : dao.getUsers()) {
    				if (userName.equals(user.getName())) {
    					// ignore matching users
    				} else {
    					// a user in dao that is other than our candidate
    					if (emailsMatch(user.getEmail(), candidate.getEmail())) {
    						result = false;
    						break;  // no need to loop further
    					}
    				}
    			}
			}
		}
		return result;
	}
	
	public boolean  userHasRole(User candidate, UserDao dao) {
		boolean result;
		if (candidate == null || candidate.getRoles() == null ||
			candidate.getRoles().size() == 0) 
			result = false;
		else {
			result = true; // assume the best
			// loop through roles, checking that they are all good
			for (String role : candidate.getRoles()) {
				if (role == null || role.trim().length() == 0) {
					// bad role found
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Return true iff both emails are valid, and they are equal.
	 */
	private boolean emailsMatch(String email1, String email2) {
	    return (email1 != null) && (email2 != null) && 
	    	email1.trim().equals(email2.trim());
    }


}
