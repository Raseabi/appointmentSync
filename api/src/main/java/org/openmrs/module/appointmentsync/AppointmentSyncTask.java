package org.openmrs.module.appointmentsync;

import org.openmrs.module.appointmentsync.api.AppointmentSyncService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;
import org.openmrs.module.appointmentsync.api.utils.Util;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.springframework.remoting.caucho.BurlapServiceExporter;

import java.util.List;

public class AppointmentSyncTask extends AbstractTask {

    private String URL = "";

    @Override
    public void execute() {

        String url = "http://173.212.221.182:8090/api/appointments";

        AppointmentSyncService service = Context.getService(AppointmentSyncService.class);
        List<PatientAppointment> dueAppointments = service.getAllAppointments(); // Only those due in 3 days

        for(PatientAppointment pa : dueAppointments) {
            if(!published(pa.getPatientAppointmentId(), url)) {
                Util.postAppointmentApi(url, Util.convertObjectToJson(pa));
            }
        }

    }

    public boolean published(String paId, String urlString) {
        boolean pub = false;
        for(PatientAppointment pa : Util.getAllAppointmentsApi(urlString)) {
            if (pa.getPatientAppointmentId().equals(paId))
                pub = true;
            else pub = false;
        }
        return pub;
    }

}
