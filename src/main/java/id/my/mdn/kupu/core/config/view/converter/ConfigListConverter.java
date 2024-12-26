
package id.my.mdn.kupu.core.config.view.converter;

import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import id.my.mdn.kupu.core.config.model.Config;
import id.my.mdn.kupu.core.config.service.ConfigFacade;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named("ConfigListConverter")
@FacesConverter(value = "ConfigListConverter", managed = true)
public class ConfigListConverter extends SelectionsConverter<Config> {

    @Inject
    private ConfigFacade service;

    @Override
    protected Config getAsObject(String value) {
        return service.find(KLong.valueOf(value));
    }

    @Override
    protected String getAsString(Config obj) {
        return obj.toString();
    }

}
