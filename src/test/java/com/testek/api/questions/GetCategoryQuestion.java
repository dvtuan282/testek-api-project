package com.testek.api.questions;

import com.testek.api.models.CategoryResponseModel;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetCategoryQuestion implements Question<CategoryResponseModel> {
    @Override
    public CategoryResponseModel answeredBy(Actor actor) {
        // In toàn bộ phản hồi API ra màn hình để kiểm tra
        System.out.println("Phản hồi từ API: " + SerenityRest.lastResponse().body().asString());

        // Sử dụng JsonPath để lấy phần "data" từ JSON
        return SerenityRest.lastResponse()
                .jsonPath()
                .getObject("", CategoryResponseModel.class);  // Lấy toàn bộ phản hồi và ánh xạ sang CategoryResponseModel
    }

    public static GetCategoryQuestion fetchedCategory() {
        return new GetCategoryQuestion();
    }
}
