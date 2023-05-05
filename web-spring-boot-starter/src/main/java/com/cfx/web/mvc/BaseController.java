package com.cfx.web.mvc;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Eason
 * @since 2023/4/27
 */
public class BaseController<M extends IService<T>, T> {

    @Autowired
    protected M iService;


    @PostMapping("/list")
    public List<T> list() {
        return iService.list();
    }
}
