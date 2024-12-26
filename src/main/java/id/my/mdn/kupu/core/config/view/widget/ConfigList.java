
package id.my.mdn.kupu.core.config.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList;
import id.my.mdn.kupu.core.base.view.widget.EditingFeature;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IEditable;
import id.my.mdn.kupu.core.config.model.Config;
import id.my.mdn.kupu.core.config.service.ConfigFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.configuration2.Configuration;


/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
@Named(value = "configList")
@Dependent
public class ConfigList extends AbstractPagedValueList<Config>
        implements IEditable<Config>, Serializable {

    @Inject    
    private EditingFeature<Config> editingFeature;
    
    @Inject
    private ConfigFilter filterContent;     

    @Inject
    private ConfigFacade dao;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<Config> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<Config> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public Config getEditedValue() {
        return editingFeature.getEditedValue();
    }

    @Override
    public void setEditedValue(Config editedValue) {
        editingFeature.setEditedValue(editedValue);
    }

    @Override
    public void toggleEdit(Config value) {
        editingFeature.toggleEdit(value);
    }

    @Override
    public boolean isEditing(Config value) {
        return editingFeature.isEditing(value);
    }

    @Override
    public void save(Config editedValue) {
        dao.edit(editedValue);
    }
    
    @Inject
    private Configuration config;

    @Override
    public void saveEdit() {
        Config cfg = getEditedValue();
        IEditable.super.saveEdit();
        config.clearProperty(cfg.getModule() + "." + cfg.getKey());
    }

}
