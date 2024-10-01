package com.testek.api.questions;

import com.testek.api.models.CategoryModels;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetCategoryQuestion implements Question<CategoryModels> {
    @Override
    public CategoryModels answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getObject("data", CategoryModels.class);
    }

    public static GetCategoryQuestion fetchedCategory() {
        return new GetCategoryQuestion();
    }
}
