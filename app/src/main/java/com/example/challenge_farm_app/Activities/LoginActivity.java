package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.User;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;
import com.example.challenge_farm_app.Activities.AnimalsActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    String device_imei = "";
    String IMEI1 = "352682501302805";
    String IMEI2 = "359646201302806";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBarf().hide();
        final EditText username = findViewById(R.id.input_username);

        final EditText pass = findViewById(R.id.input_password);
        AppCompatButton loginBTN = findViewById(R.id.btn_login);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        device_imei = telephonyManager.getDeviceId();

      /*  if (serialNumber.equals("unknown")){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serialNumber = Build.getSerial();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                Log.d("responseData", String.valueOf(e.getMessage()));
            }
        }*/
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser(username.getText().toString(), pass.getText().toString());
            }
        });

    }

    public void checkUser(final String username, final String pass) {

        // String ip = getLocalIpAddress();
        // String ip = "192.168.1.106";
        String ip = "10.0.2.2";
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
        Call<UsersList> usersCall = service.getAllUsers();

        usersCall.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                UsersList users = response.body();
                boolean found = false, valid = true;
                //TODO: Change this condition to not (!)
                if (!device_imei.equals(IMEI1) && !device_imei.equals(IMEI2))
                {
                    Toast.makeText(LoginActivity.this, "You can't login to our system on this device!", Toast.LENGTH_LONG).show();
                    //valid = false;
                }
                else{
                    for(int i=0; i<users.getUsers().size(); i++)
                        if (username.equals(users.getUsers().get(i).getUsername()) && pass.equals(users.getUsers().get(i).getPassword())) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
//                            overridePendingTransition(R.anim.bottom_up, R.anim.activity);
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                            overridePendingTransition(R.anim.activity, R.anim.bottom_down);
//                            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);

                            found = true;
                            break;
                            //}
                        }

                    if (!found)
                        Toast.makeText(LoginActivity.this, "The username or password is wrong, try again please!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public String getLocalIpAddress() {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(manager.getConnectionInfo().getIpAddress());

      /*  InetAddress iA= null;
        try {
            iA = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(iA.getHostAddress());*/

      /*  try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Socket Error",ex.toString());
        }
        return ""; */
    }
}
