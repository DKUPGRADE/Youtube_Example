package com.example.youtubeexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button;

    String m_androidId;
    TelephonyManager telephoneManager;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    addchannel();
                } catch (Exception e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/" + channel)));
                }
            }


            public String getUserCountry(Context context) {
                try {
                    final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    final String simCountry = tm.getSimCountryIso();
                    if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                        return simCountry.toLowerCase(Locale.US);
                    } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                        String networkCountry = tm.getNetworkCountryIso();
                        if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                            return networkCountry.toLowerCase(Locale.US);
                        }
                    }
                } catch (Exception e) {
                    Log.d("TAG120", "getUserCountry: " + e);
                }
                return null;
            }

            private void addchannel() {

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();

                Call<List<youtube>> call = RetroFitClient.getRetrofitInstance().create(Api.class).getdata(m_androidId, ts);
                call.enqueue(new Callback<List<youtube>>() {
                    public void onFailure(@NonNull Call<List<youtube>> call, @NonNull Throwable th) {
                        Log.d("TAG54", "onFailure: " + th);
                        Toast.makeText(MainActivity.this, "All set err", Toast.LENGTH_SHORT).show();
                    }

                    public void onResponse(@NonNull Call<List<youtube>> call, @NonNull Response<List<youtube>> response) {


                        String channel = "c/MasterforMCPE";


                        String country_name = getUserCountry(MainActivity.this);

                        Locale loc = new Locale("", country_name);
                        loc.getDisplayCountry();


                        Log.d("TAG01", "onResponse: " + loc.getDisplayCountry());
                        Log.d("TAG01", "onResponse: " + response.body().get(0).getCountry());


                        if (response.body().get(0).getCountry().equals(loc.getDisplayCountry())) {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://www.youtube.com/" + channel));

                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Enjoy your youtube ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Sorry, Network issue", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }


        });

    }


}


