package com.github.kiolk.cowsandbulls.data.repositories.game.remote;

import android.util.Log;

import com.github.kiolk.cowsandbulls.BuildConfig;
import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.Result;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;
import com.github.kiolk.cowsandbulls.data.repositories.game.GameDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RemoteGameDataSource implements GameDataSource {

    @Override
    public void publishResult(ResultRemote result) {
        String stringUrl = BuildConfig.BASE_URL + "result";
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
            jsonParam.put("deviceToken", result.getDeviceToken());

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

    @Override
    public List<ResultRemote> getBestResults(PeriodType type) {
        String stringUrl = BuildConfig.BASE_URL + "best?period=" + type.getValue();
        String inputLine;
        List<ResultRemote> results = new ArrayList<>();
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);         //Create a connection
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();         //Set methods and timeouts
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);

            connection.connect();         //Create a new InputStreamReader
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());         //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();         //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }         //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();         //Set our result equal to our stringBuilder
            String result = stringBuilder.toString();
            connection.disconnect();

            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                ResultRemote remote = new ResultRemote();
                remote.setTime(object.getLong("time"));
                remote.setMoves(object.getInt("moves"));
                remote.setUserName(object.getString("login"));
                remote.setUuid(object.getString("uuid"));
                remote.setDate(object.getString("date"));
                results.add(remote);
            }

            Log.d("MyLogs", "getBestResults: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }
}
