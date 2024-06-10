package org.openmrs.module.appointmentsync.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmrs.module.appointmentsync.api.model.PatientAppointment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static void postAppointmentApi(String urlString, String json, String crudType) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(crudType);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
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
                paList = objectMapper.readValue(output, new TypeReference<List<PatientAppointment>>(){});
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return  paList;
    }

    public static String convertObjectToJson(PatientAppointment pa) {
        String jsonStr = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Object ========> " + pa.toString());
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pa);
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
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
    public static String convertToIsoDatetime(String dateStr) {
        String nowAsISO = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateStr);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            nowAsISO = df.format(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return nowAsISO;
    }
}
