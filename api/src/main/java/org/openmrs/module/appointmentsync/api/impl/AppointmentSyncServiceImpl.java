/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p/>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p/>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.appointmentsync.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.appointmentsync.api.AppointmentSyncService;
import org.openmrs.module.appointmentsync.api.db.AppointmentSyncServiceDAO;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;

import java.util.Collection;
import java.util.List;

/**
 * It is a default implementation of {@link AppointmentSyncService}.
 */
public class AppointmentSyncServiceImpl extends BaseOpenmrsService implements AppointmentSyncService {

    protected final Log log = LogFactory.getLog(this.getClass());

    private AppointmentSyncServiceDAO dao;
    

    /**
     * @param dao the dao to set
     */
    public void setDao(AppointmentSyncServiceDAO dao) {
        this.dao = dao;
    }

    public AppointmentSyncServiceDAO getDao() { return dao; }

    @Override
    public List<PatientAppointment> getAllAppointments() {
        return dao.getAllAppointments();
    }
}
