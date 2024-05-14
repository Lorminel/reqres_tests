package tests;

import models.LoginResponseModel;
import models.MissingPasswordResponseModel;
import models.RegistrationAndLoginRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpecs.*;

@DisplayName("Login tests")
public class LoginTests extends TestBase {
    @Test
    @DisplayName("Successful login check")
    void successfulLoginTest() {
        RegistrationAndLoginRequestModel userData = new RegistrationAndLoginRequestModel();
        userData.setEmail("eve.holt@reqres.in");
        userData.setPassword("cityslicka");

        LoginResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .post("login")

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Check response", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Test
    @DisplayName("Login without password check")
    void unsuccessfulLoginTest() {
        RegistrationAndLoginRequestModel userData = new RegistrationAndLoginRequestModel();
        userData.setEmail("peter@klaven");

        MissingPasswordResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .post("register")

                        .then()
                        .spec(code400ResponseSpec)
                        .extract().as(MissingPasswordResponseModel.class));

        step("Check response", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}

