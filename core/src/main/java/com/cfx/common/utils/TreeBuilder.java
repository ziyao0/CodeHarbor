package com.cfx.common.utils;

import com.cfx.common.api.AbstractTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public abstract class TreeBuilder {

    private TreeBuilder() {
    }

    /**
     * 将节点列表转换为树形结构
     *
     * @param nodeList 节点列表
     * @param <T>      节点类型
     * @param id       父节点id
     * @return 根节点列表
     */
    public static <T extends AbstractTreeNode> List<T> buildTree(List<T> nodeList, String id) {
        // 用哈希表存储节点信息，以便快速查找某个节点的父节点

        Map<Long, T> nodeMap = nodeList.stream().collect(Collectors.toMap(AbstractTreeNode::getId, Function.identity()));

        // 遍历节点列表，将每个节点加入其父节点的children列表中
        List<T> rootNodes = new ArrayList<>();
        for (T node : nodeList) {
            String parentId = node.getParentId();
            // 判断是否为父节点
            if (Objects.equals(parentId, id)) {
                node.setLevel(0);
                rootNodes.add(node);
            } else {
                // 将该节点加入其父节点的children列表中
                T parentNode = nodeMap.get(parentId);
                if (parentNode != null) {
                    if (parentNode.getNodes() == null) {
                        parentNode.setNodes(new ArrayList<>());
                    }
                    node.setLevel(parentNode.getLevel() + 1);
                    parentNode.getNodes().add(node);
                }
            }
        }
        return rootNodes;
    }
}
