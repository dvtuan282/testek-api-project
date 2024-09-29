package com.testek.api.tasks.basicTasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import java.util.Map;

public class SendRequestDelete implements Task {
    private final String endPoint;
    private final Map<String, Object> headers;
    private final Map<String, Object> queryParams;
    private final Map<String, Object> pathParams;

    public SendRequestDelete(String endPoint, Map<String, Object> headers, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        this.endPoint = endPoint;
        this.headers = headers;
        this.queryParams = queryParams;
        this.pathParams = pathParams;
    }

    public static SendRequestDelete fromDetails(String endPoint, Map<String, Object> headers, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        return new SendRequestDelete(endPoint, headers, queryParams, pathParams);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
                Delete.from(endPoint).with(
                        req -> {
                            req.contentType(ContentType.JSON);
                            if (headers != null) {
                                req.headers(headers);
                            }
                            if (queryParams != null) {
                                req.queryParams(queryParams);
                            }
                            if (pathParams != null) {
                                req.pathParams(pathParams);
                            }
                            return req;
                        }
                )
        );
    }
}
