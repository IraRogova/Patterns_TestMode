package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInIfActiveValidUser() {
        Registration user = Randomise.generateNewActiveValidUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        //$$("button").find(exactText("Продолжить")).click();
        $$("button").findBy(exactText("Продолжить")).click();
        $("h2").shouldHave(text("Личный кабинет"));
    }
    @Test
    void shouldNotLogInIfBlockedUser() {
        Registration user = Randomise.generateNewBlockedUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text
                ("Пользователь заблокирован")).shouldBe(visible, Duration.ofMillis(5000));
    }

    @Test
    void shouldNotLogInIfActiveUserInvalidLogin() {
        Registration user = Randomise.generateNewActiveUserInvalidLogin();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text
                ("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    @Test
    void shouldNotLogInIfActiveUserInvalidPassword() {
        Registration user = Randomise.generateNewActiveInvalidPassword();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text
                ("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyLogin() {
        Registration user = Randomise.generateNewActiveValidUser();
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id='login'] .input__sub").shouldHave(text
                ("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofMillis(5000));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyPassword() {
        Registration user = Randomise.generateNewActiveValidUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id='password'] .input__sub").shouldHave(text
                ("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofMillis(5000));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyLoginAndPassword() {
        $$("button").findBy(exactText("Продолжить")).click();
        $("[data-test-id='login'] .input__sub").shouldHave(text
                ("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofMillis(5000));
        $("[data-test-id='password'] .input__sub").shouldHave(text
                ("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofMillis(5000));;
    }
}
