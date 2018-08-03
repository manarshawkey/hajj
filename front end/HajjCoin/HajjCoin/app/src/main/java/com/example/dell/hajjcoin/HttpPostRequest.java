package com.example.dell.hajjcoin;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

interface OnTaskCompleted{
    void onTaskCompleted(String res);
    void UpdateProgress(int flag);

}

public class HttpPostRequest extends AsyncTask<String, Integer, String> {
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 1500;
    private OnTaskCompleted listener;

    private String postData;

    HttpPostRequest(String json, OnTaskCompleted listener){
        if (json != null)
            this.postData =  json;
        this.listener = listener;
    }

    @Override
    protected  void onPreExecute(){
        listener.UpdateProgress(20);
    }
    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result = "", inputLine;
        try
        {

            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            publishProgress(40);
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json");

            if (this.postData != null)
            {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(postData);
                writer.flush();
                writer.close();
            }
            publishProgress(75);

            int statusCode = connection.getResponseCode();
            if (statusCode == 200)
            {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                final StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null)
                {
                    stringBuilder.append(inputLine);
                }
                result = stringBuilder.toString();
                streamReader.close();
                publishProgress(90);
            }
            else Log.e("Error", "Error connecting");

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

        return  result;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        listener.UpdateProgress(values[0]);
    }


    @Override
    protected void onPostExecute(String result) {
        listener.onTaskCompleted(result);
        listener.UpdateProgress(100);
    }


}