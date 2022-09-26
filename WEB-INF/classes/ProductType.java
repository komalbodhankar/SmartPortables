import java.util.Arrays;
import java.util.List;

public enum ProductType {
    Wearable, Phone, Laptop, Assistant;

    public static ProductType getEnum(String type) {
        try {
            List<ProductType> productTypes = Arrays.asList(ProductType.values());
            return productTypes.stream().filter(x -> x.toString().equalsIgnoreCase(type)).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
}
