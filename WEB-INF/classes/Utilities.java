import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet {
    HttpServletRequest req;
    PrintWriter pw;
    String url;
    HttpSession session;

    public Utilities(HttpServletRequest req, PrintWriter pw) {
        this.req = req;
        this.pw = pw;
        this.url = this.getFullURL();
        this.session = req.getSession(true);
    }



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

    public void printHtml(String file) {
        String result = HtmlToString(file);
        //to print the right navigation in header of username cart and logout etc
        if (file == "Header.html") {
            result = result + "<div id='menu' style='float: right;'><ul>";
            if (session.getAttribute("username") != null) {
                String username = session.getAttribute("username").toString();
                UserType userType = UserType.getEnum(session.getAttribute("usertype").toString());
                System.out.println("---" + userType + "---");
                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
                result = result + "<li><a><span class='glyphicon'>Hello," + username + "</span></a></li>";
                switch (userType){
                    case Customer : result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
                            + "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
                            + "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount() + ")</span></a></li>";
                    break;
                    case SalesMan: result = result + "<li><a href='CreateUser'><span class='glyphicon'>CreateUser</span></a></li>"
                            + "<li><a href='ViewCustomerOrder'><span class='glyphicon'>View Orders</span></a></li>"
                            + "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount() + ")</span></a></li>";
                    break;
                }
                result += "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
            } else {
                result += "<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>";
                result += "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
            }
            result += "</ul></div></div><div id='page'>";
            pw.print(result);
        } else
            pw.print(result);
    }


    /*  getFullURL Function - Reconstructs the URL user request  */

    public String getFullURL() {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        url.append("/");
        return url.toString();
    }

    /*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
    public String HtmlToString(String file) {
        String result = null;
        try {
            String webPage = url + file;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();
        } catch (Exception e) {
        }
        return result;
    }

    /*  logout Function removes the username , usertype attributes from the session variable*/

    public void logout() {
        session.removeAttribute("username");
        session.removeAttribute("usertype");
    }

    /*  logout Function checks whether the user is loggedIn or Not*/

    public boolean isLoggedin() {
        if (session.getAttribute("username") == null)
            return false;
        return true;
    }

    /*  username Function returns th    e username from the session variable.*/

    public String username() {
        if (session.getAttribute("username") != null)
            return session.getAttribute("username").toString();
        return null;
    }

    /*  usertype Function returns the usertype from the session variable.*/
    public String usertype() {
        if (session.getAttribute("usertype") != null)
            return session.getAttribute("usertype").toString();
        return null;
    }

    public Map<String, User> getUsers() {
        Map<String, User> userNameUserObjectMap = new HashMap<>();
        String TOMCAT_HOME = System.getProperty("catalina.home");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "/webapps/SmartPortables/UserDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            userNameUserObjectMap = (HashMap) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userNameUserObjectMap;
    }

    /*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
    public User getUser() {
        Map<String, User> users = getUsers();
        User user = users.get(username());
        return user;
    }

    /*  getCustomerOrders Function gets  the Orders for the user*/
    public ArrayList<OrderItem> getCustomerOrders() {
        ArrayList<OrderItem> order = new ArrayList<OrderItem>();
        if (OrdersHashMap.orders.containsKey(username()))
            order = OrdersHashMap.orders.get(username());
        return order;
    }

    public ArrayList<OrderItem> getCustomerOrders(String username) {
        ArrayList<OrderItem> order = new ArrayList<OrderItem>();
        if (OrdersHashMap.orders.containsKey(username))
            order = OrdersHashMap.orders.get(username);
        return order;
    }

    /*  getOrdersPaymentSize Function gets  the size of OrderPayment */
    public int getOrderPaymentSize() {
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
        String TOMCAT_HOME = System.getProperty("catalina.home");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "/webapps/SmartPortables/PaymentDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            orderPayments = (HashMap) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int size = 0;
        for (Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
            size = size + 1;

        }
        return size;
    }

    /*  CartCount Function gets  the size of User Orders*/
    public int CartCount() {
        if (isLoggedin())
            return getCustomerOrders().size();
        return 0;
    }

    /* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

    public void storeProduct(String name, String type, String maker, String acc) {
        if (!OrdersHashMap.orders.containsKey(username())) {
            ArrayList<OrderItem> arr = new ArrayList<>();
            OrdersHashMap.orders.put(username(), arr);
        }
        ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
        if (type.equals("wearables")) {
            Product product;
            product = SaxParserDataStore.wearables.get(name);
            OrderItem orderitem = new OrderItem(product.getName(), product.getPrice(), product.getImage(), product.getRetailer(), product.getDiscount(), product.getRebate(), product.getbuyWarranty());
            orderItems.add(orderitem);
        }
        if (type.equals("phones")) {
            Product product;
            product = SaxParserDataStore.phones.get(name);
            OrderItem orderitem = new OrderItem(product.getName(), product.getPrice(), product.getImage(), product.getRetailer(), product.getDiscount(), product.getRebate(), product.getbuyWarranty());
            orderItems.add(orderitem);
        }
        if (type.equals("laptops")) {
            Product product;
            product = SaxParserDataStore.laptops.get(name);
            OrderItem orderitem = new OrderItem(product.getName(), product.getPrice(), product.getImage(), product.getRetailer(), product.getDiscount(), product.getRebate(), product.getbuyWarranty());
            orderItems.add(orderitem);
        }
        if (type.equals("accessories")) {
            Accessory accessory = SaxParserDataStore.accessories.get(name);
            OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer(), accessory.getDiscount(), accessory.getRebate(), accessory.getbuyWarranty());
            orderItems.add(orderitem);
        }

    }

    // store the payment details for orders
    public void storePayment(int orderId,
                             String orderName, double orderPrice, double discount,
                             double rebate, double buyWarranty, String creditCardNo, String firstName, String lastName,
                             String address1, String address2, String city, String state, String zipcode, String phone) {
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
        String TOMCAT_HOME = System.getProperty("catalina.home");
        // get the payment details file
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "/webapps/SmartPortables/PaymentDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            orderPayments = (HashMap) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orderPayments == null) {
            orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        }
        // if there exist order id already add it into same list for order id or create a new record with order id

        if (!orderPayments.containsKey(orderId)) {
            ArrayList<OrderPayment> arr = new ArrayList<>();
            orderPayments.put(orderId, arr);
        }
        ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
        OrderPayment orderpayment = new OrderPayment(orderId, username(), orderName, orderPrice, discount,
                rebate, creditCardNo, firstName, lastName, address1, address2, city, state, zipcode, phone);
        listOrderPayment.add(orderpayment);


        // add order details into file,
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME + "/webapps/SmartPortables/PaymentDetails.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orderPayments);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
