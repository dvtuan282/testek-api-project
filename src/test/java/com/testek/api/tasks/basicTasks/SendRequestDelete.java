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

    private SendRequestDelete(String endPoint, Map<String, Object> headers, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        this.endPoint = endPoint;
        this.headers = headers;
        this.queryParams = queryParams;
        this.pathParams = pathParams;
    }

    public static Builder to(String endPoint) {
        return new Builder(endPoint);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(endPoint).with(req -> {
                    req.contentType(ContentType.JSON);
                    if (headers != null) req.headers(headers);
                    if (queryParams != null) req.queryParams(queryParams);
                    if (pathParams != null) req.pathParams(pathParams);
                    return req;
                })
        );
    }

    public static class Builder {
        private final String endPoint;
        private Map<String, Object> headers;
        private Map<String, Object> queryParams;
        private Map<String, Object> pathParams;

        public Builder(String endPoint) {
            this.endPoint = endPoint;
        }

        public Builder withHeaders(Map<String, Object> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withQueryParams(Map<String, Object> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public Builder withPathParams(Map<String, Object> pathParams) {
            this.pathParams = pathParams;
            return this;
        }

        public SendRequestDelete build() {
            return new SendRequestDelete(endPoint, headers, queryParams, pathParams);
        }
    }
}
