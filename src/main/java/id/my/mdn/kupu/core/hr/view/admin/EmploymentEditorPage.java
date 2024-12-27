/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.admin;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.hr.dao.EmploymentFacade;
import id.my.mdn.kupu.core.hr.entity.Employee;
import id.my.mdn.kupu.core.hr.entity.Employment;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.form.PersonEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "employmentEditorPage")
@ConversationScoped
public class EmploymentEditorPage extends FormPage<Employment> {

    @Inject
    private EmploymentFacade dao;

    @Inject
    @Form
    private PersonEditorForm form;

    @Bookmark
    private Position position;

    @Bookmark
    private Employee employee;

    @Bookmark
    private Person person;

    @Override
    public void load() {
        super.load();
        form.init(entity.getEmployee().getPerson());
        form.setValidityChecker(this::checkValid);
        form.setPacker(this::doPack);
    }

    private Result<String> checkValid() {
        Result<String> validity = form.getPersonForm().getValidityChecker().isValid();
        return validity;
    }

    private void doPack() {
        form.getPersonForm().getPacker().pack();
    }

    @Override
    protected Employment newEntity() {
        Employment employment = new Employment();
        BusinessEntity businessEntity = position.getBusinessEntity();
        employment.setBusinessEntity(businessEntity);
        employment.setPosition(position);
        employment.setFromDate(LocalDate.now());

        if (employee == null) {
            if (person == null) {
                person = Person.builder().get();
            }

            employee = Employee.builder()
                    .forBusinessEntity(businessEntity)
                    .withPerson(person)
                    .get();

        }
        
        employment.setEmployee(employee);

        businessEntity.getSourceRelationships().add(employment);
        employee.getTargetRelationships().add(employment);

        return employment;
    }

    @Override
    protected Result<String> save(Employment entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Employment entity) {
        return dao.edit(entity);
    }

    public PersonEditorForm getForm() {
        return form;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
