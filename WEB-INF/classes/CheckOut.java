import javafx.scene.control.CustomMenuItem;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.time.Instant;
import java.time.Period;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
//		PrintWriter pw = response.getWriter();
//	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");



		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
			pw.print("<table  class='gridtable'>" +
					"<tr class='rowTable'>" +
					"<td class='leftDataTable'>User Id:</td>" +
					"<td>" +userName +"</td>" +
					"</tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td>");
			pw.print(oi.getName()+"</td></tr>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("<input type='hidden' name='discount' value='"+oi.getDiscount()+"'>");
			pw.print("<input type='hidden' name='rebate' value='"+oi.getRebate()+"'>");
			pw.print("<tr><td> Product Discount:</td><td>" + oi.getDiscount() + "</td></tr>");
			pw.print("<tr><td> Product Rebate:</td><td>" + oi.getRebate() + "</td></tr>");
			pw.print("<tr><td> Product Price:</td><td>" + oi.getPrice() + "</td></tr>");
		}
		if(UserType.SalesMan.equals(utility.usertype())) {
			pw.print("<tr class='rowTable'>");
			pw.print("<td class='leftDataTable'>");
			pw.print("Select UserName:");
			pw.print("</td>");
			pw.print("<td class='rightDataTable'>");
			pw.print("<select style='radius: 20px; width: 100%;' name='userId' id='userId'>");
			for(User user: utility.getUsers().values()) {
				pw.print("<option value='" + user.getId() + "'>" + user.getName() + "</option>");
			}
			pw.print("</td>");
			pw.print("</tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</td></tr></table><table><tr></tr><tr></tr>");

		pw.print("<tr><td>");
		pw.print("First Name</td>");
		pw.print("<td><input type='text' name='firstName'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Last Name</td>");
		pw.print("<td><input type='text' name='lastName'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Address 1</td>");
		pw.print("<td><input type='text' name='address1'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Address 2</td>");
		pw.print("<td><input type='text' name='address2'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("City</td>");
		pw.print("<td><input type='text' name='city'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("State</td>");
		pw.print("<td><input type='text' name='state'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Zip Code</td>");
		pw.print("<td><input type='text' name='zipcode'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Phone Number</td>");
		pw.print("<td><input type='text' name='phone'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("Delivery Option</td>");
		pw.print("<td><input type='radio' id='homeDelivery' name='order' value='homeDelivery' checked><label for='homeDelivery'>Delivery</label><br>");
		pw.print("<input type='radio' id='storePickup' name='order' value='storePickup'><label for='storePickup'>Pickup</label>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		pw.print("<label for='storePickup'>Choose a pick-up location:</label>");
		pw.print("<select name='order' id='storePickup'> " +
				"<option value='location1'>Location 1</option> " +
				"<option value='location2'>Location 2</option> " +
				"<option value='location3'>Location 3</option> " +
				"<option value='location4'>Location 4</option> " +
				"<option value='location5'>Location 5</option> " +
				"<option value='location6'>Location 6</option> " +
				"<option value='location7'>Location 7</option> " +
				"<option value='location8'>Location 8</option> " +
				"<option value='location9'>Location 9</option> " +
				"<option value='location10'>Location 10</option> " +
				"</select>");
		pw.print("</td></tr>");


		pw.print("<tr><td colspan='2'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
