package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/login_db";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "MOHANSAI2006";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Get values from HTML form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql =
                "SELECT * FROM users WHERE username=? AND password=?";

        try {

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection con =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            // Create PreparedStatement
            PreparedStatement ps =
                    con.prepareStatement(sql);

            // Set values
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Check result
            if (rs.next()) {

                out.println("<html>");
                out.println("<body>");

                out.println("<h1>Login Successful</h1>");

                out.println("<h2>Welcome "
                        + username
                        + "</h2>");

                out.println("</body>");
                out.println("</html>");

            } else {

                out.println("<html>");
                out.println("<body>");

                out.println("<h1>Login Failed</h1>");

                out.println("<p>Invalid Username or Password</p>");

                out.println("<a href='index.html'>Try Again</a>");

                out.println("</body>");
                out.println("</html>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Database Error</h2>");

            out.println("<p>"
                    + e.getMessage()
                    + "</p>");
        }
    }
}