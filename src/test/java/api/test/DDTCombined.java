package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTCombined {
	
	
	@Test(dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testCreateAndDeleteUser(String userID, String userName, String fName, String lName, String email, String pwd, String phone) throws InterruptedException {
	    // Create User
	    User userPayload = new User();
	    userPayload.setId(Integer.parseInt(userID));
	    userPayload.setUsername(userName);
	    userPayload.setFirstName(fName);
	    userPayload.setLastName(lName);
	    userPayload.setEmail(email);
	    userPayload.setPassword(pwd);
	    userPayload.setPhone(phone);

	    Response createResponse = UserEndPoints2.createUser(userPayload);
	    createResponse.then().log().all();
	    Assert.assertEquals(createResponse.getStatusCode(), 200);
	    
	    Thread.sleep(2000); // 1 second pause


	    // Delete User
	    Response deleteResponse = UserEndPoints2.deleteUser(userName);
	    deleteResponse.then().log().all();
	    Assert.assertEquals(deleteResponse.getStatusCode(), 200);
	}
	
}
