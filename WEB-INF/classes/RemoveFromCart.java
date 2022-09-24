import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RemoveFromCart")
public class RemoveFromCart extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);

            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to Remove from cart");
                response.sendRedirect("Login");
                return;
            }
            Object orderIndex = request.getParameter("orderIndex");
            System.out.println("------------ Order Index ---------" + orderIndex);
            int index = Integer.parseInt(orderIndex.toString());

            utility.getCustomerOrders().remove(index);
            response.sendRedirect("Cart");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
