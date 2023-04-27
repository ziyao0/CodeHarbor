package com.cfx.web.mvc;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Eason
 * @since 2023/4/27
 */
public class BaseController<M extends IService<T>, T> {

    @Autowired
    protected M iService;


}
