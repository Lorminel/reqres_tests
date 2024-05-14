package tests;

import models.ResourceListData;
import models.ResourceListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.TestSpecs.code200ResponseSpec;
import static specs.TestSpecs.requestSpecification;

@DisplayName("Resource list data tests")
public class ResourceListTests extends TestBase {

    @Test
    @DisplayName("Per_page value check in resource list")
    void perPageValueTest() {
        ResourceListResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .when()
                        .get("unknown")

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(ResourceListResponseModel.class));

        step("Check response", () ->
                assertThat(response.getPerPage()).isEqualTo(6));
    }

    @Test
    @DisplayName("Check element with id = 3 data")
    void listDataTest() {
        ResourceListResponseModel response = step("Make request", () ->
                given(requestSpecification)
                        .when()
                        .get("unknown")

                        .then()
                        .spec(code200ResponseSpec)
                        .extract().as(ResourceListResponseModel.class));

        ResourceListData checkedElement = response.getData().get(2);

        step("Check response", () -> {
            assertThat(checkedElement.getName()).isEqualTo("true red");
            assertThat(checkedElement.getYear()).isEqualTo(2002);
            assertThat(checkedElement.getColor()).isEqualTo("#BF1932");
            assertThat(checkedElement.getPantoneValue()).isEqualTo("19-1664");
        });
    }
}
