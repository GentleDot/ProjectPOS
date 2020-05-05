package net.gentledot.pos.model.request;

import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.goods.GoodsCategories;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.StandardCharsets;

public class GoodsRequest {
    private String name;
    private int price;
    private GoodsCategories category;
    private String description;
    private boolean status;

    protected GoodsRequest() {

    }

    public GoodsRequest(String name, int price, GoodsCategories category, String description, boolean status) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public GoodsCategories getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return status;
    }

    public Goods generateGoods() {
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("상품명은 필수 입니다.");
        } else if (name.getBytes(StandardCharsets.UTF_8).length > 50) {
            throw new RuntimeException("상품명은 50bytes 내외로 설정할 수 있습니다.");
        } else if (price <= 0) {
            throw new RuntimeException("유효하지 않은 상품가격입니다.");
        } else if (category == null) {
            throw new RuntimeException("상품카테고리는 필수 입니다.");
        } else if (StringUtils.isNotBlank(description) && description.getBytes(StandardCharsets.UTF_8).length > 300) {
            throw new RuntimeException("상품설명은 300bytes 내외로 설정할 수 있습니다.");
        }

        return new Goods.Builder()
                .goodsName(name)
                .goodsPrice(price)
                .goodsCat(category)
                .goodsDesc(description)
                .goodsStatus(status)
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("price", price)
                .append("category", category)
                .append("description", description)
                .append("status", status)
                .toString();
    }
}
