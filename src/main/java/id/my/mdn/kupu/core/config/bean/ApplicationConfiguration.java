package id.my.mdn.kupu.core.config.bean;

import id.my.mdn.kupu.core.config.model.AbstractConfigurationWrapper;
import id.my.mdn.kupu.core.config.service.ConfigFacade;
import java.io.Serializable;
import java.util.Iterator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.configuration2.Configuration;

/**
 *
 * @author aphasan
 */
@ApplicationScoped
public class ApplicationConfiguration implements Serializable {

    @Inject
    private ConfigFacade configFacade;

    private Configuration configuration;

//    @Inject
//    private CacheManager cacheManager;

//    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
//        jakarta.cache.configuration.Configuration<String, String> config
//                = new MutableConfiguration<String, String>()
//                        .setReadThrough(true)
//                        .setCacheLoaderFactory(
//                                new FactoryBuilder.SingletonFactory<>(
//                                        new CacheLoader<String, String>() {
//
//                                    @Override
//                                    public String load(String key) throws CacheLoaderException {
//                                        return configFacade.getValue(key);
//                                    }
//
//                                    @Override
//                                    public Map<String, String> loadAll(Iterable<? extends String> keys)
//                                            throws CacheLoaderException {
//                                        return configFacade.getConfigs(keys);
//                                    }
//                                }
//                                ));
//
//        cache = cacheManager.getCache("ApplicationConfiguration");
//
//        if (cache == null) {
//            cache = cacheManager.createCache("ApplicationConfiguration", config);
//        }

    }

//    @PreDestroy
//    private void destroy() {
//        cacheManager.destroyCache("ApplicationConfiguration");
//    }

    @Produces
    @Default
    @Named(value = "config")
    public Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new AbstractConfigurationWrapper() {

                @Override
                protected void addPropertyDirect(String key, Object value) {
//                    cache.put(key, value.toString());
                }

                @Override
                protected void clearPropertyDirect(String key) {
//                    cache.remove(key);
                }

                @Override
                protected Iterator<String> getKeysInternal() {
                    return configFacade.getKeys();
                }

                @Override
                protected Object getPropertyInternal(String key) {
                    return configFacade.getValue(key);
                }

                @Override
                protected boolean isEmptyInternal() {
                    return false;
                }

                @Override
                protected boolean containsKeyInternal(String key) {
                    return configFacade.containsKey(key);
                }
            };

        }

        return configuration;
    }

}
