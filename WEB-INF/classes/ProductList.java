import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductList")

public class ProductList extends HttpServlet {

	/* Console Page Displays all the Consoles and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String categoryName = request.getParameter("maker");
		String productType = request.getParameter("type");
		String prodType = null;
		ProductType type = ProductType.getEnum(productType);
        

		/* Checks the Tablets type whether it is microsoft or sony or nintendo */

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


		if (categoryName != null && !categoryName.isEmpty()) {
			Map<String, Product> temp = new HashMap<>();
			name = categoryName;
			for (Map.Entry<String, Product> entry : productsMap.entrySet()) {
				System.out.println(entry.getValue());
				if (categoryName.equalsIgnoreCase(entry.getValue().getCategoryName())) {
					temp.put(entry.getKey(), entry.getValue());
				}
			}
			productsMap = temp;
		}


		
		/* Header, Left Navigation Bar are Printed.

		All the Console and Console information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Products</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= productsMap.size();

		for(Map.Entry<String, Product> entry : productsMap.entrySet())
		{
			Product product = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+ product.getName()+"</h3>");
			pw.print("<strong>$"+ product.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/products/"+ product.getImage()+"' alt='' /></li>");
			pw.print("<li class='description'><span>" + product.getDescription() + "</span></li>");
			pw.print("<li class='description'><span> Discount:" + product.getDiscount() + "</span></li>");
			pw.print("<li class='description'><span> Rebate:" + product.getRebate() + "</span></li>");
			pw.print("<li class='description'><span> Warranty:" + product.getWarrantyPrice() + "</span></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='"+prodType+"'>"+
					"<input type='hidden' name='maker' value='"+categoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='ViewProduct'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='"+prodType+"'>"+
					"<input type='hidden' name='maker' value='"+categoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='View Product'></form></li>");




			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}	
		pw.print("</table></div></div></div>");
   
		utility.printHtml("Footer.html");
		
	}
}
