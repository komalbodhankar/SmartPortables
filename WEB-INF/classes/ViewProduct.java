import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ViewProduct")
public class ViewProduct extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession(true);
        Carousel carousel = new Carousel();

        String name = null;
        String CategoryName = request.getParameter("maker");
        String productType = request.getParameter("type");
        String prodType = null;
        ProductType type = ProductType.getEnum(productType);

        Map<String, Product> productsMap = new HashMap<>();

        switch (type) {
            case Wearable:
                prodType = "wearables";
                productsMap.putAll(SaxParserDataStore.wearables);
                break;
            case Phone:
                prodType = "phones";
                productsMap.putAll(SaxParserDataStore.phones);
                break;
            case Laptop:
                prodType = "laptops";
                productsMap.putAll(SaxParserDataStore.laptops);
                break;
        }

        UserType userType = session.getAttribute("userType") != null ? UserType.getEnum(session.getAttribute("userType").toString()) :
                null;

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>" + productsMap.get("name") + "</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");

        int i = 1; int size= productsMap.size();
        for(Map.Entry<String, Product> entry : productsMap.entrySet()){
            Product phone = entry.getValue();
            if(i%3==1) pw.print("<tr>");
            pw.print("<td><div id='shop_item'>");
            pw.print("<h3>"+phone.getName()+"</h3>");
            pw.print("<strong>"+ "$" + phone.getPrice() + "</strong><ul>");
            pw.print("<li id='item'><img src='images/phones/"+phone.getImage()+"' alt='' /></li>");
            pw.print("<li class='description'><span>" + phone.getDescription() + "</span></li>");
            pw.print("<li class='description'><span> Discount:" + phone.getDiscount() + "</span></li>");
            pw.print("<li class='description'><span> Rebate:" + phone.getRebate() + "</span></li>");
            pw.print("<li><form method='post' action='ViewPhone'>" +
                    "<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
                    "<input type='hidden' name='type' value='"+prodType+"'>"+
                    "<input type='hidden' name='maker' value='"+CategoryName+"'>"+
                    "<input type='hidden' name='access' value=''>"+
                    "<input type='submit' class='btnbuy' value='View Product'></form></li>");
            pw.print("</ul></div></td>");
            if(i%3==0 || i == size) pw.print("</tr>");
            i++;
        }
        pw.print("</table></div></div></div>");
        pw.print(carousel.carouselfeature(utility));
        utility.printHtml("Footer.html");

    }
}