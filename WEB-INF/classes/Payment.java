import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.Period;
import java.util.Random;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String address1=request.getParameter("address1");
		String address2=request.getParameter("address2");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String zipcode=request.getParameter("zipcode");
		String phone=request.getParameter("phone");
		String order = request.getParameter("order");
		String creditCardNo=request.getParameter("creditCardNo");

		validation(firstName, lastName, address1, city, state, zipcode,
				phone, order, creditCardNo, utility, pw);

		if(!firstName.isEmpty()) {
			//Random rand = new Random(); 
			//int orderId = rand.nextInt(100);
			int orderId=utility.getOrderPaymentSize()+1;

			//iterate through each order

			for (OrderItem oi : utility.getCustomerOrders())
			{

				//set the parameter for each column and execute the prepared statement

				utility.storePayment(orderId,
						oi.getName(),
						oi.getPrice(),
						oi.getDiscount(),
						oi.getRebate(),
						(oi.getbuyWarranty() ? 25.0 : 0.0),
						firstName,
						lastName,
						address1,
						address2,
						city,
						state,
						zipcode,
						phone,
						creditCardNo);
			}

			Instant now = Instant.now();
			System.out.println(now);
			Instant update = now.plus(Period.ofDays(14));
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ) .withLocale( Locale.US )
					.withZone( ZoneId.systemDefault() );
			String instantStr = formatter.format(update);

			//remove the order details from cart after processing
			
			OrdersHashMap.orders.remove(utility.username());	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			pw.print("<h2>Your Order");
			pw.print("&nbsp&nbsp");  
			pw.print("is stored ");
			pw.print("<br>Your Order Confirmation No is "+(orderId));
			pw.print("<br>Pickup / Delivery date is "+(instantStr));
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");
		}else
		{
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			pw.print("<h4 style='color:red'>Please enter valid address and creditcard number</h4>");
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");
		}	
	}

	private void validation(String firstName, String lastName, String address1,
							String city, String state, String zipCode, String phone,
							String order, String creditCardNo, Utilities utilities, PrintWriter pw) {
		if (firstName.isEmpty()) {
			sendError(utilities, pw, "first name");
			return;}
		if (lastName.isEmpty()) {
			sendError(utilities, pw, "last name");
			return;}
		if (address1.isEmpty()) {
			sendError(utilities, pw, "address1");
			return;}
		if (city.isEmpty()) {
			sendError(utilities, pw, "city");
			return;}
		if (state.isEmpty()) {
			sendError(utilities, pw, "state");
			return;}
		if (zipCode.isEmpty()) {
			sendError(utilities, pw, "zip code");
			return;}
		if (phone.isEmpty()) {
			sendError(utilities, pw, "phone");
			return;}
		if (creditCardNo.isEmpty()) {
			sendError(utilities, pw, "Credit Card Number");
		}
	}

	private void sendError(Utilities utilities, PrintWriter pw, String errorName) {
		utilities.printHtml("Header.html");
		utilities.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");

		pw.print("<h4 style='color:red'>Please enter " + errorName + "</h4>");
		pw.print("</h2></div></div></div>");
		utilities.printHtml("Footer.html");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		
	}
}
