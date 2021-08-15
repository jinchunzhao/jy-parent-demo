package com.jy.server.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jy.common.pojo.goods.Sku;
import com.jy.common.web.ResultBean;
import com.jy.server.goods.dao.SkuDao;
import com.jy.server.goods.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SKU控制管理实现类
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:34
 */
@Service
@Slf4j
public class SkuServiceImpl extends ServiceImpl<SkuDao, Sku> implements SkuService {
    @Override
    public List<Sku> querySkuList() {
        return this.list();
    }

    @Override
    public Sku findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ResultBean add(Sku sku) {
        boolean save = this.save(sku);
        if (save){
            return ResultBean.success();
        }else {
            return ResultBean.failed();
        }

    }
}
