package com.testek.api.tasks.basicTasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class SendRequestPost implements Task {
    private final String endPoint;
    private final Object body;

    private final Map<String, Object> headers;

    public SendRequestPost(String endPoint, Object body, Map<String, Object> headers) {
        this.endPoint = endPoint;
        this.body = body;
        this.headers = headers;
    }

    public static SendRequestPost withDetails(String endPoint, Object body, Map<String, Object> headers) {
        return new SendRequestPost(endPoint, body, headers);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
                Post.to(endPoint).with(
                        req -> {
                            req.contentType(ContentType.JSON);
                            req.body(body);
                            if (headers != null) {
                                req.headers(headers);
                            }
                            return req;
                        }
                )
        );
    }
}
