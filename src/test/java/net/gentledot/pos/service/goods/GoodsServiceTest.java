package net.gentledot.pos.service.goods;


import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.goods.GoodsCategories;
import net.gentledot.pos.model.request.GoodsRequest;
import net.gentledot.pos.repository.GoodsMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoodsServiceTest {
    private static final String TEST_GOODS_CODE = "2020bake1";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Mock
    private GoodsMapper goodsMapper;

    @InjectMocks
    private GoodsService goodsService;

    @Test
    @DisplayName("상품을 등록하는 기능 구현")
    void addGoodsTest() {
        // given
        GoodsRequest goodsRequest = new GoodsRequest("testName", 100, GoodsCategories.JUICE, "상품설명", true);
        Goods goods = goodsRequest.generateGoods();

        // when
        when(goodsMapper.save(goods)).thenReturn(1);
        int result = goodsMapper.save(goods);

        if (result != 1) {
            throw new RuntimeException("저장 처리에 실패하였습니다.");
        }

        // then
        assertThat(goodsService.addGoods(goodsRequest), is(true));
    }

    @Test
    @DisplayName("상품 리스트를 조회하는 기능 구현")
    void allGoodsListTest() {
        // given
        ArrayList<Goods> goodsList = new ArrayList<>();

        // when
        when(goodsMapper.findAll()).thenReturn(goodsList);
        List<Goods> list = goodsMapper.findAll();

        // then
        assertThat(goodsService.allGoodsList().isEmpty(), is(true));
    }

    @Test
    @DisplayName("상품 정보를 조회하는 기능 구현")
    void getGoodsInfoTest() {
        // given
        Goods goods = generateTestGoods();
        String requestCode = TEST_GOODS_CODE;

        // when
        when(goodsMapper.findByCode(requestCode)).thenReturn(Optional.of(goods));
        goodsMapper.findByCode(requestCode)
                .orElseThrow(() -> new RuntimeException("대상 상품을 조회할 수 없습니다."));

        // then
        assertThat(goodsService.getGoodsInfo(requestCode), is(goods));
    }

    @Test
    @DisplayName("상품 정보를 수정하는 기능 구현")
    void updateGoodsInfoTest() {
        // given
        String requestCode = TEST_GOODS_CODE;
        GoodsRequest goodsRequest = new GoodsRequest("testGoods", 1500, GoodsCategories.JUICE, "S/S 시즌 인기상품", true);
        Goods goods = goodsRequest.generateGoods();

        // when
        when(goodsMapper.findByCode(requestCode)).thenReturn(Optional.of(goods));
        Goods target = goodsMapper.findByCode(requestCode)
                .orElseThrow(() -> new RuntimeException("대상 상품을 조회할 수 없습니다."));

        Goods modifiedGoods = new Goods.Builder(target)
                .goodsPrice(1500)
                .goodsDesc("S/S 시즌 인기상품")
                .build();
        when(goodsMapper.update(modifiedGoods)).thenReturn(1);

        int result = goodsMapper.update(modifiedGoods);

        if (result != 1) {
            throw new RuntimeException("수정 처리에 실패하였습니다.");
        }

        // then
        assertThat(goodsService.updateGoodsInfo(requestCode, goodsRequest), is(true));
    }

    private Goods generateTestGoods() {
        return new Goods.Builder()
                .goodsName("testGoods")
                .goodsStatus(true)
                .goodsDesc("testDesc")
                .goodsCat(GoodsCategories.BAKERY)
                .goodsPrice(1000)
                .build();
    }

}