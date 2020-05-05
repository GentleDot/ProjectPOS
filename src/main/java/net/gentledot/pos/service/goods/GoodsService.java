package net.gentledot.pos.service.goods;

import net.gentledot.pos.model.goods.Goods;
import net.gentledot.pos.model.request.GoodsRequest;
import net.gentledot.pos.repository.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsService {
    private final GoodsMapper goodsMapper;

    public GoodsService(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Transactional
    public boolean addGoods(GoodsRequest request) {
        int result = goodsMapper.save(request.generateGoods());

        if (result != 1) {
            throw new RuntimeException("저장 처리에 실패하였습니다.");
        }

        return true;
    }

    public List<Goods> allGoodsList() {
        return goodsMapper.findAll();
    }

    public Goods getGoodsInfo(String goodsCode) {
        return goodsMapper.findByCode(goodsCode)
                .orElseThrow(() -> new RuntimeException("대상 상품을 조회할 수 없습니다."));
    }

    @Transactional
    public boolean updateGoodsInfo(String goodsCode, GoodsRequest request) {
        Goods targetGoods = goodsMapper.findByCode(goodsCode)
                .orElseThrow(() -> new RuntimeException("대상 상품을 조회할 수 없습니다."));

        Goods modifiedGoods = new Goods.Builder(targetGoods)
                .goodsPrice(request.getPrice())
                .goodsDesc(request.getDescription())
                .goodsStatus(request.getStatus())
                .goodsName(request.getName())
                .build();

        int result = goodsMapper.update(modifiedGoods);

        if (result != 1) {
            throw new RuntimeException("수정 처리에 실패하였습니다.");
        }

        return true;
    }
}
