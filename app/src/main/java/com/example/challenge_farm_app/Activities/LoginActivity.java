package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    // String userAndroidId = "636bcf4b90a007bf"; // LG Id
    String userAndroidId = "D94BEA6FB3C703F1"; // Redmi 9 note s Id
    String phoneAndroidId;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                1);

        getLocalIpAddress();

        final EditText username = findViewById(R.id.input_username);
        final EditText pass = findViewById(R.id.input_password);
        AppCompatButton loginBTN = findViewById(R.id.btn_login);

        Log.d("androidId", "" + phoneAndroidId);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneAndroidId.equals(userAndroidId)) {
                    checkUser(username.getText().toString(), pass.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "لا يمكنك الدخول على النظام من هذا الجهاز!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void checkUser(final String username, final String pass) {
        // String ip = "192.168.1.8";
        // String ip = "192.168.1.112";
//         String ip = "10.0.2.2";
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
        Call<UsersList> usersCall = service.getAllUsers();

        usersCall.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                UsersList users = response.body();
                boolean found = false, valid = true;

                for (int i = 0; i < users.getUsers().size(); i++)
                    if (username.equals(users.getUsers().get(i).getUsername()) && pass.equals(users.getUsers().get(i).getPassword())) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("IP",ip);
                        startActivity(intent);
                        overridePendingTransition(R.anim.bottom_down, R.anim.bottom_up);
                        found = true;
                        break;
                    }

                if (!found)
                    Toast.makeText(LoginActivity.this, "The username or password is wrong, try again please!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getLocalIpAddress() {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String phoneIP = Formatter.formatIpAddress(manager.getConnectionInfo().getIpAddress());
        String[] tokens = phoneIP.split("\\.");
        Log.d("phoneIP", phoneIP);

        int lastDigit = Integer.parseInt(tokens[3]);
        int prev, next;
        if (lastDigit - 19 > 0)
            prev = lastDigit - 19;
        else
            prev = 1;

        if (lastDigit + 19 < 256)
            next = lastDigit + 19;
        else
            next = 255;

        for (int i = prev; i <= next; i++) {
            String checkedIP = tokens[0] + "." + tokens[1] + "." + tokens[2] + "." + i;
            final GetDataService service = RetrofitClientInstance.getRetrofitInstance(checkedIP).create(GetDataService.class);
            Call<UsersList> usersCall = service.getAllUsers();
            usersCall.enqueue(new Callback<UsersList>() {
                @Override
                public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                    if (response.isSuccessful()) {
                        ip = checkedIP;
                    }
                }

                @Override
                public void onFailure(Call<UsersList> call, Throwable t) {
                    Log.d("onFailure", t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        phoneAndroidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "يُرجى السماح بالوصول إلى أذونات الهاتف للتحقق من صلاحية الجهاز لدخول النظام!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}