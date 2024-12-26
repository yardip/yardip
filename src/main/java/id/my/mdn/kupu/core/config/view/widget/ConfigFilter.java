
package id.my.mdn.kupu.core.config.view.widget;

import id.my.mdn.kupu.core.base.view.annotation.Default;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "configFilter")
@Dependent
public class ConfigFilter extends FilterContent implements Serializable {

    @Default("application")
    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
