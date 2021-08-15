package com.jy.server.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jy.common.pojo.goods.Sku;
import com.jy.common.web.ResultBean;

import java.util.List;

/**
 * SKU控制管理service
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:31
 */
public interface SkuService extends IService<Sku> {

    /**
     * 查询全部的SKU数据
     *
     * @return
     *        结果数据
     */
    List<Sku> querySkuList();

    /**
     * 根据ID查询SKU数据
     * @param id
     *        skuId
     * @return
     *        数据
     */
    Sku findById(Long id);

    /**
     * 添加SKU数据
     *
     * @param sku
     *        参数
     * @return
     *        结果
     */
    ResultBean add(Sku sku);
}
