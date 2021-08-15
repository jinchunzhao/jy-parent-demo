package com.jy.server.goods.controller;

import com.jy.common.pojo.goods.Sku;
import com.jy.common.web.ResultBean;
import com.jy.server.goods.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Sku控制管理器controller
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:26
 */
@RestController
@Slf4j
@RequestMapping("/sku")
@Api(value = "Sku控制管理器", tags = {"Sku控制管理器相关接口"})
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 查询全部的SKU数据
     *
     * @return
     *        结果数据
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value = "查询全部的SKU数据", notes = "查询全部的SKU数据")
    public ResultBean<List<Sku>> querySkuList(){
        List<Sku> skuList = skuService.querySkuList();
        return ResultBean.success(skuList) ;
    }

    /**
     * 根据ID查询SKU数据
     * @param id
     *        skuId
     * @return
     *        数据
     */
    @ResponseBody
    @GetMapping("/findById/{id}")
    @ApiOperation(value = "根据ID查询SKU数据", notes = "根据ID查询SKU数据")
    public ResultBean<Sku> findById(@PathVariable("id") Long id){
        Sku sku = skuService.findById(id);
        return ResultBean.success(sku);
    }


    /**
     * 添加SKU数据
     *
     * @param sku
     *        参数
     * @return
     *        结果
     */
    @ResponseBody
    @PostMapping
    @ApiOperation(value = "添加SKU数据", notes = "添加SKU数据")
    public ResultBean add(@RequestBody Sku sku){
        return skuService.add(sku);
    }


}
