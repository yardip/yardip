
package id.my.mdn.kupu.core.config.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import id.my.mdn.kupu.core.base.view.annotation.OnReload;
import id.my.mdn.kupu.core.config.view.widget.ConfigList;
import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
@Named(value = "configPage")
@ViewScoped
public class ConfigPage extends ChildPage implements Serializable {
    
    @Inject
    @Bookmarked
    private ConfigList configList;
    
    @OnInit
    public void init() {
//        configList.init();
    }

    @OnReload
    public void reload() {
//        configList.reload();
    }

    public ConfigList getConfigList() {
        return configList;
    }
}
