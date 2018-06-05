package occurrens.zaica.walik.occurrens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API api = new API("http://localhost:5000/", 1);

        /*api.sendGET("books/read/1/", new ResponseAPI() {
            public void onResponse() {
                try {
                    Log.d("Walik", "API Response: " + this.getAsJSON().getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(int responseCode) {
                Log.d("Walik", "API Error: " + responseCode);
            }
        });*/

        String[] data = {"username=walik", "password=123456789"};

        api.sendPOST("users/login", data, new ResponseAPI() {
            public void onResponse() {
                Log.d("Walik", "API Response: " + this.response);
            }

            public void onError(int responseCode) {
                Log.d("Walik", "API Error: " + responseCode);
            }
        });
    }
}
