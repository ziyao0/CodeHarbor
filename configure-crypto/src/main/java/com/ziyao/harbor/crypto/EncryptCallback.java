package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.Ordered;

/**
 * 加密回调
 * <p>
 * 在文本需要加密时回调用该方法，
 * 实现自定义加密算法时需要实现该接口并通过{@code SPI}的方式加载实现的类
 * 如果出现多个实现该接口的类时，则已{@link Ordered#getOrder()}的值做
 * 为有优先级来划分，取优先级最高的为实际加密的方案。
 *
 * @author ziyao
 * @since 2023/4/23
 */
public interface EncryptCallback extends Ordered {

    /**
     * 加密传入的对象属性
     *
     * @param text 代加密文本
     * @return 返回加密后的密文信息
     * @throws Exception 加密异常
     */
    String encrypt(String text) throws Exception;

}
