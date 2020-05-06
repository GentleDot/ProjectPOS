package net.gentledot.pos.repository;

import net.gentledot.pos.model.goods.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GoodsMapper {
    /*@Results(id = "goodsResult",
    value = {
            @Result(column = "goods_no"         , property = "goodsNo", id = true),
            @Result(column = "goods_code"       , property = "goodsCode"),
            @Result(column = "goods_name"       , property = "goodsName"),
            @Result(column = "goods_price"      , property = "goodsPrice"),
            @Result(column = "goods_category"   , property = "goodsCat"),
            @Result(column = "goods_description", property = "goodsDesc"),
            @Result(column = "status_for_sale"  , property = "status")

    })*/

    @Select("select now()")
    String getTime();

    @SelectKey(resultType = String.class, keyProperty = "goodsCode", before = true,
            statement = "select concat(year(now()), #{goodsCat}, ifnull(max(goods_no), 0) + 1) from goods where goods_category = #{goodsCat}")
    @Insert("insert into goods (goods_code, goods_name, goods_price, goods_category, goods_description)" +
            "values (#{goodsCode}, #{goodsName}, #{goodsPrice}, #{goodsCat}, '${goodsDesc.get}')")
    int save(Goods goods);

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale " +
            "from goods ")
    List<Goods> findAll();

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale " +
            "from goods " +
            "where status_for_sale = 1 " +
            "and goods_no = #{id}")
    Optional<Goods> findById(long id);

    @Select("select goods_no, goods_code, goods_name, goods_price, goods_category, goods_description, status_for_sale " +
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
