package com.testek.api.tasks.CategoryTasks;

import com.testek.api.tasks.basicTasks.SendRequestPost;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import com.testek.api.models.CategoryModels;

import java.util.HashMap;
import java.util.Map;

public class CreateCategory implements Task {
    private final CategoryModels categoryMode;
    private final Boolean isLogin;

    public CreateCategory(CategoryModels categoryMode, Boolean isLogin) {
        this.categoryMode = categoryMode;
        this.isLogin = isLogin;
    }

    public static CreateCategory withCategory(CategoryModels category, Boolean isLogin) {
        return new CreateCategory(category, isLogin);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + t.recall("access_token"));
        String endPoint = "/category";
        t.attemptsTo(
                SendRequestPost.withDetails(endPoint, categoryMode, headers)
        );
        if (!isLogin) {
            t.attemptsTo(
                    SendRequestPost.withDetails(endPoint, categoryMode, null)
            );
        }
    }
}
