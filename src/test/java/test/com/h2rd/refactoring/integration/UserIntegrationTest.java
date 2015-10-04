package test.com.h2rd.refactoring.integration;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.web.UserResource;

public class UserIntegrationTest {
	
	@Test
	public void createUserTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void createUserTest2() {
		UserResource userResource = new UserResource();
		
		Response response = createUser2(userResource);
        
        Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void createUserTestBad() {
		UserResource userResource = new UserResource();
		
		User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(new ArrayList<String>());
        
        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        
        // expect BAD_REQUEST, as roles are missing
        Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void updateUserTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
        User updated = new User();
        updated.setName("integration");
        updated.setEmail("updated@integration.com");
        updated.setRoles(new ArrayList<String>());
        
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Sys Admin" );
        roles.add( "Dirt Pounder");
        updated.setRoles(roles);
        
        Response response2 = userResource.updateUser(updated.getName(), 
        	updated.getEmail(), updated.getRoles());
        // expect OK
        Assert.assertEquals(200, response2.getStatus());
	}
	
	@Test
	public void updateUserTestBad() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
        User updated = new User();
        updated.setName("integration");
        updated.setEmail("updated@integration.com");
        updated.setRoles(new ArrayList<String>());
        
        Response response2 = userResource.updateUser(updated.getName(), 
        	updated.getEmail(), updated.getRoles());
        // expect BAD_REQUEST, as roles are missing
        Assert.assertEquals(400, response2.getStatus());
	}
	
	@Test
	public void updateUserTestBadEmail() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
        User updated = new User();
        updated.setName("integration");
        updated.setEmail("");
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Sheep Herder" );
        roles.add( "Groundskeeper");
        updated.setRoles(roles);
        
        Response response2 = userResource.updateUser(updated.getName(), 
        	updated.getEmail(), updated.getRoles());
        // expect BAD_REQUEST, as roles are missing
        Assert.assertEquals(400, response2.getStatus());
	}
	
	@Test
	public void twoUsersTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
		Response response2 = createUser2(userResource);
        
        Assert.assertEquals(200, response2.getStatus());
 	}
	
	@Test
	public void userDeleteTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
		Response response2 = createUser2(userResource);
        
        Assert.assertEquals(200, response2.getStatus());
        
        Response response3 = userResource.deleteUser("integration", null, null);
        Assert.assertEquals(200, response3.getStatus());
 	}
	
	@Test
	public void userFindTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
		Response response2 = createUser2(userResource);
        
        Assert.assertEquals(200, response2.getStatus());
        
        Response response3 = userResource.findUser("integration2");
        Assert.assertEquals(200, response3.getStatus());
 	}
	
	@Test
	public void userGetUserTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
		Response response2 = createUser2(userResource);
        
        Assert.assertEquals(200, response2.getStatus());
        
        Response response3 = userResource.getUsers();
        Object respEntity = response3.getEntity();
        outputMetadata(response3.getMetadata());
 	}

	protected void outputMetadata(MultivaluedMap<String, Object> mdMap) {
	    System.out.println("Map, length " +  mdMap.size());
        for (String key : mdMap.keySet()) {
        	System.out.println("   " + key + " -> " +mdMap.get(key) );
        }
    }
	
	
	@Test
	public void twoUsersEmailConflictTest() {
		UserResource userResource = new UserResource();
		
		Response response = createUser1(userResource);
        
        Assert.assertEquals(200, response.getStatus());
        
		Response response2 = createUser2(userResource);
        
        Assert.assertEquals(200, response2.getStatus());
        
        User updated = new User();
        updated.setName("integration");
        updated.setEmail("initial2@integration.com"); // this is user 2's email
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Sheep Herder" );
        roles.add( "Groundskeeper");
        updated.setRoles(roles);
        
        Response response3 = userResource.updateUser(updated.getName(), 
        	updated.getEmail(), updated.getRoles());
        // expect BAD_REQUEST, as roles are missing
        Assert.assertEquals(400, response3.getStatus());
 	}
	// -------------------------- Helpers --------------------------------------
	
	protected Response createUser1(UserResource userResource) {
	    User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Sheep Herder" );
        roles.add( "Groundskeeper");
        integration.setRoles(roles);
        
        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
	    return response;
    }
	
	protected Response createUser2(UserResource userResource) {
	    User integration = new User();
        integration.setName("integration2");
        integration.setEmail("initial2@integration.com");
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Typical user" );
        roles.add( "Host");
        integration.setRoles(roles);
        
        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
	    return response;
    }

}
