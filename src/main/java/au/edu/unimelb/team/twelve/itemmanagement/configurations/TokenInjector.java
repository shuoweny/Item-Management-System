package au.edu.unimelb.team.twelve.itemmanagement.configurations;

import au.edu.unimelb.team.twelve.itemmanagement.repositories.UserRepository;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInjector implements HandlerInterceptor {
    private final UserRepository users;

    public TokenInjector(UserRepository users) {
        this.users = users;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var token = request.getParameter("token");
        if (token == null) {
            return true;
        }
        var user = users.fromToken(token);
        if (user == null) {
            return true;
        }
        request.setAttribute("user", user);
        return true;
    }
}
