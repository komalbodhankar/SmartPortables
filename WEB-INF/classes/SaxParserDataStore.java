
/*********


 http://www.saxproject.org/

 SAX is the Simple API for XML, originally a Java-only API.
 SAX was the first widely adopted API for XML in Java, and is a �de facto� standard.
 The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java.

 The following URL from Oracle is the JAVA documentation for the API

 https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
 *********/


import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

 SAX parser use callback function  to notify client object of the XML document structure.
 You should extend DefaultHandler and override the method when parsin the XML document
 ***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    Wearable wearable;
    Phone phone;
    Laptop laptop;
    Accessory accessory;
    static HashMap<String, Wearable> wearables;
    static HashMap<String, Phone> phones;
    static HashMap<String, Laptop> laptops;
    static HashMap<String, Accessory> accessories;
    String consoleXmlFileName;
    HashMap<String, String> accessoryHashMap;
    String elementValueRead;
    String currentElement = "";

    public SaxParserDataStore() {
    }

    public SaxParserDataStore(String consoleXmlFileName) {
        this.consoleXmlFileName = consoleXmlFileName;
        wearables = new HashMap<String, Wearable>();
        phones = new HashMap<String, Phone>();
        laptops = new HashMap<String, Laptop>();
        accessories = new HashMap<String, Accessory>();
        accessoryHashMap = new HashMap<String, String>();
        parseDocument();
    }

    //parse the xml using sax parser to get the data
    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }


////////////////////////////////////////////////////////////

    /*************

     There are a number of methods to override in SAX handler  when parsing your XML document :

     Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document.
     Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.
     Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


     There are few other methods that you could use for notification for different purposes, check the API at the following URL:

     https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     ***************/

