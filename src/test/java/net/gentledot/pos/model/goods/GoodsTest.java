package net.gentledot.pos.model.goods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class GoodsTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("상품 생성 테스트")
    void createGoodsTest() {
        Goods goods = new Goods.Builder()
                .goodsName("testName")
                .goodsPrice(100)
                .goodsCat(GoodsCategories.BAKERY)
                .build();

        assertThat(goods, is(notNullValue()));
        // [goodsNo=0,goodsCode=<null>,goodsName=testName,goodsPrice=100,goodsCat=test,goodsDesc=<null>,forSale(status)=true]
        log.debug("생성된 goods : {}", goods);
    }

    @Test
    @DisplayName("상품 카테고리를 enum 객체로 생성하여 활용")
    void CategoryEnumTest() {
        GoodsCategories bakery = GoodsCategories.valueOf("BAKERY");
        String code = bakery.getCode();
        GoodsCategories bakery_dir = GoodsCategories.BAKERY;

        log.debug("카테고리 코드 확인 : {}", code);

        GoodsCategories category = GoodsCategories.getCategory(code);
        log.debug("코드로 카테고리 확인 : {}", category);

        assertThat(bakery.equals(bakery_dir), is(true));
        assertThat(bakery_dir.equals(category), is(true));
    }

}