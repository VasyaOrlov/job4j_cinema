package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    UserService userService;
    UserController userController;

    @BeforeEach
    void init() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRegistrationTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = userController.registration(model, httpSession);
        assertThat(rsl).isEqualTo("registration");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenAddUserAndSuccessTest() {
        User user = new User(1, "email", "pass", "name");

        when(userService.add(user)).thenReturn(Optional.of(user));

        String rsl = userController.addUser(user);
        assertThat(rsl).isEqualTo("redirect:/userSuccess");
    }

    @Test
    public void whenAddUserAndFailTest() {
        User user = new User(1, "email", "pass", "name");

        when(userService.add(user)).thenReturn(Optional.empty());

        String rsl = userController.addUser(user);
        assertThat(rsl).isEqualTo("redirect:/userFail");
    }

    @Test
    public void whenUserFailTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = userController.userFail(model, httpSession);
        assertThat(rsl).isEqualTo("userFail");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenUserSuccessTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = userController.userSuccess(model, httpSession);
        assertThat(rsl).isEqualTo("userSuccess");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenGetLoginTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = userController.login(model, httpSession);
        assertThat(rsl).isEqualTo("login");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenPostLoginSuccessTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(userService.findUser(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));

        String rsl = userController.entry(user, model, httpSession);
        assertThat(rsl).isEqualTo("redirect:/cinema");
        verify(httpSession).setAttribute("user", user);
    }

    @Test
    public void whenPostLoginFailTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(userService.findUser(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());

        String rsl = userController.entry(user, model, httpSession);
        assertThat(rsl).isEqualTo("login");
        assertThat(model.getAttribute("error"))
                .isEqualTo("Почта или пароль введены неверно!");
    }

    @Test
    public void whenLogoutTest() {
        HttpSession httpSession = mock(HttpSession.class);

        String rsl = userController.logout(httpSession);
        assertThat(rsl).isEqualTo("redirect:/cinema");
    }
}