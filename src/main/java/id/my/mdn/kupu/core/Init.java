package id.my.mdn.kupu.core;

import id.my.mdn.kupu.core.base.AbstractApplication;
import id.my.mdn.kupu.core.base.event.AppPostInitEvent;
import id.my.mdn.kupu.core.base.event.InitEvent;
import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.base.util.ModuleUtil;
import id.my.mdn.kupu.core.config.service.ConfigFacade;
import id.my.mdn.kupu.core.local.service.TranslationFacade;
import id.my.mdn.kupu.core.reporting.service.ReportingService;
import id.my.mdn.kupu.core.security.service.SecurityService;
import jakarta.enterprise.event.Event;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.PostConstructApplicationEvent;
import jakarta.faces.event.PreDestroyApplicationEvent;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;
import jakarta.inject.Inject;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arief Prihasanto <aphasan at medinacom.id>
 */
//@ApplicationScoped
public class Init implements SystemEventListener {

    @Inject
    private ConfigFacade configFacade;

    @Inject
    private SecurityService securityService;

    @Inject
    private TranslationFacade translationFacade;
    
    @Inject
    private ReportingService reportingService;

    @Inject
    @InitEvent
    Event<Object> appInitEvent;
    
    @Inject
    private AbstractApplication app;

    @Inject
    @AppPostInitEvent
    private Event<Object> appPostInitEvent;

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        
        if (event instanceof PostConstructApplicationEvent) {
            doPostConstructApplication();
        } else if (event instanceof PreDestroyApplicationEvent) {
            doPreDestroyApplication();
        }

    }

    private void doPostConstructApplication(/*@Observes @Initialized(ApplicationScoped.class) Object payload*/ ) {

        appInitEvent.fire(new Object());

        appPostInitEvent.fire(new Object());

        ModuleUtil.inspectModules(
                securityService::dumpSecurity,
                this::dumpTranslations,
                configFacade::dumpConfig,
                reportingService::dumpTemplate
                
        );

    }

    private void doPreDestroyApplication(/*@Observes @Destroyed(ApplicationScoped.class) Object payload*/) {
        
        

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ex) {
                Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void dumpTranslations(ModuleInfo moduleInfo) {
        
        translationFacade.dumpTranslations(moduleInfo, app.getAvailableLocales());
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return true;
    }

}
