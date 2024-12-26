package id.my.mdn.kupu.core.base.model;

import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 * @param <Element>
 */
public interface HierarchicalEntity <Element extends HierarchicalEntity> {
    public Element getParent();
    public void setParent(Element parent);
    public List<Element> getChildren();
    public void setChildren (List<Element> children);
}
