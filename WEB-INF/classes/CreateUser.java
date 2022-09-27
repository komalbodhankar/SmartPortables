import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        System.out.println("----------------- Inside Create User Get ----------------");
        displayContainer(request, response, utility, pw, false);

    }

    private void displayContainer(HttpServletRequest request, HttpServletResponse response, Utilities utility, PrintWriter pw, boolean error) {
        utility.printHtml("Header.html");

        pw.print("<div class='post' style='float: none; width: 100%'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Create New User</a></h2>"
                + "<div class='entry'>"
                + "<div style='width:100%; margin:25px; margin-left: auto;margin-right: auto;'>");

        pw.print("<div class='content'>");

//        String error_msg = (String) request.getAttribute("error_msg");
        pw.print("<section class='createContent'>");

        if (error) {
            pw.print("Unable to display content for this user");
        }

        pw.print("<form action='CreateUser' method='post'>");

        pw.print("<table style='width: 100%'>");

        pw.print("<tr class='rowTable'>");
        pw.print("<td class='leftDataTable'>");
        pw.print("User Name:");
        pw.print("</td>");
        pw.print("<td class='rightDataTable'>");
        pw.print("<input style='radius: 20px; width: 100%;' type='text' placeholder='Enter User Name' name='userName' required");
        pw.print("</td>");
        pw.print("</tr>");

        pw.print("<tr class='rowTable'>");
        pw.print("<td class='leftDataTable'>");
        pw.print("Password:");
        pw.print("</td>");
        pw.print("<td class='rightDataTable'>");
        pw.print("<input style='radius: 20px; width: 100%;' type='password' placeholder='Enter User Password' name='password' required");
        pw.print("</td>");
        pw.print("</tr>");

        pw.print("<tr class='rowTable'>");
        pw.print("<td class='leftDataTable'>");
        pw.print("RePassword:");
        pw.print("</td>");
        pw.print("<td class='rightDataTable'>");
        pw.print("<input style='radius: 20px; width: 100%;' type='password' placeholder='Enter User RePassword' name='rePassword' required");
        pw.print("</td>");
        pw.print("</tr>");

        pw.print("</table>");

        pw.print("<div class='buttonContainer'><button class='classicButton' type='submit' value='Submit'>Create User</button></div>");

        pw.print("</form>");

        pw.print("</section>");
        pw.print("</div>");

        pw.print("</div>");
        pw.print("</div>");
        pw.print("</div>");
        utility.printHtml("Footer.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("----------------- Inside Create User Post ----------------");
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();


        Utilities utility = new Utilities(request, pw);
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");

        System.out.println(name + " " + password + " " + usertype);
        //get the userdata from sql database to hashmap
        Map<String, User> hm = new HashMap<>();

        if (hm.containsKey(name)) {
            error_msg = "Username already exist as " + usertype.toString();
        } else {
            User user = new User(name, password, usertype);
            hm.put(name, user);

            HttpSession session = request.getSession(true);
            if (!utility.isLoggedin()) {
                session.setAttribute("login_msg", "Your " + usertype.toString() + " account has been created. Please login");
                response.sendRedirect("Login");
            } else {
                response.sendRedirect("UserCreated");
            }
        }
        displayContainer(request, response, utility, pw, true);
    }
}