package org.openmrs.module.appointmentsync;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentsync.api.AppointmentSyncService;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;
import org.openmrs.module.appointmentsync.api.utils.Util;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.util.List;

public class MissedAppointment extends AbstractTask  {

    @Override
    public void execute() {

        String url = Context.getAdministrationService().getGlobalProperty("appointmentsync.cloud");

        AppointmentSyncService service = Context.getService(AppointmentSyncService.class);
        List<PatientAppointment> missedAppointments = service.getMissedAppointments();

        for(PatientAppointment pa : missedAppointments) {
            if(!missed(pa.getPatientAppointmentId(), url)) {
                Util.postAppointmentApi(url, Util.convertObjectToJson(pa), "PUT");
            }
        }

    }

    // Check if status was set
    public boolean missed(String paId, String urlString) {
        boolean pub = false;
        for(PatientAppointment pa : Util.getAllAppointmentsApi(urlString)) {
            if (pa.getPatientAppointmentId().equals(paId))
                if(pa.getStatus().equals("Missing"))
                    pub = true;
                else pub = false;
        }
        return pub;
    }


}
