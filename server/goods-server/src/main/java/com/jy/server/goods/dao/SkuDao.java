package com.jy.server.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jy.common.pojo.goods.Sku;
import org.springframework.stereotype.Repository;

/**
 * SKU控制管理dao
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:33
 */
@Repository
public interface SkuDao extends BaseMapper<Sku> {
}
