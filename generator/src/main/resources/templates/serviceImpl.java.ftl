package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${superServiceImplClassPackage};
import ${dto}.${entity}DTO;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
* <p>
    * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@RequiredArgsConstructor
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    private final ${table.mapperName} ${table.mapperName?uncap_first};

    @Override
    public Page<${entity}> page(Page<${entity}> page, ${entity}DTO ${entity?uncap_first}DTO) {
        LambdaQueryWrapper<${entity}> wrapper = ${entity?uncap_first}DTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return ${table.mapperName?uncap_first}.selectPage(page, wrapper);
    }
}
</#if>
