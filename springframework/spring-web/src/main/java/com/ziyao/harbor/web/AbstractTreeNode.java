package com.ziyao.harbor.web;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用于解析树结构的公共类
 *
 * @author zhangziyao
 * @since 2023/4/21
 */
@Setter
@Getter
public abstract class AbstractTreeNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 8382484378443306257L;
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private List<AbstractTreeNode> nodes;
    private int level;
    private String url;


}
