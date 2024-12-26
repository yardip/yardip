/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.local.service;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.local.model.Translation;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author aphasan
 */
@Stateless
public class TranslationFacade extends AbstractFacade<Translation> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TranslationFacade() {
        super(Translation.class);
    }

    public Object[][] getContent(Locale locale, Locale defaultLocale) {
        List<Object[]> resultList = getEntityManager().createQuery(
                "SELECT t1.key, COALESCE(t2.value, t1.value) "
                + "FROM Translation t1 "
                + "LEFT OUTER JOIN Translation t2 "
                + "ON t2.key = t1.key "
                + "AND t2.locale = :locale "
                + "WHERE t1.locale = :fallback")
                .setParameter("locale", locale)
                .setParameter("fallback", defaultLocale)
                .getResultList();

        return resultList.toArray(new Object[resultList.size()][]);
    }

    public void dumpTranslations(ModuleInfo moduleInfo, List<Locale> listOfLocales) {
        listOfLocales.stream()
                .forEach(locale -> dumpTranslation(moduleInfo, locale));
    }

    @Asynchronous
    private void dumpTranslation(ModuleInfo moduleInfo, Locale locale) {
        String resource = "string_" + locale.toLanguageTag() + ".properties";

        InputStream in = moduleInfo.getResourceAsStream(resource);
        if (in != null) {
            Properties prop = new Properties();
            try {
                prop.load(in);
                prop.keySet().stream().map(Object::toString)
                        .forEach(k -> {
                            if (translationNotExist(k, locale)) {
                                Translation t = new Translation();
                                t.setKey(k);
                                t.setValue(prop.getProperty(k));
                                t.setLocale(locale);
                                create(t);
                            }
                        });
            } catch (IOException ex) {
                Logger.getLogger(TranslationFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean translationNotExist(String key, Locale locale) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);

        Root<Translation> translations = cq.from(Translation.class);

        cq.select(translations).where(
                cb.equal(translations.get("key"), key),
                cb.equal(translations.get("locale"), locale)
        );

        TypedQuery<Translation> q = getEntityManager().createQuery(cq);

        List<Translation> result = q.getResultList();
        return result.isEmpty();
    }

    public String findTranslation(String key, Locale locale) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);

        Root<Translation> translations = cq.from(Translation.class);

        cq.select(translations.get("value")).where(
                cb.equal(translations.get("key"), key),
                cb.equal(translations.get("locale"), locale)
        );

        TypedQuery<String> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
