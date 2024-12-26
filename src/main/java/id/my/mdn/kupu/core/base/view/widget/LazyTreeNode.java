/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import org.primefaces.model.TreeNode;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public interface LazyTreeNode<E> extends TreeNode<E> {
    @Override
    public boolean isLeaf();
}
