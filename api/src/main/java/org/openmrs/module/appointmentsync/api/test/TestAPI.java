package org.openmrs.module.appointmentsync.api.test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.openmrs.Patient;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;

public class TestAPI {

    public static void main(String[] args) throws Exception {


        String urlString = "http://localhost:8090/api/appointments";

        PatientAppointment pa = populateObject();
        String json = convertObjectToJson(pa);
        List<PatientAppointment> appts = getAllAppointmentsApi(urlString);
        int count = 1;
        for(PatientAppointment appt : appts) {
            System.out.println(count + ". " + appt.toString());
            count++;
        }

        /*String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"; //2024-03-29T22:54:01.754Z
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601);
        Date d = new Date("2022/04/04 04:12:35");
        System.out.println(dateFormat.format(d));*/
    }

    public static void postAppointmentApi(String urlString, String json) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<PatientAppointment> getAllAppointmentsApi(String urlString) {
        List<PatientAppointment> paList = new ArrayList<PatientAppointment>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            final ObjectMapper objectMapper = new ObjectMapper();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;

            while ((output = br.readLine()) != null) {
                //System.out.println(">>>> "+ output);
                paList = objectMapper.readValue(output, new TypeReference<List<PatientAppointment>>(){});
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return  paList;
    }

    public static String convertObjectToJson(PatientAppointment pa) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pa);
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PatientAppointment convertJsonToObject(String json, Class c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientAppointment pa = mapper.readValue(json, PatientAppointment.class);
            return pa;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PatientAppointment populateObject() {
        PatientAppointment pa = new PatientAppointment();
        pa.setPatientAppointmentId("4");
        pa.setNames("Test Test");
        pa.setGender("M");
        pa.setPhone("0788743445");
        pa.setIdentifier("GAN20240310");
        pa.setLocation("Maseru");
        pa.setStartDate("2024-04-05T14:00:00");
        pa.setEndDate("2024-04-05T15:00:00");
        pa.setComment("Testing...");
        pa.setStatus("Pending");
        return pa;
    }

}
