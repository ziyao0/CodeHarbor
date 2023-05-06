package com.cfx.web.orm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author Eason
 * @since 2023/5/6
 */
public class QueryWrapperUtils {


    public static <T> void initWrapper(Object bean, QueryWrapper<T> queryWrapper) {


        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(bean, Map.class);


        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String key = entry.getKey();

            Object value = entry.getValue();


            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);


            // value被
            if (StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            // 包含name字段的进行模糊匹配
            if (key.contains("name")) {
                queryWrapper.likeRight(column, entry.getValue());

            } else if (Objects.equals(key, "order")) {
                // key为order则为排序字段
                String[] val = value.toString().split(",");
                if (val[0].equalsIgnoreCase("ASC")) {
                    // 正序
                    String replace = value.toString().replace("ASC", "");
                    queryWrapper.orderByDesc(replace.split(","));
                } else if (val[0].equalsIgnoreCase("DESC")) {
                    // 倒序
                    String replace = value.toString().replace("DESC", "");
                    queryWrapper.orderByDesc(replace.split(","));
                } else {
                    // 默认倒序
                    queryWrapper.orderByDesc(val);
                }
            } else {
                // 默认是全等于
                queryWrapper.eq(column, value);
            }

        }
    }


    public static void main(String[] args) {

//        String resultStr = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "userName");
//        System.out.println("转换后的结果：" + resultStr);
//
//


    }
}
