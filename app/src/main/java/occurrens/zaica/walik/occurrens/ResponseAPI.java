package occurrens.zaica.walik.occurrens;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by v.zaica on 6/5/2018.
 */

public abstract class ResponseAPI {
    public String response;

    public void setResponseStream(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = response.toString();
    }

    public JSONObject getAsJSON() throws JSONException {
        return new JSONObject(this.response);
    }

    abstract void onResponse();

    abstract void onError(int responseCode);
}
