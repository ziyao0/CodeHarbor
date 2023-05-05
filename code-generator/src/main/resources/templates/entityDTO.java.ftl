package ${cfg.dto};

import com.cfx.common.dto.EntityDTO;
import ${package.Entity}.${entity};
import lombok.Data;

<#--<#list table.importPackages as pkg>-->
<#--import ${pkg};-->
<#--</#list>-->
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *${table.comment!}
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

    @Override
    public ${entity} getEntity() {
        return new ${entity}();
    }
}
