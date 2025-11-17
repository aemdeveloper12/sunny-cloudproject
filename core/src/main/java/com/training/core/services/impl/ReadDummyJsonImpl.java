package com.training.core.services.impl;

import com.training.core.services.PaymentConfigs;
import com.training.core.services.ReadDummyJson;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = ReadDummyJson.class, immediate = true)
public class ReadDummyJsonImpl implements ReadDummyJson {

    @Reference
    PaymentConfigs paymentConfigs;

    @Override
    public String getDatafromDummyJsonApi() throws Exception {

        URL url = new URL(paymentConfigs.getJsonURL());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int responseCode = con.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            return "Error: API returned response code " + responseCode;
        }

        // Read response
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );

        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        con.disconnect();

        return result.toString();
    }
}
