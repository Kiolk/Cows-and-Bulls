package com.github.kiolk.cowsandbulls.data.repositories.game.remote;

import android.util.Log;

import com.github.kiolk.cowsandbulls.data.models.GameResultRemote;
import com.github.kiolk.cowsandbulls.data.repositories.game.GameDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RemoteGameDataSource implements GameDataSource {

    @Override
    public void publishResult(GameResultRemote result) {
        String stringUrl = "https://us-central1-cowsandbulls-acdea.cloudfunctions.net/result";
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);         //Create a connection
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();         //Set methods and timeouts
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);


            JSONObject jsonParam = new JSONObject();
            jsonParam.put("login", result.getUserName());
            jsonParam.put("uuid", result.getUuid());
            jsonParam.put("moves", result.getMoves());
            jsonParam.put("time", result.getTime());
            jsonParam.put("date", result.getDate());

            //Connect to our url
//            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
//            os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonParam.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
                os.close();
            }

//            os.flush();
//            os.close();

            Log.i("MyLogs", String.valueOf(connection.getResponseCode()));
            Log.i("MyLogs", connection.getResponseMessage());

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
