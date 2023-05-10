package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${dto}.${entity}DTO;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
* <p>
 * ${table.comment!} 服务类
 * </p>
*
* @author ${author}
* @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 分页查询
     */
    Page<${entity}> page(Page<${entity}> page, ${entity}DTO ${entity?uncap_first}DTO);
}
</#if>
