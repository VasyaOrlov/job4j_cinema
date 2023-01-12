package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.cinema.util.GetUser.getUser;

/**
 * UserController - класс контроллер
 * Отвечает за работу с пользователями в кинотеатре
 */
@Controller
@ThreadSafe
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * метод возвращает вид с формой регистрации пользователя
     * @return вид с формой регистрации
     */
    @GetMapping("/registration")
    public String registration(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "registration";
    }

    /**
     * метод добавляет пользователя в базу
     * @param user - пользователь
     * @return - url по итогам работы метода
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        Optional<User> rsl = userService.add(user);
        if (rsl.isEmpty()) {
            return "redirect:/userFail";
        }
        return "redirect:/userSuccess";
    }

    /**
     * метод возвращает вид с отображением ошибки регистрации пользователя
     * @return - вид с ошибкой регистрации пользователя
     */
    @GetMapping("/userFail")
    public String userFail(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "userFail";
    }

    /**
     * метод возвращает вид успешной регистрации
     * @return - вид успешной регистрации нового пользователя
     */
    @GetMapping("/userSuccess")
    public String userSuccess(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "userSuccess";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "login";
    }

    @PostMapping("/login")
    public String entry(@ModelAttribute User user, Model model, HttpSession httpSession) {
        Optional<User> temp = userService.findUser(user.getEmail(), user.getPassword());
        if (temp.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно!");
            return "login";
        }
        httpSession.setAttribute("user", temp.get());
        return "redirect:/cinema";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/cinema";
    }
}
