package com.jy.server.feign.goods;

import com.jy.common.pojo.goods.Sku;
import com.jy.common.web.ResultBean;
import com.jy.server.feign.goods.fallback.SkuFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * user feign远程调用
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 13:22
 */
@FeignClient(name = "goods",fallback = SkuFeignFallBack.class)
public interface SkuFeign {

    /**
     * 根据ID查询SKU数据
     * @param id
     *        skuId
     * @return
     *        数据
     */
    @GetMapping("/findById/{id}")
    ResultBean<Sku> findById(@PathVariable("id") Long id);
}
