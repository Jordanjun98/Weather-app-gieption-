package com.example.jordan.weather;

import android.icu.util.ICUUncheckedIOException;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    double latitude = 3.1280;
    double longitude = 101.7543;

    public TextView textview;
    public TextView textview2;
    public TextView textview3;

    private CurrentWeather weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.textview);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);

        String apikey = getResources().getString(R.string.api_key);
        String forecast = "https://api.darksky.net/forecast/" + apikey + "/" + latitude + "," + longitude;


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                                    .url(forecast)
                                    .build();


        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try{
                    if(response.body() != null){
                        //Log.d("Testing", response.body().string());
                        weather = getCurrentWeather(response.body().string());
                    }
                    if (response.isSuccessful()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textview.setText("Summary :"+weather.getSummary());
                                textview2.setText("Humidity :"+weather.getHumidity());
                                textview3.setText("Temperature :"+weather.getTemperature());
                            }
                        });
                    }else{
                        alertDialog();
                    }
                }catch (IOException e){
                    Log.e("Error Happening","IOException error");
                }catch(JSONException e){
                    Log.e("Error happening","JSONException error");
                }
            }
        });


    }

    private CurrentWeather getCurrentWeather(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        double lat = forecast.getDouble("latitude");
        Log.d("latvalue",Double.toString(lat));

        JSONObject current = forecast.getJSONObject("currently");

        CurrentWeather curr = new CurrentWeather();

        curr.setHumidity(current.getDouble("humidity"));
        curr.setSummary(current.getString("summary"));
        curr.setTemperature(current.getDouble("temperature"));

        return curr;
    }

    private void alertDialog() {
        DialogFragmentAlert dialog = new DialogFragmentAlert();
        dialog.show(getFragmentManager(),"error_dialog");
    }
}
