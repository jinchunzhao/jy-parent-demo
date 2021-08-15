package com.jy.server.order.controller;

import com.jy.common.config.auth.TokenDecode;
import com.jy.common.web.ResultBean;
import com.jy.server.order.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 购物车控制管理
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 16:53
 */
@RestController
@Slf4j
@RequestMapping("cart")
@Api(value = "购物车控制管理", tags = {"购物车控制管理相关接口"})
public class CartController {

    @Resource
    private TokenDecode tokenDecode;

    @Autowired
    private CartService cartService;

    /**
     * 加入购物车
     *
     * @param skuId
     *        商品id
     * @param num
     *        数量
     * @return
     *        结果信息
     */
    @ResponseBody
    @GetMapping("/addCart")
    @ApiOperation(value = "根据用户名获取用户信息", notes = "根据用户名获取用户信息")
    public ResultBean addCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        String username = tokenDecode.getUserInfo().get("username");
        cartService.addCart(skuId, num,username);
        return ResultBean.success();
    }

    /**
     * 查询当前用户的购物车列表
     *
     * @return
     *        结果信息
     */
    @ResponseBody
    @ApiOperation(value = "当前用户的购物车列表", notes = "当前用户的购物车列表")
    @GetMapping("/list")
    public ResultBean<Map<String, Object>> list(){
        String username = tokenDecode.getUserInfo().get("username");
        return ResultBean.success(cartService.cartList(username));
    }
}
