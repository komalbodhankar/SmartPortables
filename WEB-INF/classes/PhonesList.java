import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PhonesList")

public class PhonesList extends HttpServlet {

	/* Games Page Displays all the Games and their Information in Game Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Product> hm = new HashMap<>();
		
		if(CategoryName==null)
		{
			hm.putAll(SaxParserDataStore.phones);
			name = "";
		}
		else {
			if (CategoryName.equals("apple")) {
				for (Map.Entry<String, Product> entry : SaxParserDataStore.phones.entrySet()) {
					if (entry.getValue().getRetailer().equals("Apple")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Apple";
			} else {
				if (CategoryName.equals("pixel")) {
					for (Map.Entry<String, Product> entry : SaxParserDataStore.phones.entrySet()) {
						if (entry.getValue().getRetailer().equals("Google Pixel")) {
							hm.put(entry.getValue().getId(), entry.getValue());
						}
					}
					name = "Google Pixel";
			}else
				{
				if (CategoryName.equals("samsung")) {
					for (Map.Entry<String, Product> entry : SaxParserDataStore.phones.entrySet()) {
						if (entry.getValue().getRetailer().equals("Samsung Galaxy")) {
							hm.put(entry.getValue().getId(), entry.getValue());
						}
					}
					name = "Samsung Galaxy";
			}else {
					if (CategoryName.equals("oneplus")) {
						for (Map.Entry<String, Product> entry : SaxParserDataStore.phones.entrySet()) {
							if (entry.getValue().getRetailer().equals("One Plus")) {
								hm.put(entry.getValue().getId(), entry.getValue());
							}
						}
						name = "One Plus";
			}else {
					if (CategoryName.equals("nokia")) {
						for (Map.Entry<String, Product> entry : SaxParserDataStore.phones.entrySet()) {
							if (entry.getValue().getRetailer().equals("Nokia")) {
								hm.put(entry.getValue().getId(), entry.getValue());
							}
						}
						name = "Nokia";
			}
					}
				}
			}
		}
	}


		/* Header, Left Navigation Bar are Printed.

		All the Games and Games information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Phones</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Product> entry : hm.entrySet()){
			Product phone = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+phone.getName()+"</h3>");
			pw.print("<strong>"+ "$" + phone.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/phones/"+phone.getImage()+"' alt='' /></li>");
			pw.print("<li class='description'><span>" + phone.getDescription() + "</span></li>");
			pw.print("<li class='description'><span> Discount:" + phone.getDiscount() + "</span></li>");
			pw.print("<li class='description'><span> Rebate:" + phone.getRebate() + "</span></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='phones'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
