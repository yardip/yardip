/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList.DefaultCount;
import static id.my.mdn.kupu.core.base.view.widget.Selector.CHECKBOX;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import id.my.mdn.kupu.core.base.view.widget.Selector.SelectionModeSelector;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.primefaces.model.TreeNode;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class AbstractValueTree<E extends HierarchicalEntity<E>>
        extends AbstractValueList<E> {

    private static enum NodeEvent {
        NONE,
        SELECT,
        UNSELECT,
        EXPAND,
        COLLAPSE
    }

    @FunctionalInterface
    public static interface TreeNodeConstructor<E> {

        TreeNode<E> construct(E data, TreeNode<E> parent);
    }

    protected TreeNode<E> rootNode = null;

    protected DefaultCount defaultCount = () -> null;

    private TreeNodeConstructor<E> constructor;

    private NodeEvent latestEvent = NodeEvent.NONE;

    private TreeNode<E> nodeSelection;

    private List<TreeNode<E>> nodeSelections;

    public AbstractValueTree() {
        super();
    }

    @Override
    public void setSelections(List<E> selections) {
        selector.setSelectionsInternal(selections);

        if (rootNode == null) {
            rootNode = createNode(getRoot(), null);
            constructRoot();
        }

        List<TreeNode<E>> selectedNodes = new ArrayList<>();
        findSelections(rootNode, selectedNodes);

        nodeSelections = !selectedNodes.isEmpty() ? selectedNodes : null;
    }

    public void setNodeSelections(List<TreeNode<E>> nodeSelections) {
        if (!latestEvent.equals(NodeEvent.EXPAND)) {
            setNodeSelectionsInternal(nodeSelections);
        }
        latestEvent = NodeEvent.NONE;

    }

    public List<TreeNode<E>> getNodeSelections() {
        return getNodeSelectionsInternal();
    }

    public void setNodeSelectionsInternal(List<TreeNode<E>> nodeSelections) {
        this.nodeSelections = nodeSelections;

        PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        if (phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {

            List<E> selections = null;

            if (nodeSelections != null) {
                selections = new ArrayList<>();
                for (TreeNode<E> node : nodeSelections) {
                    selections.add(node.getData());
                }
            }

            selector.setSelectionsInternal(selections);
        }
    }

    public List<TreeNode<E>> getNodeSelectionsInternal() {
        return nodeSelections;
    }

    @Override
    public void setSelection(E selection) {
        selector.setSelectionInternal(selection);

        if (rootNode == null) {
            rootNode = createNode(getRoot(), null);
            constructRoot();
        }

        TreeNode<E> selectedNode = null;
        findSelection(rootNode, selectedNode);

        nodeSelection = selectedNode;
    }

    public TreeNode<E> getNodeSelection() {
        return nodeSelection;
    }

    public void setNodeSelection(TreeNode<E> nodeSelection) {
        if (!latestEvent.equals(NodeEvent.EXPAND)) {
            setNodeSelectionInternal(nodeSelection);
        }
        latestEvent = NodeEvent.NONE;

    }

    public TreeNode<E> getNodeSelectionInternal() {
        return nodeSelection;
    }

    public void setNodeSelectionInternal(TreeNode<E> nodeSelection) {
        this.nodeSelection = nodeSelection;

        PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        if (phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {

            E selection = null;

            if (nodeSelection != null) {
                selection = nodeSelection.getData();
            }

            selector.setSelectionInternal(selection);
        }
    }

    private void findSelection(TreeNode<E> firstNode, TreeNode<E> selectedNode) {
        TreeNode<E> node = firstNode;
        if (getSelection() == null) {
            return;
        }
        if (getSelection().equals(node.getData())) {
            selectedNode = node;
            node.setSelected(true);
            node.setExpanded(true);
            TreeNode<E> parent = node.getParent();
            while (parent != null) {
                parent.setExpanded(true);
                parent = parent.getParent();
            }
        }

        List<TreeNode<E>> childNodes = firstNode.getChildren();
        if (childNodes != null) {
            for (TreeNode<E> childNode : childNodes) {
                findSelection(childNode, selectedNode);
            }
        }
    }

    private void findSelections(TreeNode<E> firstNode, List<TreeNode<E>> selectedNodes) {
        TreeNode<E> node = firstNode;
        if (getSelections() == null) {
            return;
        }
        if (getSelections().contains(node.getData())) {
            if (!selectedNodes.contains(node)) {
                selectedNodes.add(node);
                node.setSelected(true);
                node.setExpanded(true);
                TreeNode<E> parent = node.getParent();
                while (parent != null) {
                    parent.setExpanded(true);
                    parent = parent.getParent();
                }
            }
        }

        List<TreeNode<E>> childNodes = firstNode.getChildren();
        if (childNodes != null) {
            for (TreeNode<E> childNode : childNodes) {
                findSelections(childNode, selectedNodes);
            }
        }
    }

    protected abstract long getItemsCountInternal(
            Map<String, Object> parameters,
            List<FilterData> filters,
            DefaultCount defaultCount,
            AbstractFacade.DefaultChecker defaultChecker);

    public TreeNode<E> getRootNode() {
        if (rootNode == null) {
            rootNode = createNode(getRoot(), null);
            constructRoot();
        }
        return rootNode;
    }

    private void constructRoot() {
        if (!valid) {

            constructTree(rootNode);

            if (getSelectionMode().equals(SINGLE)) {
                TreeNode<E> selectedNode = null;
                findSelection(rootNode, selectedNode);
            } else {
                List<TreeNode<E>> selectedNodes = new ArrayList<>();
                findSelections(rootNode, selectedNodes);
            }

            validate();

        }
    }

    private void constructTree(TreeNode<E> topNode) {
        loadChildren(topNode);
    }

    private void loadChildren(TreeNode<E> parent) {

        List<E> childrenData = loadChildren(parent.getData());

        if (childrenData != null && !childrenData.isEmpty()) {

            for (E childData : childrenData) {

                TreeNode<E> childNode = createNode(childData, parent);

                parent.getChildren().add(childNode);

                loadChildren(childNode);

            }
        }
    }

    protected E getRoot() {
        return null;
    }

    protected TreeNode<E> createNode(E data, TreeNode<E> parent) {
        return constructor.construct(data, parent);
    }

    private List<E> loadChildren(E t) {
        List<FilterData> filters = new ArrayList<>();
        filters.addAll(filter.getValues());

        filters.add(FilterData.by("parent", t));

        return getFetchedItemsInternal(
                getParameters(),
                filters,
                getSorters(),
                defaultList,
                defaultChecker);
    }

    private List<TreeNode<E>> getChildren(TreeNode<E> parent) {
        List<FilterData> filters = filter.getValues();

        filters.add(new FilterData("parent", parent.getData()));

        List<E> children = getFetchedItemsInternal(
                getParameters(),
                filters,
                getSorters(),
                defaultList,
                defaultChecker);

        return children.stream()
                .map(e -> constructor.construct(parent.getData(), parent))
                .collect(Collectors.toList());

    }

    public Long countChildren(TreeNode<E> t) {
        List<FilterData> filters = filter.getValues();
        filters.add(new FilterData("parent", t.getData()));
        return getItemsCountInternal(getParameters(), filters, defaultCount, defaultChecker);
    }

    @Override
    protected void resetInternal() {       
        if(getSelectionMode().equals(SINGLE)) {
            nodeSelection = null;
        } else {
            nodeSelections = null;
        }
        super.resetInternal();
        rootNode = null;
    }

    @Override
    public void setSelectionMode(SelectionModeSelector mode) {
        selector.setSelectionMode(mode);
        if (getSelectionMode().equals(CHECKBOX)) {
            constructor = (data, parent) -> new LazyCheckboxTreeNode<>(data, parent,
                    this::getChildren,
                    this::countChildren);
        } else {
            constructor = (data, parent) -> new LazyDefaultTreeNode<E>(data, parent,
                    this::getChildren,
                    this::countChildren);
        }
    }

    public void setSelectionsLabel(String selectionsLabel) {
        getSelector().setSelectionsLabel(selectionsLabel);
    }

    public void setNodeConstructor(TreeNodeConstructor<E> constructor) {
        this.constructor = constructor;
    }
}
