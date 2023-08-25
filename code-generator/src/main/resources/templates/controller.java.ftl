package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.harbor.common.exception.ServiceException;
import com.ziyao.harbor.web.response.Errors;
import ${dto}.${entity}DTO;
<#if superControllerClassPackage??>
    import ${package.Entity}.${entity};
    import ${package.Service}.${table.serviceName};
    import ${superControllerClassPackage};
</#if>
import com.ziyao.harbor.web.orm.PageQuery;
import com.ziyao.harbor.web.orm.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#--<#if superControllerClass??>-->
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceName}, ${entity}> {
<#--<#else>-->
<#--public class ${table.controllerName} {-->
<#--</#if>-->

@Autowired
private ${table.serviceName} ${table.serviceName?uncap_first};

    @PostMapping("/save")
    public void save(@RequestBody ${entity}DTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody ${entity}DTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody ${entity}DTO entityDTO) {
if (ObjectUtils.isEmpty(entityDTO.getId())) {
throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
}
super.iService.updateById(entityDTO.getInstance());
}

/**
* 默认一次插入500条
*/
@PostMapping("/saveBatch")
public void saveBatch(@RequestBody List
<${entity}DTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(${entity}DTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<${entity}> getPage(@RequestBody PageQuery<${entity}DTO> pageQuery) {
        Page<${entity}> page = PageUtils.initPage(pageQuery, ${entity}.class);
        return ${table.serviceName?uncap_first}.page(page, pageQuery.getQuery());
    }
}
</#if>
