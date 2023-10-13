package com.ziyao.harbor.web;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * 用于解析树结构的公共类
 *
 * @author zhangziyao
 * @since 2023/4/21
 */
@Getter
public abstract class AbstractTreeNode implements Serializable {

    private static final long serialVersionUID = 8382484378443306257L;
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private List<AbstractTreeNode> nodes;
    private int level;
    private String url;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setNodes(List<AbstractTreeNode> nodes) {
        this.nodes = nodes;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
