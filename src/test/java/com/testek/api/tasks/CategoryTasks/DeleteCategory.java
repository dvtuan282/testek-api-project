package com.testek.api.tasks.CategoryTasks;

import com.testek.api.tasks.basicTasks.SendRequestDelete;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.HashMap;
import java.util.Map;

public class DeleteCategory implements Task {
    private final Boolean isSoft;
    private final String categoryId;

    public DeleteCategory(Boolean isSoft, String categoryId) {
        this.isSoft = isSoft;
        this.categoryId = categoryId;
    }

    public static DeleteCategory withOptions(Boolean isSoft, String categoryId) {
        return Tasks.instrumented(DeleteCategory.class, isSoft, categoryId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + actor.recall("access_token"));

        Map<String, Object> pathParams = Map.of("id", categoryId);
        Map<String, Object> queryParams = Map.of("isSoft", isSoft);
        // Perform deletion
        SendRequestDelete sendRequestDelete = SendRequestDelete.to("/category/{id}")
                .withHeaders(headers)
                .withPathParams(pathParams)
                .withQueryParams(queryParams)
                .build();
        actor.attemptsTo(sendRequestDelete);

    }
}
