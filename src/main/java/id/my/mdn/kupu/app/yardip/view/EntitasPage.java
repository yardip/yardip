/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.base.view.widget.Toast;
import id.my.mdn.kupu.core.hr.dao.EmploymentFacade;
import id.my.mdn.kupu.core.hr.entity.Employee;
import id.my.mdn.kupu.core.hr.entity.Employment;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.hr.view.EmploymentPage;
import id.my.mdn.kupu.core.hr.view.admin.EmploymentEditorPage;
import id.my.mdn.kupu.core.hr.view.admin.PositionEditorPage;
import id.my.mdn.kupu.core.hr.view.widget.PositionTree;
import id.my.mdn.kupu.core.party.dao.OrganizationFacade;
import id.my.mdn.kupu.core.party.dao.PartyContactMechanismFacade;
import id.my.mdn.kupu.core.party.dao.PostalAddressFacade;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.entity.ContactType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyContactMechanism;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import id.my.mdn.kupu.core.party.view.BusinessEntityEditorPage;
import id.my.mdn.kupu.core.party.view.form.PostalAddressForm;
import id.my.mdn.kupu.core.party.view.widget.BusinessEntityTree;
import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationSecurityGroup;
import id.my.mdn.kupu.core.security.view.ApplicationUserEditorPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "entitasPage")
@ViewScoped
public class EntitasPage extends Page implements Serializable {

    @Bookmarked
    @Inject
    private BusinessEntityTree entityTree;

    @Bookmarked
    @Inject
    private PositionTree positionTree;

    @Inject
    private OrganizationFacade orgDao;

    @Inject
    private EmploymentFacade employmentFacade;

    @Inject
    private PartyContactMechanismFacade contactMechanismFacade;

    @Inject
    private ApplicationUserFacade userFacade;

    @Inject
    private PostalAddressForm postalAddressForm;

    @Inject
    private PostalAddressFacade postalAddressFacade;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        entityTree.setSelectionMode(() -> Selector.SINGLE);
        entityTree.setName("entityTree");
        entityTree.setSelectionsLabel("es");

        entityTree.getSelector().addListenerInternal(this::loadDetail);
        entityTree.addSelectListener(this::loadDetail);

        positionTree.setName("positionTree");
        positionTree.setSelectionMode(() -> Selector.SINGLE);
        positionTree.setSelectionsLabel("as");
        positionTree.getFilter().setStaticFilter(this::getPositionFilter);

//        entityTree.reset();
//        positionTree.reset();
    }

    public void loadDetail(Object obj) {
        if (entityTree.getSelection() != null) {
            PartyContactMechanism partyContactMechanism
                    = getDefaultPostalAddress(entityTree.getSelection());
            entityTree.getSelected().setPostalAddress((PostalAddress) partyContactMechanism.getContactMechanism());
            postalAddressForm.init(partyContactMechanism);
        }
        positionTree.setSelectionInternal(null);
        positionTree.reset();

    }

    private List<FilterData> getPositionFilter() {
        return List.of(
                FilterData.by("businessEntity", entityTree.getSelection())
        );
    }

    @Creator(of = "entityTree")
    public void openDataCreator() {
        RequestedView creatorPage = gotoChild(BusinessEntityEditorPage.class);

        BusinessEntity parent = entityTree.getSelected();

        if (parent != null) {
            creatorPage.addParam("parent").withValues(parent);
            creatorPage.addParam("currentDate")
                    .withValues(LocalDate.now());
        }

        creatorPage.open();
    }

    @Deleter(of = "entityTree")
    public void openDataDeleter() {
        entityTree.deleteSelections();
    }

    @Creator(of = "positionTree")
    public void openPositionCreator() {
        RequestedView creatorPage = gotoChild(PositionEditorPage.class);

        BusinessEntity businessEntity = entityTree.getSelected();
        creatorPage.addParam("entitas").withValues(businessEntity);

        Position parent = positionTree.getSelected();
        if (parent != null) {
            creatorPage.addParam("parent").withValues(parent);
        }

        creatorPage.open();
    }

    @Editor(of = "positionTree")
    public void openPositionEditor() {
    }

    @Deleter(of = "positionTree")
    public void openPositionDeleter() {
        positionTree.deleteSelections();
    }

    public void addEmployment(ActionEvent evt) {
        RequestedView creatorPage = gotoChild(EmploymentEditorPage.class);

        Position position = positionTree.getSelector().getSelection();
        creatorPage.addParam("pos").withValues(position);

        creatorPage.open();
    }

    public void listEmployment(ActionEvent evt) {
        gotoChild(EmploymentPage.class)
                .addParam("position")
                .withValues(positionTree.getSelected())
                .open();
    }

    @Inject
    private ApplicationSecurityGroupFacade groupFacade;

    public void createAppUser(ActionEvent evt) {
        Employment employment = employmentFacade.getExclusiveEmployment(
                positionTree.getSelected(), LocalDate.now()
        );

        if (employment != null) {
            Employee employee = employment.getEmployee();
            BusinessEntity businessEntity = employment.getBusinessEntity();
            ApplicationSecurityGroup entitasGroup = groupFacade.
                    findSingleByAttribute("party", businessEntity.getOrganization());
            ApplicationSecurityGroup adminGroup = groupFacade.findByName("yardip", "Administrator Entitas");
            gotoChild(ApplicationUserEditorPage.class)
                    .addParam("party")
                    .withValues(employee.getParty())
                    .addParam("groups")
                    .withValues(entitasGroup, adminGroup)
                    .open();
        }

    }

    public void removeAppUser(ActionEvent evt) {
        Employment employment = employmentFacade.getExclusiveEmployment(
                positionTree.getSelected(), LocalDate.now()
        );

        Employee employee = employment.getEmployee();
        Person person = employee.getPerson();
        boolean hasRemoved = userFacade.removeAppUser(person);
        if (hasRemoved) {
            Toast.info("Akun pengguna telah dihapus");
        } else {
            Toast.info("Tidak ada akun pengguna yang dihapus");
        }
    }

    public void handleLogoUpload(FileUploadEvent event) {
        byte[] content = event.getFile().getContent();
        Organization org = entityTree.getSelected().getOrganization();
        org.setLogo(content);
        orgDao.edit(org);
    }

    public String getCurrentEmployee(Position position) {
        Employment employment = employmentFacade.getExclusiveEmployment(
                position, LocalDate.now()
        );

        if (employment != null) {
            return employment.getEmployee().getPerson().getName();
        } else {
            return "Belum ada";
        }
    }

    private PartyContactMechanism getDefaultPostalAddress(BusinessEntity businessEntity) {
        PartyContactMechanism partyContactMechanism
                = contactMechanismFacade.findDefaultContactMechanism(
                        businessEntity.getOrganization(),
                        ContactType.PostalAddress
                );

        return partyContactMechanism;
    }

    public void updatePostalAddress(ActionEvent evt) {
        BusinessEntity businessEntity = entityTree.getSelector().getSelection();
        postalAddressFacade.edit(businessEntity.getPostalAddress());
    }

    public void onEntityEdit(AjaxBehaviorEvent evt) {
        entityTree.edit(entityTree.getSelection());
    }

    public BusinessEntityTree getEntityTree() {
        return entityTree;
    }

    public PositionTree getPositionTree() {
        return positionTree;
    }

    public PostalAddressForm getPostalAddressForm() {
        return postalAddressForm;
    }

}
