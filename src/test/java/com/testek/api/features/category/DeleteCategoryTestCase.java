package com.testek.api.features.category;

import com.testek.api.models.AccountModel;
import com.testek.api.models.CategoryModels;
import com.testek.api.questions.GetCategoryQuestion;
import com.testek.api.questions.ResponseBodyQuestion;
import com.testek.api.questions.ResponseStatusCode;
import com.testek.api.tasks.CategoryTasks.CreateCategory;
import com.testek.api.tasks.CategoryTasks.DeleteCategory;
import com.testek.api.tasks.Oauth2.Login;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
// cần so sánh thêm với các trường trong response body
// với các trường pass thfi response và status trả ra như nào
// kết hợp với các trường failse thì response và status trả ra như nào
// các trường có sản phẩm thì sẽ như nào khi đúng và false
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
    @MethodSource("Dataprivot")
    void deleteCategoryWhenNotContainProducts(CategoryModels categoryModels, boolean isSoft, String message) {
        CreateCategoryThenDelete(categoryModels, isSoft, message);
    }

    private static Stream<Arguments> Dataprivot() {
        return Stream.of(
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp", "test02-102", "ACTIVE"), true, "case1"),
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp", "test02-103", "ACTIVE"), false, "case2"),
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp", "test02-105", "INACTIVE"), false, "case3"),
                Arguments.of(new CategoryModels("Dòng xe địa hình cao cấp", "test02-10423", "INACTIVE"), true, "case4")
        );
    }

    private void CreateCategoryThenDelete(CategoryModels categoryInput, boolean isSoft, String message) {
        // Tạo mới một danh mục để xóa
        actor.attemptsTo(CreateCategory.withCategory(categoryInput, true));
        // Lấy id của category đã tạo mới để xóa
        CategoryModels categoryResponse = actor.asksFor(GetCategoryQuestion.fetchedCategory());
        // Xóa category sau khi đã lấy được từ response
        System.out.println("id đã tạo: " + categoryResponse.getId());
        actor.attemptsTo(
                DeleteCategory.withOptions(isSoft, categoryResponse.getId()),
                Ensure.that(message, ResponseStatusCode.status()).isEqualTo(200)
        );
        System.out.println("Tại đây");
        // clear category khi xóa với isSoft = true.
        if (isSoft) {
            actor.attemptsTo(
                    DeleteCategory.withOptions(false, categoryResponse.getId())
            );
        }
        System.out.println("oke");
    }
}
