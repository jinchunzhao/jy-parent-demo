package com.jy.server.feign.goods.fallback;


import com.jy.common.pojo.goods.Sku;
import com.jy.common.web.ResultBean;
import com.jy.common.web.ResultBeanCode;
import com.jy.server.feign.goods.SkuFeign;
import org.springframework.stereotype.Component;

/**
 * sku feign远程调用服务降级
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 13:27
 */
@Component
public class SkuFeignFallBack extends CommonFallBackServer implements SkuFeign {


    @Override
    public ResultBean<Sku> findById(Long id) {
        return new ResultBean<Sku>(ResultBeanCode.ERROR.getCode(),"服务异常进入降级处理");
    }
}
