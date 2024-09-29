package com.testek.api.tasks.CategoryTasks;

import com.testek.api.tasks.basicTasks.SendRequestDelete;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.HashMap;
import java.util.Map;

public class DeleteCategory implements Task {
    private final Boolean isLogin;
    private final Map<String, Object> queryParams;
    private final Map<String, Object> pathParams;

    public DeleteCategory(Boolean isLogin, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        this.isLogin = isLogin;
        this.queryParams = queryParams;
        this.pathParams = pathParams;
    }

    public static DeleteCategory fromDetails(Boolean isLogin, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        return new DeleteCategory(isLogin, queryParams, pathParams);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + t.recall("access_token"));
        String endPoint = "/category/{id}";
        t.attemptsTo(
                SendRequestDelete.fromDetails(endPoint, headers, queryParams, pathParams)
        );
        if (!isLogin) {
            t.attemptsTo(
                    SendRequestDelete.fromDetails(endPoint, null, queryParams, pathParams)
            );
        }
    }
}
