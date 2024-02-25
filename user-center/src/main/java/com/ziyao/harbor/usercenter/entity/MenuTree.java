package com.ziyao.harbor.usercenter.entity;

import com.ziyao.harbor.web.AbstractTreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class MenuTree extends AbstractTreeNode {
    public MenuTree() {
    }

    public MenuTree(String name) {
        setName(name);
    }
}
