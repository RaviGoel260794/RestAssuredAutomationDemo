package api.test;

import static api.utilities.ExtentReportManager.extentTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

    public Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String userName, String fName, String lName, String email, String pwd, String phone) {
        logger.info("********* Starting test: Create User *********");
        extentTest.get().log(Status.INFO, "********* Starting test: Create User *********");

        User userPayload = new User();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fName);
        userPayload.setLastName(lName);
        userPayload.setEmail(email);
        userPayload.setPassword(pwd);
        userPayload.setPhone(phone);

        extentTest.get().log(Status.INFO, "Payload created for user: " + userName);

        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();

        logger.info("Response received with status: " + response.getStatusCode());
        extentTest.get().log(Status.INFO, "Response status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("User created successfully: " + userPayload.getUsername());
        extentTest.get().log(Status.PASS, "User created successfully: " + userPayload.getUsername());
        extentTest.get().log(Status.INFO, "********* Finished test: Create User *********");
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByUserName(String userName) {
        logger.info("********* Starting test: Delete User *********");
        extentTest.get().log(Status.INFO, "********* Starting test: Delete User *********");

        Response response = UserEndPoints.deleteUser(userName);
        response.then().log().all();

        logger.info("Response status for deletion: " + response.getStatusCode());
        extentTest.get().log(Status.INFO, "Response status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("User deleted: " + userName);
        extentTest.get().log(Status.PASS, "User deleted successfully: " + userName);
        extentTest.get().log(Status.INFO, "********* Finished test: Delete User *********");
    }
}
