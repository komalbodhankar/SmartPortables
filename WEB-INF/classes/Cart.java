import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Cart")

public class Cart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();


			/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

			Utilities utility = new Utilities(request, pw);
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String maker = request.getParameter("maker");
			String access = request.getParameter("access");
			System.out.print("name" + name + "type" + type + "maker" + maker + "accesee" + access);

			/* StoreProduct Function stores the Purchased product in Orders HashMap.*/

			utility.storeProduct(name, type, maker, access);
			displayCart(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}

	}


/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request,pw);
			Carousel carousel = new Carousel();
			HttpSession session = request.getSession(true);
			if(!utility.isLoggedin()){
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}

			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Cart("+utility.CartCount()+")</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<form name ='Cart' action='CheckOut' method='post'>");
			if(utility.CartCount()>0) {
				pw.print("<table  class='gridtable'>");
				pw.print("<tr>");
				pw.print("<th>Number</th>");
				pw.print("<th>Order Name</th>");
				pw.print("<th>Order Price</th>");
				pw.print("<th>Discount</th>");
				pw.print("<th>Rebate</th>");
				pw.print("<th>Remove Item</th>");
				pw.print("</tr>");
				int i = 1;
				double total = 0;
				double totalRebate = 0;
				for (OrderItem oi : utility.getCustomerOrders())
				{
					pw.print("<tr>");
					pw.print("<td>" + i + ".</td>");
					pw.print("<td>"+oi.getName()+"</td><td> "+oi.getPrice()+"</td>");
					pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
					pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
					pw.print("<td>" + (oi.getDiscount()) + "</td>");
					pw.print("<td>" + (oi.getRebate()) + "</td>");
					pw.print("<td>" + (oi.getbuyWarranty() ? 25: 0) + "</td>");
					pw.print("<td class='buttonContainer'>" +
							"<form style='margin:auto' action='RemoveFromCart' method='post'>" +
							"<input type='hidden' name='orderIndex' value='" + (i - 1) + "'>" +
							"<button class='mainButton' type='submit' value='submit'>Remove</button>" +
							"</form>" +
							"</td>");
					pw.print("<tr></tr>");
					total = total +oi.getPrice() + (oi.getbuyWarranty() ? 25: 0) - oi.getDiscount();
					totalRebate += oi.getRebate();
					i++;
				}
				pw.print("<input type='hidden' name='orderTotal' value='"+total+"'>");
				pw.print("<input type='hidden' name='orderTotalRebate' value='" + totalRebate + "'>");
				pw.print("<tr><th></th><th>Total</th><th>"+total+"</th>");
				pw.print("</table>");

				pw.print("<form name ='Cart' action='CheckOut' method='post'>");
				pw.print("<input type='hidden' name='orderTotal' value='" + total + "'>");
				pw.print("<input type='hidden' name='orderTotalRebate' value='" + totalRebate + "'>");
				pw.print("<input type='submit' name='CheckOut' value='CheckOut' class='btnbuy' />");
				pw.print("</form>");
				/* This code is calling Carousel.java code to implement carousel feature*/
				pw.print(carousel.carouselfeature(utility));
			}
			else
			{
				pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
			}
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		displayCart(request, response);
	}
}
