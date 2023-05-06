package ${cfg.dto};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import ${package.Entity}.${entity};
import lombok.Data;
import org.springframework.util.StringUtils;

<#--<#list table.importPackages as pkg>-->
<#--import ${pkg};-->
<#--</#list>-->
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
</#if>
public class ${entity}DTO implements EntityDTO<${entity}>, Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<${entity}> initWrapper() {

        return Wrappers.lambdaQuery(${entity}.class)
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
        <#if !field.fill??>
            <#if !field.keyFlag>
                // ${field.comment}
                <#if field.propertyType == "String">
                .likeRight(!StringUtils.isEmpty(${field.propertyName}), ${entity}::${getprefix}${field.capitalName}, ${field.propertyName})
                <#else>
                .eq(!StringUtils.isEmpty(${field.propertyName}), ${entity}::${getprefix}${field.capitalName}, ${field.propertyName})
                </#if>
            </#if>
        </#if>
    </#list>
        <#list table.fields as field>
            <#if field.propertyName=="sort" || field.propertyName="sorted">
                // ${field.comment}
                .orderByAsc(${entity}::${getprefix}${field.capitalName})
            </#if>
        </#list>
                ;
    }

    @Override
    public ${entity} getEntity() {
        return new ${entity}();
    }
}
