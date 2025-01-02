/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.model.JenisLaporanTransaksi;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisLaporanTransaksiList")
@Dependent
public class JenisLaporanTransaksiList implements IValueList<JenisLaporanTransaksi> {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ApplicationUserFacade userFacade;

    List<String> groups;

    @PostConstruct
    private void init() {
        String username = securityContext.getCallerPrincipal().getName();
        ApplicationUser user = userFacade.findByUsername(username);

        groups = userFacade.getGroups(user).stream()
                .map(e -> e.getOrganization().getName())
                .collect(Collectors.toList());

    }

    private Predicate<JenisLaporanTransaksi> filterIn = (x) -> {
        boolean allowed = false;
        for (String role : x.getRoles()) {
            allowed = groups.contains(role);
            if (allowed) {
                break;
            }
        }

        return allowed;
    };

    @Override
    public List<JenisLaporanTransaksi> getFetchedItems() {
        return Arrays.asList(JenisLaporanTransaksi.values()).stream()
                .filter(filterIn)
                .collect(Collectors.toList());
    }

    public void setFilterIn(Predicate<JenisLaporanTransaksi> filterIn) {
        this.filterIn = filterIn;
    }

}
