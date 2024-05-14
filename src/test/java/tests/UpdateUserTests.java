package tests;

import models.UpdateUserRequestModel;
import models.UpdateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpecs.code200ResponseSpec;
import static specs.TestSpecs.requestSpecification;

@DisplayName("Update user tests")
public class UpdateUserTests extends TestBase {

    @Test
    @DisplayName("Update User Data with Patch method")
    void updateUserWithPatchTest() {
        UpdateUserRequestModel userData = new UpdateUserRequestModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UpdateUserResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .patch("users/{id}", 2)

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @DisplayName("Update User Data with Put method")
    void updateUserWithPutTest() {
        UpdateUserRequestModel userData = new UpdateUserRequestModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UpdateUserResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .put("users/{id}", 2)

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("zion resident");
        });
    }
}
