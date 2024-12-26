/*
 * Copyright 2015 Arief Prihasanto <ariefp5758 at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Arief Prihasanto <ariefp5758 at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Arief Prihasanto <ariefp5758 at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Arief Prihasanto <ariefp5758 at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Arief Prihasanto <ariefp5758 at gmail.com>

 */
package id.my.mdn.kupu.core.config.service;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.config.model.Config;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
@Stateless
public class ConfigFacade extends AbstractFacade<Config> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigFacade() {
        super(Config.class);
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "module":
                return cb.equal(from[0].get("module"), filterValue);
            default:
                return null;
        }
    }

    public Iterator<String> getKeys() {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Config> root = cq.from(entityClass);

        cq.select(root.get("key"));

        TypedQuery<String> q = getEntityManager().createQuery(cq);

        return q.getResultList().iterator();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String getValue(String mixedKey) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Config> root = cq.from(entityClass);

        String[] splittedKey = mixedKey.split("\\.");
        
        cq.select(root.get("value")).where(
                cb.equal(root.get("module"), splittedKey[0]),
                cb.equal(root.get("key"), splittedKey[1])
        );

        TypedQuery<String> q = getEntityManager().createQuery(cq);

        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Map<String, String> getConfigs(Iterable<? extends String> mixedKeys) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Config> cq = cb.createQuery(Config.class);
        Root<Config> root = cq.from(entityClass);

        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        mixedKeys.forEach(key -> {
            String[] splittedKey = key.split("\\.");
            predicates.add(cb.equal(root.get("module"), splittedKey[0]));
            predicates.add(cb.equal(root.get("key"), splittedKey[1]));
        });

        cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Config> q = getEntityManager().createQuery(cq);

        return q.getResultList().stream()
                .collect(Collectors.toMap(Config::getKey, Config::getValue));
    }

    public void dumpConfig(ModuleInfo moduleInfo) {
        InputStream in = moduleInfo.getResourceAsStream("config.json");
        if (in != null) {

            try (JsonReader jsonReader = Json.createReader(in)) {
                JsonArray jsonArray = jsonReader.readArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonConfiguration = jsonArray.getJsonObject(i);
                    processConfig(moduleInfo.getName(), jsonConfiguration);
                }
            }

        }

    }

    private void processConfig(String moduleName, JsonObject jsonConfiguration) {
        String key = jsonConfiguration.getString("key");
        if (!containsKey(moduleName, key)) {
            Config cfg = new Config();
            cfg.setModule(moduleName);
            cfg.setKey(key);
            cfg.setValue(jsonConfiguration.getString("value"));
            cfg.setRemarks(jsonConfiguration.getString("remarks"));
            create(cfg);
        }
    }

    public boolean containsKey(String mixedKey) {
        

        String[] splittedKey = mixedKey.split("\\.");

        return containsKey(splittedKey[0], splittedKey[1]);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean containsKey(String moduleName, String key) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Config> cq = cb.createQuery(Config.class);
        Root<Config> root = cq.from(entityClass);

        cq.select(root).where(cb.equal(root.get("module"), moduleName),
                cb.equal(root.get("key"), key)
        );

        TypedQuery<Config> q = getEntityManager().createQuery(cq);

        return !q.getResultList().isEmpty();
    }

}
