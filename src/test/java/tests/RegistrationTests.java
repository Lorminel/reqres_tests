package tests;

import models.MissingPasswordResponseModel;
import models.RegistrationAndLoginRequestModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpecs.*;

@DisplayName("Registration tests")
public class RegistrationTests extends TestBase {

    @Test
    @DisplayName("Successful registration check")
    void successfulRegistrationTest() {
        RegistrationAndLoginRequestModel userData = new RegistrationAndLoginRequestModel();
        userData.setEmail("eve.holt@reqres.in");
        userData.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .post("register")

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getToken()).isNotNull();
            assertThat(response.getId()).isNotNull();
        });
    }

    @Test
    @DisplayName("Registration without password check")
    void unsuccessfulRegistrationTest() {
        RegistrationAndLoginRequestModel userData = new RegistrationAndLoginRequestModel();
        userData.setEmail("eve.holt@reqres.in");

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

