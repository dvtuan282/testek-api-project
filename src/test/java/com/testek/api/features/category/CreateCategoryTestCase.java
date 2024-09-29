package com.testek.api.features.category;

import com.testek.api.models.AccountModel;
import com.testek.api.models.CategoryModels;
import com.testek.api.questions.BasicQuestion;
import com.testek.api.tasks.CategoryTasks.CreateCategory;
import com.testek.api.tasks.Oauth2.Login;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class CreateCategoryTestCase {
    private static Actor actor;

    @BeforeAll
    static void setup() {
        actor = Actor.named("tuanTester")
                .whoCan(CallAnApi.at("http://tapi.testek.vn/api/v0/prod-man"));
    }

    @BeforeEach
    void login() {
        actor.attemptsTo(
                Login.withAccount(new AccountModel("testek", "admin"))
        );
    }

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource("CreateFor")
    void createWhenLogged(CategoryModels categoryModels, String message, int statusCodeExpected) {
        actor.attemptsTo(
                CreateCategory.withCategory(categoryModels, true)
        );
        actor.should(
                seeThat(message, BasicQuestion.status(), equalTo(statusCodeExpected))
        );
    }

    @Test
    void createWhenNotLogged() {
        actor.attemptsTo(
                CreateCategory.withCategory(new CategoryModels("Dòng xe địa hình cao cấp", "Xe địa hình CC", "ACTIVE"), false)
        );
        actor.should(
                seeThat("Tạo danh mục khi chưa login", BasicQuestion.status(), equalTo(401))
        );
    }

    private static Stream<Arguments> CreateFor() {
        return Stream.of(
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp", "Không chứa sản phẩm", "ACTIVE"), "Tạo danh mục thành công", 201),
                Arguments.of(new CategoryModels("", "Xe đạp giấc mơ", "ACTIVE"), "Tạo danh mục thành công khi để trống các trường option", 201),
                Arguments.of(new CategoryModels("Dòng xe đạp cho người già", "", "ACTIVE"), "Tạo danh mục thành công khi để trống các trường option", 400),
                Arguments.of(new CategoryModels("Dòng xe đạp đường phố", " ", "ACTIVE"), "Tạo danh mục thành công khi để trống các trường option", 400),
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp copy", "Xe địa hình CC", "ACTIVE"), "Tạo danh mục thành công", 400)
        );
    }
}
