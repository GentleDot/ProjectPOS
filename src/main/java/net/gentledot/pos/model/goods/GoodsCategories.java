package net.gentledot.pos.model.goods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum GoodsCategories {
    BAKERY("bake"), DESSERT("dess"), BRUNCH("brun"), JUICE("juic"), JAMNSOURCE("jams");

    private final String code;
    private static Map<String, GoodsCategories> categoryMap = new HashMap<>();

    GoodsCategories(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static GoodsCategories getCategory(String code) {
        if (categoryMap.isEmpty()) {
            categoryMap = Arrays.stream(GoodsCategories.values()).collect(Collectors.toMap(GoodsCategories::getCode, goodsCategories -> goodsCategories));
        }
        /*Arrays.stream(GoodsCategories.values()).filter(category -> category.code.equals(code))
                .findFirst().orElse(null);*/

        return categoryMap.get(code);
    }
}
