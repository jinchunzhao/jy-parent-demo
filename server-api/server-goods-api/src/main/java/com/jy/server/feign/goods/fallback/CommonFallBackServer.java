package com.jy.server.feign.goods.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jy.common.web.ResultBean;
import com.jy.common.web.ResultBeanCode;

/**
 * feign通用降级方法
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 13:25
 */
public abstract class CommonFallBackServer {

    /**
     * 通用降级方法
     * 
     * @return ResponseBean
     */
    public String defaultCommonFallBack() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new ResultBean<>(ResultBeanCode.ERROR));
    }

    /**
     * 通用降级方法
     *
     * @param code
     *            编码
     * @param data
     *            数据
     * @return ResponseBean
     */
    public String defaultCommonFallBack(ResultBeanCode code, Object data) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new ResultBean(code, data));
    }

    /**
     * 返回一个指定类型的空对象
     * 
     * @param cls
     *            类型
     * @param <T>
     *            泛型
     * @return 对象
     */
    public <T> T defaultCommonFallBack(Class<T> cls) {
        return null;
    }

    /**
     * 返回一个指定类型对象
     * 
     * @param data
     *            具体对象
     * @param <T>
     *            泛型
     * @return 对象
     */
    public <T> T defaultCommonFallBack(T data) {
        return data;
    }
}