////////////////////////////////////////////////////////////

    // when xml start element is parsed store the id into respective hashmap for console,games etc
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        try {
            System.out.println("******************** start of the element ******************************");
            System.out.println("elementName " + elementName);
            if (elementName.equalsIgnoreCase("wearable")) {
                currentElement = "wearable";
                wearable = new Wearable();
                wearable.setId(attributes.getValue("id"));
            }
            if (elementName.equalsIgnoreCase("laptop")) {
                currentElement = "laptop";
                laptop = new Laptop();
                laptop.setId(attributes.getValue("id"));
            }
            if (elementName.equalsIgnoreCase("phone")) {
                currentElement = "phone";
                phone = new Phone();
                phone.setId(attributes.getValue("id"));
            }
            if (elementName.equals("accessory") && !currentElement.equals("wearable")) {
                currentElement = "accessory";
                accessory = new Accessory();
                accessory.setId(attributes.getValue("id"));
            }
            System.out.println("********************************* end of the element ************************");

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    // when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {

        try {
            if (element.equals("wearable")) {
                wearables.put(wearable.getId(), wearable);
                return;
            }

            if (element.equals("laptop")) {
                laptops.put(laptop.getId(), laptop);
                return;
            }
            if (element.equals("phone")) {
                phones.put(phone.getId(), phone);
                return;
            }
            if (element.equals("accessory") && currentElement.equals("accessory")) {
                accessories.put(accessory.getId(), accessory);
                return;
            }
            if (element.equals("accessory") && currentElement.equals("wearable")) {
                accessoryHashMap.put(elementValueRead, elementValueRead);
            }
//            Testing corousel

            if (element.equalsIgnoreCase("accessories") && currentElement.equals("wearable")) {
                wearable.setAccessories(accessoryHashMap);
                accessoryHashMap = new HashMap<>();
                return;
            }

            if (element.equalsIgnoreCase("accessories") && currentElement.equals("phone")) {
                phone.setAccessories(accessoryHashMap);
                accessoryHashMap = new HashMap<>();
                return;
            }

            if (element.equalsIgnoreCase("image")) {
                if (currentElement.equals("wearable"))
                    wearable.setImage(elementValueRead);
                if (currentElement.equals("phone"))
                    phone.setImage(elementValueRead);
                if (currentElement.equals("laptop"))
                    laptop.setImage(elementValueRead);
                if (currentElement.equals("accessory"))
                    accessory.setImage(elementValueRead);
                return;
            }


            if (element.equalsIgnoreCase("discount")) {
                if (currentElement.equals("wearable"))
                    wearable.setDiscount(Double.parseDouble(elementValueRead));
                if (currentElement.equals("phone"))
                    phone.setDiscount(Double.parseDouble(elementValueRead));
                if (currentElement.equals("laptop"))
                    laptop.setDiscount(Double.parseDouble(elementValueRead));
                if (currentElement.equals("accessory"))
                    accessory.setDiscount(Double.parseDouble(elementValueRead));
                return;
            }


            if (element.equalsIgnoreCase("condition")) {
                if (currentElement.equals("wearable"))
                    wearable.setCondition(elementValueRead);
                if (currentElement.equals("phone"))
                    phone.setCondition(elementValueRead);
                if (currentElement.equals("laptop"))
                    laptop.setCondition(elementValueRead);
                if (currentElement.equals("accessory"))
                    accessory.setCondition(elementValueRead);
                return;
            }

            if (element.equalsIgnoreCase("description")) {
                if (currentElement.equals("wearable"))
                    wearable.setDescription(elementValueRead);
                if (currentElement.equals("phone"))
                    phone.setDescription(elementValueRead);
                if (currentElement.equals("laptop"))
                    laptop.setDescription(elementValueRead);
                if (currentElement.equals("accessory"))
                    accessory.setDescription(elementValueRead);
                return;
            }

            if (element.equalsIgnoreCase("rebate")) {
                if (currentElement.equals("wearable"))
                    wearable.setRebate(Double.parseDouble(elementValueRead));
                if (currentElement.equals("phone"))
                    phone.setRebate(Double.parseDouble(elementValueRead));
                if (currentElement.equals("laptop"))
                    laptop.setRebate(Double.parseDouble(elementValueRead));
                if (currentElement.equals("accessory"))
                    accessory.setRebate(Double.parseDouble(elementValueRead));
                return;
            }

            if (element.equalsIgnoreCase("manufacturer")) {
                if (currentElement.equals("wearable"))
                    wearable.setRetailer(elementValueRead);
                if (currentElement.equals("phone"))
                    phone.setRetailer(elementValueRead);
                if (currentElement.equals("laptop"))
                    laptop.setRetailer(elementValueRead);
                if (currentElement.equals("accessory"))
                    accessory.setRetailer(elementValueRead);
                return;
            }

            if (element.equalsIgnoreCase("name")) {
                if (currentElement.equals("wearable"))
                    wearable.setName(elementValueRead);
                if (currentElement.equals("phone"))
                    phone.setName(elementValueRead);
                if (currentElement.equals("laptop"))
                    laptop.setName(elementValueRead);
                if (currentElement.equals("accessory"))
                    accessory.setName(elementValueRead);
                return;
            }

            if (element.equalsIgnoreCase("price")) {
                if (currentElement.equals("wearable"))
                    wearable.setPrice(Double.parseDouble(elementValueRead));
                if (currentElement.equals("phone"))
                    phone.setPrice(Double.parseDouble(elementValueRead));
                if (currentElement.equals("laptop"))
                    laptop.setPrice(Double.parseDouble(elementValueRead));
                if (currentElement.equals("accessory"))
                    accessory.setPrice(Double.parseDouble(elementValueRead));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////

    //call the constructor to parse the xml and get product details
    public static void addHashmap() {
        String TOMCAT_HOME = System.getProperty("catalina.home");
        SaxParserDataStore saxParserDataStore = new SaxParserDataStore(TOMCAT_HOME + "/webapps/SmartPortables/ProductCatalog.xml");
    }
}
