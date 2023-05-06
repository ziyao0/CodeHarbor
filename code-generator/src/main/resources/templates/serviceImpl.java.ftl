package ${package.ServiceImpl};
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${cfg.dto}.${entity}DTO;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Resource
    private ${table.mapperName} ${table.mapperName?uncap_first};

    @Override
    public Page<${entity}> page(Page<${entity}> page, ${entity}DTO ${entity?uncap_first}DTO) {
        LambdaQueryWrapper<${entity}> wrapper = initWrapper(${entity?uncap_first}DTO);
        return ${table.mapperName?uncap_first}.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param ${entity?uncap_first}DTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<${entity}> initWrapper(${entity}DTO ${entity?uncap_first}DTO) {

        LambdaQueryWrapper<${entity}> wrapper = Wrappers.lambdaQuery(${entity}.class);
        <#list table.fields as field>
        <#if field.propertyType == "boolean">
        <#assign getprefix="is"/>
        <#else>
        <#assign getprefix="get"/>
        </#if>
        // ${field.comment}
        wrapper.eq(!StringUtils.isEmpty(${entity?uncap_first}DTO.${getprefix}${field.capitalName}()), ${entity}::${getprefix}${field.capitalName}, ${entity?uncap_first}DTO.${getprefix}${field.capitalName}());
        </#list>
        return wrapper;
    }
}
</#if>
