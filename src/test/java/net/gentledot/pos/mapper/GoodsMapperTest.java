package net.gentledot.pos.mapper;

import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.goods.GoodsCategories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GoodsMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    @DisplayName("DB 접속 확인 test")
    void getTimeTest() {
        log.debug("현재 시각 : {}", goodsMapper.getTime());
    }

    @Test
    @DisplayName("상품 정보 저장 테스트")
    void saveTest() {
        Goods goods = new Goods.Builder()
                .goodsName("testGoods")
                .goodsCat(GoodsCategories.valueOf("JUICE"))
                .goodsPrice(100)
                .goodsDesc("testDesc")
                .build();

        int result = goodsMapper.save(goods);

        assertThat(result, is(1));

    }

    @Test
    @DisplayName("상품 조회 테스트 - goodsNo")
    void findByIdTest() {
        Goods goods = goodsMapper.findById(1L).orElse(null);
        log.debug("goods : {}", goods);

        assertThat(goods, is(notNullValue()));
        assertThat(goods.getGoodsCode(), is("2020cat1"));
        assertThat(goods.getGoodsName(), is("testname"));
        assertThat(goods.getGoodsPrice(), is(100));
        assertThat(goods.getGoodsCat(), is("cat"));
        assertThat(goods.getGoodsDesc().get(), is("testdesc"));
        assertThat(goods.isForSaleGoods(), is(true));
    }

    @Test
    @DisplayName("상품 조회 테스트 - goodsCode")
    void findByCodeTest() {
        Goods goods = goodsMapper.findByCode("2020cat1").orElse(null);
        log.debug("goods : {}", goods);

        assertThat(goods, is(notNullValue()));
        assertThat(goods.getGoodsCode(), is("2020cat1"));
        assertThat(goods.getGoodsName(), is("testname"));
        assertThat(goods.getGoodsPrice(), is(100));
        assertThat(goods.getGoodsCat(), is("cat"));
        assertThat(goods.getGoodsDesc().get(), is("testdesc"));
        assertThat(goods.isForSaleGoods(), is(true));
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void findAllTest() {
        List<Goods> all = goodsMapper.findAll();
        log.debug("조회된 목록 : {}", all);

        assertThat(all.get(0).getGoodsCode(), is("2020cat1"));
        assertThat(all.get(0).getGoodsName(), is("testname"));
        assertThat(all.get(0).getGoodsPrice(), is(100));
        assertThat(all.get(0).getGoodsCat(), is("cat"));
        assertThat(all.get(0).getGoodsDesc().get(), is("testdesc"));
        assertThat(all.get(0).isForSaleGoods(), is(true));
    }

    @Test
    @DisplayName("상품 정보 수정 테스트")
    void updateTest() {
        Goods oldGoods = goodsMapper.findByCode("2020cat1").orElse(null);
        log.debug("조회한 상품 : {}", oldGoods);
        String beforeDesc = oldGoods.getGoodsDesc().orElse("");
        int beforePrice = oldGoods.getGoodsPrice();

        Goods modified = new Goods.Builder(oldGoods)
                .goodsPrice(255)
                .goodsDesc("한국어 설명")
                .goodsStatus(true)
                .build();

        int result = goodsMapper.update(modified);

        Goods goods = goodsMapper.findByCode("2020cat1").orElse(null);

        assertThat(result, is(1));
        assertThat(goods.getGoodsPrice().equals(beforePrice), is(false));
        assertThat(goods.getGoodsPrice(), is(255));
        assertThat(goods.getGoodsDesc().get().equals(beforeDesc), is(false));
        assertThat(goods.getGoodsDesc().get(), is("한국어 설명"));
        assertThat(goods.isForSaleGoods(), is(true));
    }
}