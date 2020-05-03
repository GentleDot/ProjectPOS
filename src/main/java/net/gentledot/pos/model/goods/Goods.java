package net.gentledot.pos.model.goods;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.StandardCharsets;

public class Goods {
    private long goodsNo;
    private String goodsCode;
    private String goodsName;
    private int goodsPrice;
    private String goodsCat;
    private String goodsDesc;
    private boolean status;

    private Goods(Long goodsNo, String goodsCode, String goodsName, Integer goodsPrice, String goodsCat, String goodsDesc, boolean status) {
        if (StringUtils.isBlank(goodsCode)) {
            throw new RuntimeException("상품코드는 필수 입니다.");
        } else if (goodsCode.length() > 20) {
            throw new RuntimeException("상품코드는 20자 내외로 설정할 수 있습니다.");
        } else if (StringUtils.isBlank(goodsName)) {
            throw new RuntimeException("상품명은 필수 입니다.");
        } else if (goodsName.getBytes(StandardCharsets.UTF_8).length > 50) {
            throw new RuntimeException("상품명은 50bytes 내외로 설정할 수 있습니다.");
        } else if (goodsPrice == null || goodsPrice <= 0) {
            throw new RuntimeException("유효하지 않은 상품가격입니다.");
        } else if (StringUtils.isBlank(goodsCat)) {
            throw new RuntimeException("상품카테고리는 필수 입니다.");
        }

        this.goodsNo = ObjectUtils.defaultIfNull(goodsNo, 0L);
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsCat = goodsCat;
        this.goodsDesc = goodsDesc;
        this.status = status;
    }

    public long getGoodsNo() {
        return goodsNo;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public String getGoodsCat() {
        return goodsCat;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public boolean isForSaleGoods() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("goodsNo", goodsNo)
                .append("goodsCode", goodsCode)
                .append("goodsName", goodsName)
                .append("goodsPrice", goodsPrice)
                .append("goodsCat", goodsCat)
                .append("goodsDesc", goodsDesc)
                .append("forSale(status)", status)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        return new EqualsBuilder()
                .append(goodsNo, goods.goodsNo)
                .append(goodsCode, goods.goodsCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsNo)
                .append(goodsCode)
                .toHashCode();
    }


    public static final class Builder {
        private Long goodsNo;
        private String goodsCode;
        private String goodsName;
        private Integer goodsPrice;
        private String goodsCat;
        private String goodsDesc;
        private boolean status;

        protected Builder() {
        }

        public Builder(String goodsCode) {
            this.goodsCode = goodsCode;
            this.status = true;
        }

        public Builder(Goods goods) {
            this.goodsNo = goods.goodsNo;
            this.goodsCode = goods.goodsCode;
            this.goodsName = goods.goodsName;
            this.goodsPrice = goods.goodsPrice;
            this.goodsCat = goods.goodsCat;
            this.goodsDesc = goods.goodsDesc;
            this.status = goods.status;
        }

        public Builder goodsName(String goodsName) {
            this.goodsName = goodsName;
            return this;
        }

        public Builder goodsPrice(Integer goodsPrice) {
            this.goodsPrice = goodsPrice;
            return this;
        }

        public Builder goodsCat(String goodsCat) {
            this.goodsCat = goodsCat;
            return this;
        }

        public Builder goodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
            return this;
        }

        public Builder goodsStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Goods build() {
            return new Goods(goodsNo, goodsCode, goodsName, goodsPrice, goodsCat, goodsDesc, status);
        }
    }
}
