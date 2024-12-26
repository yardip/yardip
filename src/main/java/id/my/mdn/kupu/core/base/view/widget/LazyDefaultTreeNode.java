/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author aphasan
 * @param <E>
 */
public class LazyDefaultTreeNode<E extends HierarchicalEntity<E>> extends DefaultTreeNode<E> {

    private Function<TreeNode<E>, List<TreeNode<E>>> childrenLoader;

    private final Function<TreeNode<E>, Long> childrenCounter;

    public LazyDefaultTreeNode(E data, TreeNode<E> parent,
            Function<TreeNode<E>, List<TreeNode<E>>> childrenLoader,
            Function<TreeNode<E>, Long> childrenCounter) {

        super(data, parent);
        this.childrenLoader = childrenLoader;
        this.childrenCounter = childrenCounter;
    }

    @Override
    public boolean isLeaf() {
        return childrenCounter.apply(this) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final LazyDefaultTreeNode<E> other = (LazyDefaultTreeNode<E>) obj;

        return Objects.equals(this.getData(), other.getData());
    }
}
