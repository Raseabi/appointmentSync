/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.appointmentsync.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.module.appointmentsync.api.db.AppointmentSyncServiceDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * It is a default implementation of  {@link AppointmentSyncServiceDAO}.
 */
public class HibernateAppointmentSyncServiceDAO implements AppointmentSyncServiceDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }


	@Override
	public List<PatientAppointment> getAllAppointments() {

		StringBuffer sb = new StringBuffer();

		sb.append("select concat(b.given_name, family_name) as names, a.start_date_time as startDate, a.end_date_time as endDate, c.gender from patient_appointment a " +
				"left join person_name b on a.patient_id = b.person_id " +
				"left join person c on a.patient_id = c.person_id;");

		Session session = sessionFactory.getCurrentSession();

		Collection<Object[]> collection = session.createSQLQuery(sb.toString()).list();

		List<PatientAppointment> appointments = new ArrayList<PatientAppointment>();

		for (Object[] ob : collection) {
			PatientAppointment pa = new PatientAppointment();
			pa.setNames(ob[0].toString());
			pa.setStartDate(ob[1].toString());
			pa.setEndDate(ob[2].toString());
			pa.setGender(ob[3].toString());

			appointments.add(pa);
		}

		return appointments;
	}
}