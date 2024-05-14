package tests;

import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpecs.code201ResponseSpec;
import static specs.TestSpecs.requestSpecification;

@DisplayName("New user tests")
public class CreateUserTests extends TestBase {
    @Test
    @DisplayName("Successful new user check")
    void successfulCreateUserTest() {
        CreateUserRequestModel userData = new CreateUserRequestModel();
        userData.setName("morpheus");
        userData.setJob("leader");

        CreateUserResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .body(userData)

                        .when()
                        .post("users")

                        .then()
                        .spec(code201ResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("leader");
            assertThat(response.getId()).isNotNull();
            assertThat(response.getCreatedAt()).isNotNull();
        });
    }
}
