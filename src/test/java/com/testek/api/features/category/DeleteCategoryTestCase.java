
package com.testek.api.features.category;

import com.testek.api.models.AccountModel;
import com.testek.api.questions.BasicQuestion;
import com.testek.api.tasks.CategoryTasks.DeleteCategory;
import com.testek.api.tasks.Oauth2.Login;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class DeleteCategoryTestCase {

    private static Actor actor;

    @BeforeAll
    static void setup() {
        actor = Actor.named("tuanTester")
                .whoCan(CallAnApi.at("http://tapi.testek.vn/api/v0/prod-man"));  // Base URL of the API
    }

    @BeforeEach
    void login() {
        actor.attemptsTo(
                Login.withAccount(new AccountModel("Testek", "admin"))
        );
    }

    @ParameterizedTest
    @MethodSource("deleteScenarios")
    void DeleteCategoryWhenLogin(DeleteScenario scenario) {
        actor.attemptsTo(
                DeleteCategory.fromDetails(true, scenario.queryParams(), scenario.pathParam())
        );

        actor.should(
                seeThat(scenario.expectedMessage, BasicQuestion.status(), equalTo(scenario.expectedStatus))
        );
    }

    static Stream<Arguments> deleteScenarios() {
        return Stream.of(
//                Arguments.of(DeleteScenario.CHANGER_INACTIVE_WITH_PRODUCT),
//                Arguments.of(DeleteScenario.CHANGER_INACTIVE_WITHOUT_PRODUCT),
                Arguments.of(DeleteScenario.DELETE_STATUS_INACTIVE_WITH_SOFT)
//                Arguments.of(DeleteScenario.HARD_DELETE_WITH_PRODUCTS, true),    // Hard delete with products
//                Arguments.of(DeleteScenario.HARD_DELETE_WITHOUT_PRODUCTS, true), // Hard delete without products
//                Arguments.of(DeleteScenario.DELETE_NON_EXISTENT_CATEGORY, true), // Delete non-existent category
//                Arguments.of(DeleteScenario.DELETE_WITHOUT_ISSOFT, true),        // Delete without isSoft
//                Arguments.of(DeleteScenario.DELETE_WITHOUT_LOGIN, false)         // Delete without login (logged out)
        );
    }

    // Enum to define different delete scenarios
    enum DeleteScenario {
//        CHANGER_INACTIVE_WITH_PRODUCT(true, "5a28888b-02ec-401a-97b8-ede4b47cb198", 200, "Thay đổi trạng thái khi xóa cate có sản phẩm với isSoft = true"),
//        CHANGER_INACTIVE_WITHOUT_PRODUCT(true, "1f3bf4ef-963f-4488-ba6d-b95bb2b823dd", 200, "Thay đổi trạng thái khi xóa cate không có sản phẩm với isSoft = true"),
        DELETE_STATUS_INACTIVE_WITH_SOFT(true, "5a28888b-02ec-401a-97b8-ede4b47cb198", 200, "Xóa cate có trạng thái là inactive với isoft = true");
//        DELETE_(false, "categoryWithProducts", 200, "Category hard deleted with products"),
//        HARD_DELETE_WITHOUT_PRODUCTS(false, "categoryWithoutProducts", 200, "Category hard deleted without products"),
//        DELETE_NON_EXISTENT_CATEGORY(true, "nonExistentCategory", 404, "Category not found"),
//        DELETE_WITHOUT_ISSOFT(null, "categoryWithoutIsSoft", 400, "isSoft parameter missing"),
//        DELETE_WITHOUT_LOGIN(true, "categoryWithoutLogin", 401, "Authentication required");

        final boolean isSoft;
        final String id;
        final int expectedStatus;
        final String expectedMessage;

        // Constructor for each scenario
        DeleteScenario(boolean isSoft, String id, int expectedStatus, String expectedMessage) {
            this.isSoft = isSoft;
            this.id = id;
            this.expectedStatus = expectedStatus;
            this.expectedMessage = expectedMessage;
        }

        // Helper methods to generate query and path parameters for each scenario
        public Map<String, Object> pathParam() {
            Map<String, Object> pathParam = new HashMap<>();
            pathParam.put("id", id);
            return pathParam;
        }

        public Map<String, Object> queryParams() {
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put("isSoft", isSoft);
            return queryParam;
        }
    }
}
