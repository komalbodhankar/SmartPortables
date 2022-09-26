import java.util.Arrays;
import java.util.List;

public enum CategoryNames {
    FitnessWatches, SmartWatches, HeadPhones, VirtualReality, PetTracker,
    Apple, Samsung, Motorola, Google, OnePlus, MicroSoft, Dell, HP, Lenovo, Navi, Rufus,
    Amazon, Sony, Nintendo;

    public static CategoryNames getEnum(String productManufacturer) {
        try {
            List<CategoryNames> categoryNames = Arrays.asList(CategoryNames.values());
            return categoryNames.stream().filter(x -> x.toString().equalsIgnoreCase(productManufacturer)).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
}
