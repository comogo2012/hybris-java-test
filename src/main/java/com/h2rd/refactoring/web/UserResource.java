package com.h2rd.refactoring.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import com.h2rd.refactoring.usermanagement.UserRules;

@Path("/users")
@Repository
public class UserResource{

	private static final boolean USE_SPRING_FRAMEWORK = false;

	// userDao will be fetched from UserDao class on the fly.
	// note UserDao.getUserDao call returns a singleton, 
	// but this class doesn't rely on that.
    public UserDao userDao = null;
    
    // User Rules encapsulating business requirements
    public UserRules userRules = null;

    @GET
    @Path("add/")
    public Response addUser(@QueryParam("name") String name,
                            @QueryParam("email") String email,
                            @QueryParam("role") List<String> roles) {

    	log.info("In users/add/, UserResource=" + this.toString() 
    		+ ", " + paramsToStringHelper(name, email, roles));
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        establishHelpers();
        Response result;
        if (userRules.isUserValid(user, userDao)) {
        	log.info("In add/ uservalid = true" );
            userDao.saveUser(user);
            result = Response.ok().entity(user).build();
        } else {
        	log.info("In add/ uservalid = false" );
        	result = Response.status(Status.BAD_REQUEST).entity(user).build();
        }
        return result;
    }


	@GET
    @Path("update/")
    public Response updateUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("role") List<String> roles) {
    	log.info("In users/update/, UserResource=" + this.toString()
    		+ ", " + paramsToStringHelper(name, email, roles));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        establishHelpers();
        Response result;
        if (userRules.isUserValid(user, userDao)) {
        	log.info("In update/ uservalid = true" );
            userDao.updateUser(user);
            result = Response.ok().entity(user).build();
        } else {
        	log.info("In update/ uservalid = true" );
        	result = Response.status(Status.BAD_REQUEST).entity(user).build();
        }
        return result;
    }

    @GET
    @Path("delete/")
    public Response deleteUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("role") List<String> roles) {
    	log.info("In users/delete/, UserResource=" + this.toString()
    	+ ", " + paramsToStringHelper(name, email, roles));
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        establishHelpers();

        userDao.deleteUser(user);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("find/")
    public Response getUsers() {
    	
    	log.info("In find, UserResource=" + this.toString());
    	establishHelpers();
    	List<User> users = userDao.getUsers();
    	if (users == null) {
    		users = new ArrayList<User>();
    	}

    	log.info("In find, Users Found=" + users.size());
        GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(usersEntity).build();
    }

    @GET
    @Path("search/")
    public Response findUser(@QueryParam("name") String name) {

    	log.info("In search, UserResource(" + name + ") =" + this.toString());
    	establishHelpers();

        User user = userDao.findUser(name);
    	log.info("In search, UserFound=" + user);
        return Response.ok().entity(user).build();
    }
    
    /**
     * Creates or finds the local userDao if it has not yet been established
     */
    private void establishHelpers() {
        if (userDao == null) {
        	synchronized(this) {
        		// check for null again, in case userDao just 
        		// got established on another thread
        		if (userDao == null) {
        			if (USE_SPRING_FRAMEWORK) {
            	        ApplicationContext context 
          	          = new ClassPathXmlApplicationContext(new String[] {
          	    		"classpath:/application-config.xml"	
          	    	});
          	    	this.userDao = context.getBean(UserDao.class);
          	    	this.userRules = context.getBean(UserRules.class);

        			} else {
            			this.userDao = UserDao.getUserDao();
            			this.userRules = new UserRules();
        			}
        			log.info("in UserResource.establishHelpers," +
        					  " USE_SPRING_FRAMEWORK = " 
        				+ USE_SPRING_FRAMEWORK + ", userDao = " + this.userDao);
        		}
        	}
        }
    	
        
    }
    
    // for logging purposes
    private String paramsToStringHelper(String name, String email,
        List<String> roles)
    {
    	StringBuilder result = new StringBuilder();
    	result.append("params: name=" + name + ",email="+email+"roles=( ");
    	if (roles == null) {
    		result.append("null ");
    	} else {
    		result.append("size=" + roles.size() + "; ");
        	for (String role : roles) {
        		result.append(role);
        		result.append(" ");
        	}
    	}
    	result.append(")");
	    return result.toString();
    }

    
    private static Log log = LogFactory.getLog(UserResource.class);

}
