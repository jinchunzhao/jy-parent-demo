package com.jy.server.order.service;

import java.util.Map;

/**
 * 购物车
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 17:25
 */
public interface CartService {

    /**
     * 添加购物车
     *
     * @param skuId
     *        skuId
     * @param num
     *        数量
     * @param username
     *        当前用户名
     */
    void addCart(Long skuId,Integer num,String username);



    /**
     * 查询当前用户的购物车列表
     *
     * @param username
     *        用户名
     * @return
     *        结果信息
     */
    Map<String, Object> cartList(String username);
}
