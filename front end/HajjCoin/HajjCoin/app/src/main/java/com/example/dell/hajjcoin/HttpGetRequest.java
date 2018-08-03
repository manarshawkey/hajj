package com.example.dell.hajjcoin;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class HttpGetRequest extends AsyncTask<String, String, String> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 1500;
    private OnTaskCompleted listener;

    HttpGetRequest(OnTaskCompleted listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result ="", inputLine;
        try
        {

            URL myUrl= new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            final StringBuilder stringBuilder = new StringBuilder();
            while((inputLine = reader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            result = stringBuilder.toString();
            streamReader.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
            result = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = null;
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        listener.onTaskCompleted(result);
    }
}