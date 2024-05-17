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
import org.openmrs.module.appointmentsync.api.db.AppointmentSyncServiceDAO;

import org.hibernate.Session;
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

		sb.append("select a.patient_appointment_id, pi.identifier, '' as phone, concat(b.given_name, ' ', family_name) as names, a.start_date_time as startDate, a.end_date_time as endDate, c.gender, l.name as location, a.status, a.comments, a.date_changed, a.date_created from patient_appointment a " +
				"left join person_name b on a.patient_id = b.person_id " +
				"left join patient_identifier pi on a.patient_id = pi.patient_id " + 
				"left join patient_identifier_type pit on pi.identifier_type = pit.patient_identifier_type_id " +
				"left join location l on a.location_id = l.location_id " +
				"left join person c on a.patient_id = c.person_id " +
				"where pit.patient_identifier_type_id = 4 " +
				"and DATEDIFF(start_date_time, now()) = 3;");

		Session session = sessionFactory.getCurrentSession();

		Collection<Object[]> collection = session.createSQLQuery(sb.toString()).list();

		List<PatientAppointment> appointments = new ArrayList<PatientAppointment>();

		for (Object[] ob : collection) {
			PatientAppointment pa = new PatientAppointment();
			pa.setNames(ob[3].toString());
			pa.setStartDate(ob[4].toString());
			pa.setEndDate(ob[5].toString());
			pa.setGender(ob[6].toString());
			pa.setPatientAppointmentId(ob[0].toString());
			pa.setIdentifier(ob[1].toString());
			pa.setLocation(ob[7].toString());
			pa.setPhone(ob[2].toString());
			pa.setComment(ob[9].toString());
			pa.setStatus(ob[8].toString());
			pa.setLastUpdated(ob[10].toString());

			appointments.add(pa);
		}

		return appointments;
	}

	@Override
	public List<PatientAppointment> getMissedAppointments() {

		StringBuffer sb = new StringBuffer();

		sb.append("select a.patient_appointment_id, pi.identifier, '' as phone, concat(b.given_name, ' ', family_name) as names, a.start_date_time as startDate, a.end_date_time as endDate, c.gender, l.name as location, a.status, a.comments, a.date_changed from patient_appointment a " +
				"left join person_name b on a.patient_id = b.person_id " +
				"left join patient_identifier pi on a.patient_id = pi.patient_id " +
				"left join patient_identifier_type pit on pi.identifier_type = pit.patient_identifier_type_id " +
				"left join location l on a.location_id = l.location_id " +
				"left join person c on a.patient_id = c.person_id " +
				"where pit.patient_identifier_type_id = 4 " +
				"and DATEDIFF(start_date_time, now()) = 6 " +
				"and a.status = 'Missed'");

		Session session = sessionFactory.getCurrentSession();

		Collection<Object[]> collection = session.createSQLQuery(sb.toString()).list();

		List<PatientAppointment> appointments = new ArrayList<PatientAppointment>();

		for (Object[] ob : collection) {
			PatientAppointment pa = new PatientAppointment();
			pa.setNames(ob[3].toString());
			pa.setStartDate(ob[4].toString());
			pa.setEndDate(ob[5].toString());
			pa.setGender(ob[6].toString());
			pa.setPatientAppointmentId(ob[0].toString());
			pa.setIdentifier(ob[1].toString());
			pa.setLocation(ob[7].toString());
			pa.setPhone(ob[2].toString());
			pa.setComment(ob[9].toString());
			pa.setStatus(ob[8].toString());
			pa.setLastUpdated(ob[10].toString());

			appointments.add(pa);
		}

		return appointments;
	}
}