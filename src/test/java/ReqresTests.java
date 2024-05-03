import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

@DisplayName("Тесты на reqres.in")
public class ReqresTests {

    private String newUserData = "{\"name\": \"morpheus\", \"job\": \"leader\"}",
            updateUserData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

    @Test
    @DisplayName("Проверка успешного создания нового пользователя")
    void createNewUserTest() {

        given()
                .body(newUserData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя при помощи метода Patch")
    void updateUserWithPatchTest() {

        given()
                .body(updateUserData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя при помощи метода Put")
    void updateUserWithPutTest() {

        given()
                .body(updateUserData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .put("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Проверка успешного удаления пользователя")
    void deleteUserTest() {

        given()
                .log().uri()

                .when()
                .delete("https://reqres.in/api/users/5")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    @DisplayName("Проверка значения поля per_page в списке ресурсов")
    void perPageValueTest() {

        given()
                .log().uri()

                .when()
                .get("https://reqres.in/api/unknown")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("per_page", is(6));
    }

    @Test
    @DisplayName("Проверка значений элемента с id = 3 в списке ресурсов")
    void listDataTest() {

        given()
                .log().uri()

                .when()
                .get("https://reqres.in/api/unknown")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data[2].name", is("true red"))
                .body("data[2].year", is(2002))
                .body("data[2].color", is("#BF1932"))
                .body("data[2].pantone_value", is("19-1664"));
    }

    @Test
    @DisplayName("Проверка обращения к несуществующему пользователю")
    void singleUserNotFoundTest() {

        given()
                .log().uri()

                .when()
                .get("https://reqres.in/api/users/23")

                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(is("{}"));
    }
}
