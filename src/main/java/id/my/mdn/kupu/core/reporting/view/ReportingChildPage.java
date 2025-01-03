/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.service.ReportingJobQueue;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public abstract class ReportingChildPage extends ChildPage {

    @Inject
    private ReportingJobQueue jobQueue;

    protected boolean isReady() {
        return true;
    }

    protected abstract ReportingJob prepareReportingJob();

    public void prepareReport(ActionEvent event) {

        jobQueue.setBusy(true);
        PrimeFaces.current().executeScript("PF('poll').start()");

        if (!isReady()) {
            jobQueue.setBusy(false);
            return;
        }

        ReportingJob preparedJob = prepareReportingJob();

        jobQueue.put(preparedJob);

    }

    public void pollForCompleteness() {
        if (!jobQueue.isBusy()) {
            PrimeFaces.current().executeScript("PF('poll').stop()");
            PrimeFaces.current().executeScript("PF('blocker').hide()");
        }
    }
}
