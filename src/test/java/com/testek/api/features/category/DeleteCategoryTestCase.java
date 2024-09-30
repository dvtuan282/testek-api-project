package com.testek.api.features.category;

import com.testek.api.models.AccountModel;
import com.testek.api.models.CategoryModels;
import com.testek.api.models.CategoryResponseModel;
import com.testek.api.questions.BasicQuestion;
import com.testek.api.questions.GetCategoryQuestion;
import com.testek.api.tasks.CategoryTasks.CreateCategory;
import com.testek.api.tasks.CategoryTasks.DeleteCategory;
import com.testek.api.tasks.Oauth2.Login;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Test
    void DeleteCategoryWhenLogin() {
//        // Tạo category mới với thông tin được cung cấp
//        CategoryModels categoryInput = new CategoryModels(null,"Cái này để case1", "Tuấn case121", "ACTIVE");
//        actor.attemptsTo(
//                CreateCategory.withCategory(categoryInput, true)  // Gọi task để tạo category
//        );
//
//        // Lấy phản hồi từ API sau khi tạo category
//        CategoryResponseModel categoryResponseCreate = actor.asksFor(GetCategoryQuestion.fetchedCategory());
//
//        if (categoryResponseCreate == null || categoryResponseCreate.getData() == null) {
//            System.out.println("Không thể lấy được category. Phản hồi là null hoặc không có dữ liệu.");
//            return;  // Dừng test nếu không có dữ liệu
//        }
//
//        // Hiển thị ID của category vừa tạo
//        UUID categoryId = categoryResponseCreate.getData().getId(); // Lấy ID từ phần "data"
//        System.out.println("ID của Category vừa tạo:  " + categoryId);

        // Chuẩn bị pathParam và queryParam để xóa category
        Map<String, Object> pathParam = new HashMap<>();
        Map<String, Object> queryParam = new HashMap<>();
        pathParam.put("id", "4942a218-1e2c-492c-affd-df672a43fc60");  // Lấy ID từ response để xóa
        queryParam.put("ifSoft", true);  // Chọn xóa mềm (soft delete)

        // Thực hiện yêu cầu xóa category
        actor.attemptsTo(
                DeleteCategory.fromDetails(true, queryParam, pathParam)
        );
        // Xác nhận rằng status của yêu cầu là 200
        actor.should(
                seeThat("Kiểm tra mã trạng thái sau khi xóa", BasicQuestion.status(), equalTo(200))
        );
    }

}
