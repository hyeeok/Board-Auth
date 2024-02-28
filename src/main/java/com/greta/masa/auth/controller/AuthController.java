package com.greta.masa.auth.controller;

import com.greta.masa.auth.entity.UserInfo;
import com.greta.masa.auth.entity.UserSession;
import com.greta.masa.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login(){
        return "auth/Login";
    }

    @PostMapping("/loginpro")
    public String loginPro(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model,
                           HttpSession session) {
        try {
            // 로그인 서비스에서 로그인 성공 여부와 세션을 반환
            UserSession userSession = authService.login(username, password);

            if (userSession != null) {
                // 세션 부여
                session.setAttribute("userSession", userSession);
                return "Main";
            } else {
                // 로그인 실패 시 에러 메시지 반환
                model.addAttribute("message", "로그인에 실패했습니다. 다시 시도해 주세요.");
                model.addAttribute("searchUrl", "/login");
                return "util/Message";
            }
        } catch (RuntimeException e) {
            // 예외 발생 시 에러 메시지 반환
            model.addAttribute("message", e.getMessage());
            model.addAttribute("searchUrl", "/login");
            return "util/Message";
        }
    }

    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }

    @PostMapping("/registerpro")
    public String registerPro(@ModelAttribute("userInfo") UserInfo userInfo,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {
        authService.register(userInfo, username, password);

        model.addAttribute("message", "회원가입 완료.");
        model.addAttribute("searchUrl", "/");

        return "util/Message";
    }


}
