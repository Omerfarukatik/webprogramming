package farukcodes;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean remember = request.getParameter("remember") != null;

        if (remember) {
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(24 * 60 * 60); // Cookie 24 saat geçerli olacak şekilde ayarlanıyor
            response.addCookie(userCookie);

            Cookie passwordCookie = new Cookie("password", password);
            passwordCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(passwordCookie);
        }

        response.sendRedirect("anasayfa.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    String username = cookie.getValue();
                    for (Cookie c : cookies) {
                        if (c.getName().equals("password")) {
                            String password = c.getValue();
                            
                            if ("admin".equals(username) && "password".equals(password)) {
                                
                                response.sendRedirect("anasayfa.jsp");
                                return;
                            }
                        }
                    }
                }
            }
        }

        response.sendRedirect("login.jsp");
    }
}

