package com.cfx.common.api;

import java.io.Serializable;
import java.util.List;

/**
 * 用于解析树结构的公共类
 *
 * @author Eason
 * @date 2023/4/21
 */
public abstract class AbstractTreeNode implements Serializable {

    private static final long serialVersionUID = -5395676306973486839L;
    private String id;
    private String name;
    private String parentId;
    private List<AbstractTreeNode> nodes;
    private int level;

    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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

