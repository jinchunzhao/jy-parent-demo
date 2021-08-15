package com.jy.server.order.service.impl;

import com.jy.common.exception.CastException;
import com.jy.common.pojo.goods.Sku;
import com.jy.common.pojo.order.OrderItem;
import com.jy.common.web.ResultBean;
import com.jy.common.web.ResultBeanCode;
import com.jy.server.feign.goods.SkuFeign;
import com.jy.server.order.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 购物车实现
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 17:26
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    /**
     * 购物车每个用户的购物信息key
     */
    private static final String CART="cart_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void addCart(Long skuId, Integer num, String username) {

        //1.查询redis中相对应的商品信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(CART+username).get(skuId);
        if (orderItem != null){
            //2.如果当前商品在redis中的存在,则更新商品的数量与价钱
            orderItem.setNum(orderItem.getNum()+num);
            if (orderItem.getNum()<=0){
                //删除该商品
                redisTemplate.boundHashOps(CART+username).delete(skuId);
                return;
            }
            BigDecimal oldMoney = orderItem.getMoney();
            BigDecimal oldPayMoney = orderItem.getPayMoney();

            BigDecimal price = orderItem.getPrice();
            Integer num1 = orderItem.getNum();
            BigDecimal multiply = price.multiply(new BigDecimal(num1));
            BigDecimal addMoney = multiply.add(oldMoney);
            BigDecimal money = addMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
            orderItem.setMoney(money);


            BigDecimal payPrice = orderItem.getPrice();
            BigDecimal payMultiply = payPrice.multiply(new BigDecimal(num1));
            BigDecimal payMoney = payMultiply.add(oldPayMoney);
            payMoney = payMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
            orderItem.setPayMoney(payMoney);
        }else {
            //3.如果当前商品在redis中不存在,将商品添加到redis中
            Long categoryId = 1L;


            //封装orderItem
            orderItem = this.skuOrderItem(categoryId,1L,skuId,num);
        }

        //3.将orderItem添加到redis中
        redisTemplate.boundHashOps(CART+username).put(skuId,orderItem);
    }

    @Override
    public Map<String, Object> cartList(String username) {
        Map<String, Object> map = new HashMap();

        List<OrderItem> orderItemList = redisTemplate.boundHashOps(CART + username).values();
        map.put("orderItemList",orderItemList);

        //商品的总数量与总价格
        Integer totalNum = 0;
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            totalNum +=orderItem.getNum();
            totalMoney = totalMoney.add(orderItem.getMoney());
        }

        map.put("totalNum",totalNum);
        map.put("totalMoney",totalMoney);

        return map;
    }

    /**
     * 组装订单项实体
     *
     * @param categoryId
     *        分类id
     * @param spuId
     *        spuId
     * @param skuId
     *        skuId
     * @param num
     *        数量
     * @return
     *        结果信息
     */
    private OrderItem skuOrderItem(Long categoryId, Long spuId,Long skuId ,Integer num) {
        ResultBean<Sku> resultBean = skuFeign.findById(skuId);
        if (!Objects.equals(resultBean.getCode(), ResultBeanCode.SUCCESS.getCode())){
            CastException.cast(resultBean.getCode(),resultBean.getMsg());
        }
        Sku sku = resultBean.getData();

        OrderItem orderItem = new OrderItem();
        orderItem.setSpuId(spuId);
        orderItem.setSkuId(skuId);
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);

        BigDecimal price = orderItem.getPrice();
        Integer num1 = orderItem.getNum();
        BigDecimal moneyMultiply = price.multiply(new BigDecimal(num1));
        BigDecimal money = moneyMultiply.setScale(2, BigDecimal.ROUND_HALF_UP);
        orderItem.setMoney(money);

        BigDecimal payPrice = orderItem.getPrice();
        BigDecimal payMultiply = payPrice.multiply(new BigDecimal(num1));
        BigDecimal payMoney = payMultiply.setScale(2, BigDecimal.ROUND_HALF_UP);
        orderItem.setPayMoney(payMoney);

        orderItem.setImage("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%89%8B%E6%9C%BA%E5%9B%BE%E7%89%87%E5%8D%8E%E4%B8%BAp30&hs=0&pn=1&spn=0&di=5610&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=4241658706%2C2125171813&os=2887801636%2C442596826&simid=4184888633%2C700914540&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E6%89%8B%E6%9C%BA%E5%9B%BE%E7%89%87%E5%8D%8E%E4%B8%BAp30&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fimages2.looquan.com%2FUploads%2Flu%2F155537192428783.jpg%26refer%3Dhttp%3A%2F%2Fimages2.looquan.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1631526227%26t%3D39436eacaed5716dfd73753fda99ad04&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bs55q7wg_z%26e3Bv54AzdH3Fs7-etjo-t1-dld88n_z%26e3Bip4s&gsm=2&islist=&querylist=");

        BigDecimal weight = BigDecimal.valueOf(sku.getWeight());
        BigDecimal weightMultiply = weight.multiply(new BigDecimal(num1));
        weight = weightMultiply.setScale(2, BigDecimal.ROUND_HALF_UP);

        orderItem.setWeight(weight);
        //分类信息
        orderItem.setCategoryId(categoryId);
        return orderItem;
    }
}
