package net.gentledot.pos.controller.goods;

import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.request.GoodsRequest;
import net.gentledot.pos.service.goods.GoodsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;


@RestController
@RequestMapping("${net.gentledot.application.base-uri}/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping(value = "add")
    public boolean addGoods(@RequestBody GoodsRequest request) {
        return goodsService.addGoods(request);
    }

    @GetMapping("listAll")
    public List<Goods> getGoodsList() {
        return goodsService.allGoodsList();
    }

    @GetMapping("info/{code}")
    public Goods getGoodsInfo(@PathVariable("code") String goodsCode) {
        return goodsService.getGoodsInfo(goodsCode);
    }

    @PutMapping("modify/{code}")
    public boolean updateGoodsInfo(@PathVariable("code") String goodsCode, @RequestBody GoodsRequest request) {
        return goodsService.updateGoodsInfo(goodsCode, request);
    }

    @PatchMapping("unuse/{code}")
    public boolean setNotForSale(@PathVariable("code") String goodsCode) {
        return goodsService.setNotForSale(goodsCode);
    }

}
