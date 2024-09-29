package com.testek.api.tasks.Oauth2;

import com.testek.api.tasks.basicTasks.SendRequestPost;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class Login implements Task {
    private Object account;

    public Login(Object account) {
        this.account = account;
    }


    public static Login withAccount(Object account) {
        return new Login(account);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        String endPoint = "/login-with-local";
        t.attemptsTo(
                SendRequestPost.withDetails(endPoint, account, null)
        );
        String access_token = SerenityRest.lastResponse().jsonPath().getString("access_token");
        t.remember("access_token", access_token);
    }
}
