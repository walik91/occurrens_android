package occurrens.zaica.walik.occurrens;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by v.zaica on 6/5/2018.
 */

public class API {
    private String baseURL;
    private int version;
    private String URL;

    public API(String URL, int api_version) {
        this.baseURL = URL;
        this.version = api_version;

        this.URL = this.baseURL + "api/v" + this.version+"/";
    }

    public boolean isHttps() {
        return this.baseURL.substring(4, 1).equals('s');
    }

    public void sendGET(String URI, final ResponseAPI callback) {
        final String URL = this.URL + URI;
        final Boolean isHttps = this.isHttps();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    java.net.URL apiEndpoint = new URL(URL);

                    if (isHttps) {
                        HttpsURLConnection apiConnection = (HttpsURLConnection) apiEndpoint.openConnection();
                    } else {
                        HttpURLConnection apiConnection = (HttpURLConnection) apiEndpoint.openConnection();
                    }

                    int responseCode = apiConnection.getResponseCode();

                    if (responseCode == 200) {
                        callback.setResponseStream(apiConnection.getInputStream());

                        callback.onResponse();
                    } else {
                        callback.onError(responseCode);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendPOST(String URI, final String[] data, final ResponseAPI callback) {
        final String URL = this.URL + URI;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    java.net.URL apiEndpoint = new URL(URL);

                    HttpsURLConnection apiConnection = (HttpsURLConnection) apiEndpoint.openConnection();

                    apiConnection.setRequestMethod("POST");

                    OutputStream out = new BufferedOutputStream(apiConnection.getOutputStream());

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                    writer.write(TextUtils.join("&", data));

                    writer.flush();

                    writer.close();

                    out.close();

                    int responseCode = apiConnection.getResponseCode();

                    if (responseCode == 200) {
                        callback.setResponseStream(apiConnection.getInputStream());

                        callback.onResponse();
                    } else {
                        callback.onError(responseCode);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}