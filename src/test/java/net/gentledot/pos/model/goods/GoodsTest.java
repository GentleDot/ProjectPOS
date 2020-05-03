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
        Goods goods = new Goods.Builder("testCode")
                .goodsName("testName")
                .goodsPrice(100)
                .goodsCat("testCategory")
                .build();

        assertThat(goods, is(notNullValue()));
        // [goodsNo=0,goodsCode=testCode,goodsName=testName,goodsPrice=100,goodsCat=testCategory,goodsDesc=<null>,forSale(status)=true]
        log.debug("생성된 goods : {}", goods);
    }

}