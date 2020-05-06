package net.gentledot.pos.mapper;

import net.gentledot.pos.model.goods.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GoodsMapper {

    @Select("select now()")
    String getTime();

    @SelectKey(resultType = String.class, keyProperty = "goodsCode", before = true,
            statement = "select concat(year(now()), #{goodsCat}, ifnull(max(goods_no), 0) + 1) from goods where goods_category = #{goodsCat}")
    @Insert("insert into goods (goods_code, goods_name, goods_price, goods_category, goods_description)" +
            "values (#{goodsCode}, #{goodsName}, #{goodsPrice}, #{goodsCat}, '${goodsDesc.get}')")
    int save(Goods goods);

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale, created_at " +
            "from goods ")
    List<Goods> findAll();

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale, created_at " +
            "from goods " +
            "where status_for_sale = 1 " +
            "and goods_no = #{id}")
    Optional<Goods> findById(long id);

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale, created_at " +
            "from goods " +
            "where status_for_sale = 1 " +
            "and goods_code = #{code}")
    Optional<Goods> findByCode(String code);

    @Update("update goods\n" +
            "set\n" +
            "    goods_name = #{goodsName},\n" +
            "    goods_price = #{goodsPrice},\n" +
            "    goods_description = '${goodsDesc.get}',\n" +
            "    status_for_sale = #{status}\n" +
            "where goods_code = #{goodsCode}")
    int update(Goods goods);
}
