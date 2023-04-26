package com.cfx.usercenter.service.impl;

import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.mapper.AppMapper;
import com.cfx.usercenter.service.AppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用系统 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-04-26
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

}
