package com.h2rd.refactoring.usermanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A thread-safe data access object working against a dedicated list of Users.
 * A prerequisite is that all users in dao have a valid, normalized, name.
 * 
 * @author Corey Morgan
 *
 */
public class UserDao {

	public ArrayList<User> users = null;

	public static UserDao  userDao = null;

	/**
	 * Get the singleton userDao object
	 * @return
	 */
	public static UserDao getUserDao() {
		if (userDao == null) {
			log.info("Creating singleton");
			userDao = new UserDao();
		}
		return userDao;
	}

	/**
	 * Append a user to the dedicated list of users. Note that this DAO does
	 * not guard against duplicate user names or user emails.
	 * @param user User bean to add to our list of users.
	 */
	public void saveUser(User user) {
		if (user != null && user.getName() != null) {
    		if (users == null) {
    			users = new ArrayList<User>();
    			log.info("In saveUser, users == null: Newing up an ArrayList: " + users );
    		}
    		synchronized(users) {
    			// Normalize user name before saving
    			user.setName(user.getName().trim());
    			users.add(user);
    		}
		}
	}

	/**
	 * Return a list of users that is a _copy_ of what is contained in this dao.
	 * A copy is given so that the underlying collection reamins in control of
	 * this DAO.
	 */
	public List<User> getUsers() {
		List<User> result = null;
		if (users != null) {
			synchronized(users) {
				result = new ArrayList<User>(users);
			}
		}
		log.info("Dao.getUsers(): users = " + users + ", size = " + ((users == null) ? 0 : users.size()));
		return result;
	}

	/**
	 * Delete any and all users in our users collection that share the same
	 * name as the given user to delete. If the collection doesn't have a user,
	 * then nothing happens.
	 * @param userToDelete user whose name is used to id those to delete 
	 *        from our collection
	 */
	public void deleteUser(User userToDelete) {
		if (users != null && userToDelete != null) {
			User foundUser;
			String userName = userToDelete.getName();
			while ((foundUser = findUser(userName)) != null) {
    			synchronized (users) {
    				users.remove(foundUser);
    			}
			}
		}
	}

	/**
	 * Update the _first_ user found in our collection sharing the name
	 * of the given user to update.  If no user is found, nothing happens.
	 * @param userToUpdate user whose email and roles are to be used to update
	 */
	public void updateUser(User userToUpdate) {
		if (userToUpdate != null && userToUpdate.getName() != null) {
			String userName = userToUpdate.getName().trim();
			User user = findUser(userName);
			if (user != null) {
				user.setEmail(userToUpdate.getEmail());
				user.setRoles(userToUpdate.getRoles());
			}
		}
	}

	/**
	 * Return the _first_ user in our collection that shares the given name
	 * @param name name of the user to search for
	 * @return return the first found user having the name, or null if not found
	 */
	public User findUser(String name) {
		if (users != null && name != null) {
			// user names are pre-trimmed
			synchronized (users) {
				for (User user : users) {
					if (userMatches(user, name)) {
						return user;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Return true iff user's name matches the given name. If either is null, this returns false.
	 */
	private boolean userMatches(User user, String name) {
		return (user != null) && (name != null) && 
			user.getName().equals(name.trim());
	}
	
    private static Log log = LogFactory.getLog(UserDao.class);

}
