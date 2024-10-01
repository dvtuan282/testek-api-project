package com.testek.api.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseStatusCode implements Question<Integer> {
    @Override
    public Integer answeredBy(Actor actor) {
        return lastResponse().statusCode();
    }

    public static Question<Integer> status() {
        return new ResponseStatusCode();
    }
}
