package com.ziyao.cfx.common.api;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用于解析树结构的公共类
 *
 * @author zhangziyao
 * @since 2023/4/21
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<AbstractTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<AbstractTreeNode> nodes) {
        this.nodes = nodes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
