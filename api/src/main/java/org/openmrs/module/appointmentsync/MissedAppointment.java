package org.openmrs.module.appointmentsync;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentsync.api.AppointmentSyncService;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;
import org.openmrs.module.appointmentsync.api.utils.Util;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.util.List;

public class MissedAppointment extends AbstractTask {

    @Override
    public void execute() {

        String url = Context.getAdministrationService().getGlobalProperty("appointmentsync.cloud");
        String username = Context.getAdministrationService().getGlobalProperty("appointmentsync.cloud.username");
        String password = Context.getAdministrationService().getGlobalProperty("appointmentsync.cloud.password");

        AppointmentSyncService service = Context.getService(AppointmentSyncService.class);
        List<PatientAppointment> missedAppointments = service.getMissedAppointments();

        for (PatientAppointment pa : missedAppointments) {
            if (!missed(pa.getPatientAppointmentId(), url,username,password)) {
                Util.postAppointmentApi(url,username,password ,Util.convertObjectToJson(pa), "PUT");
            }
        }

    }

    // Check if status was set
    public boolean missed(String paId, String urlString,String username,String password) {
        boolean pub = false;
        for (PatientAppointment pa : Util.getAllAppointmentsApi(urlString,username,password)) {
            if (pa.getPatientAppointmentId().equals(paId))
                if (pa.getStatus().equals("Missing"))
                    pub = true;
                else
                    pub = false;
        }
        return pub;
    }

}
