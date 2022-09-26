import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet("/Product")


/* 
	Console class contains class variables name,price,image,retailer,condition,discount and Accessories Hashmap.

	Console class constructor with Arguments name,price,image,retailer,condition,discount and Accessories .
	  
	Accessory class contains getters and setters for name,price,image,retailer,condition,discount and Accessories .

*/

public class Product extends HttpServlet{
	private String id;
	private String name;
	private double price;
	private String image;
	private String retailer;

	private String categoryName;
	private String condition;
	private double discount;

	private double rebate;

	private String description;
	private boolean buyWarranty;

	private double WarrantyPrice;

	HashMap<String,String> accessories;
	public Product(String name, double price, String image, String retailer,
				   String condition, double discount, double rebate, String description, boolean buyWarranty, double WarrantyPrice){
		this.name=name;
		this.price=price;
		this.image=image;
		this.retailer = retailer;
		this.condition=condition;
		this.discount = discount;
		this.rebate = rebate;
		this.description = description;
        this.accessories=new HashMap<>();
		this.buyWarranty = buyWarranty;
		this.WarrantyPrice = WarrantyPrice;
	}
	
    HashMap<String,String> getAccessories() {
		return accessories;
		}

	public Product(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRetailer() {
		return retailer;
	}
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setAccessories(HashMap<String,String> accessories) {
		this.accessories = accessories;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", image='" + image + '\'' +
				", retailer='" + retailer + '\'' +
				", categoryName='" + categoryName + '\'' +
				", condition='" + condition + '\'' +
				", discount=" + discount +
				", rebate=" + rebate +
				", description='" + description + '\'' +
				", accessories=" + accessories + '\'' +
				", warrantyprice=" + WarrantyPrice +
				'}';
	}

	public boolean getbuyWarranty() {
		return buyWarranty;
	}

	public void setbuyWarranty(boolean buyWarranty) {
		this.buyWarranty = buyWarranty;
	}

	public Double getWarrantyPrice() {
		return WarrantyPrice;
	}

	public void setWarrantyPrice(Double WarrantyPrice) {
		this.WarrantyPrice = WarrantyPrice;
	}


}
