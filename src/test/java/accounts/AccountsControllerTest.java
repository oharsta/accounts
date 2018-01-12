package accounts;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.hasItems;

public class AccountsControllerTest extends AbstractIntegrationTest {

    @Test
    public void accounts() throws Exception {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("accounts/")
            .then()
            .statusCode(SC_OK)
            .body("id", hasItems(1));
    }

    @Test
    public void accountsWrongAuth() throws Exception {
        given()
            .auth().preemptive().basic("nope", "secret")
            .when()
            .get("accounts/")
            .then()
            .statusCode(SC_FORBIDDEN);
    }
}
