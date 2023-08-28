package com.ziyao.harbor.web.base;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/4/27
 */
public class BaseController<M extends IService<T>, T> {

    @Autowired
    protected M iService;

    /**
     * 获取所有数据
     * <p>不分页</p>
     */
    @PostMapping("/list")
    public List<T> list() {
        return iService.list();
    }

    /**
     * 通过id删除数据，有逻辑删除按照逻辑删除执行
     * <p>不支持联合主键</p>
     *
     * @param id 主键Id
     */
    @GetMapping("/remove/{id}")
    public void removeById(@PathVariable("id") Long id) {
        iService.removeById(id);
    }

}
