package net.gentledot.pos.controller.goods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.goods.GoodsCategories;
import net.gentledot.pos.model.request.GoodsRequest;
import net.gentledot.pos.service.goods.GoodsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(GoodsController.class)
class GoodsControllerTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoodsService goodsService;

    @Test
    @DisplayName("상품 등록 요청")
    void addGoodsTest() throws Exception {
        // given
        GoodsRequest request = new GoodsRequest("testgoods", 1000, GoodsCategories.JUICE, "testDesc", true);
        String jsonRequest = getJSONRequest(request);
        log.debug("생성된 JSON request : {}", jsonRequest);

        given(goodsService.addGoods(any(GoodsRequest.class))).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/goods/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }


    @Test
    @DisplayName("상품 목록 조회 요청")
    void getGoodsListTest() throws Exception {
        // given
        ArrayList<Goods> goodsList = new ArrayList<>();
        goodsList.add(generateTestGoods());
        given(goodsService.allGoodsList()).willReturn(goodsList);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/goods/listAll"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].goodsNo").exists())
                .andExpect(jsonPath("$[0].goodsCode").hasJsonPath())
                .andExpect(jsonPath("$[0].goodsName").value("testGoods"))
                .andExpect(jsonPath("$[0].goodsPrice").value(1000))
                .andExpect(jsonPath("$[0].goodsCat").value("bake"))
                .andExpect(jsonPath("$[0].goodsDesc").value("testDesc"))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].forSaleGoods").value(true));
    }

    @Test
    @DisplayName("상품 정보 조회 요청")
    void getGoodsInfo() throws Exception {
        // given
        String goodsCode = "existedCode";
        Goods goods = generateTestGoods();
        given(goodsService.getGoodsInfo(goodsCode)).willReturn(goods);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/goods/info/existedCode"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodsNo").exists())
                .andExpect(jsonPath("$.goodsCode").hasJsonPath())
                .andExpect(jsonPath("$.goodsName").value("testGoods"))
                .andExpect(jsonPath("$.goodsPrice").value(1000))
                .andExpect(jsonPath("$.goodsCat").value("bake"))
                .andExpect(jsonPath("$.goodsDesc").value("testDesc"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.forSaleGoods").value(true));
    }

    @Test
    @DisplayName("상품 정보 수정 요청")
    void updateGoodsInfo() throws Exception {
        // given
        String goodsCode = "existedCode";
        GoodsRequest request = new GoodsRequest("changedName", 2500, GoodsCategories.JUICE, "50% off", true);
        String jsonRequest = getJSONRequest(request);
        log.debug("생성된 JSON request : {}", jsonRequest);

        given(goodsService.updateGoodsInfo(eq(goodsCode), any(GoodsRequest.class))).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/goods/modify/existedCode")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("대상 상품을 비매품으로 설정하도록 요청")
    void setNotForSaleTest() throws Exception {
        // given
        String goodsCode = "existedCode";
        Goods goods = generateTestGoods();

        given(goodsService.setNotForSale(goodsCode)).willReturn(true);

        // when
        ResultActions actions = mockMvc. perform(patch("/api/v1/goods/unuse/existedCode"));

        Goods modifiedGoods = new Goods.Builder(goods)
                .goodsStatus(false)
                .build();

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

    }

    private String getJSONRequest(GoodsRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(request);
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