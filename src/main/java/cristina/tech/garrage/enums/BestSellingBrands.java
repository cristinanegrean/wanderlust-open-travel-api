package cristina.tech.garrage.enums;

import java.util.Arrays;
import java.util.function.Predicate;

public enum BestSellingBrands {
    RENAULT("Renault", "Megane 3 Dynamique", "Family"),
    DACIA("Dacia", "Duster", "Economy"),
    MINI("Mini", "Cooper S", "Sports"),
    TESLA("Tesla", "Model 3", "Luxury");

    private String brandName;
    private String model;
    private String classification;

    BestSellingBrands(String brandName, String model, String classification) {
        this.brandName = brandName;
        this.classification = classification;
    }

    public static boolean isBestSelling(String brandName, String model) {
        Predicate<BestSellingBrands> bp = t -> t.brandName.equals(brandName) && t.model.matches(model);

        return Arrays.asList(BestSellingBrands.values()).stream().anyMatch(bp);
    }

}
