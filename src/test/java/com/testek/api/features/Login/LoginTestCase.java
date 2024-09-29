package com.testek.api.features.Login;

import com.testek.api.models.AccountModel;
import com.testek.api.questions.BasicQuestion;
import com.testek.api.tasks.Oauth2.Login;
import io.cucumber.cucumberexpressions.Argument;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class LoginTestCase {

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = Actor.named("tuanTester")
                .whoCan(CallAnApi.at("http://tapi.testek.vn/api/v0/prod-man"));
    }

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource("loginFor")
    void LoginNotSuccess(AccountModel accountModel, String message, int statusCodeExpected) {
        actor.attemptsTo(
                Login.withAccount(accountModel)
        );
        actor.should(
                seeThat(message, BasicQuestion.status(), equalTo(statusCodeExpected))
        );
    }

    private static Stream<Arguments> loginFor() {
        return Stream.of(
                Arguments.of(new AccountModel("testek", "admin"), "Login thành công", 200),
                Arguments.of(new AccountModel("testek1", "admin"), "Login sai username", 401),
                Arguments.of(new AccountModel("testek", "admin1"), "Login sai password", 401),
                Arguments.of(new AccountModel("testek1", "admin1"), "Login sai tài khoản và mật khẩu", 401),
                Arguments.of(new AccountModel(" testek ", " admin1 "), "Login username và password có khoảng trắng đầu cuối", 401)
        );
    }
}
