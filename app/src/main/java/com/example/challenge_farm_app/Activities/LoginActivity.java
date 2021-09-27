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
import android.os.Environment;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.User;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AY";
    // String userAndroidId = "636bcf4b90a007bf"; // LG Id
    String userAndroidId = "D94BEA6FB3C703F1"; // Redmi 9 note s Id
    String phoneAndroidId = "D94BEA6FB3C703F1";
    String JSONPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    String ip;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        getLocalIpAddress();

        final EditText username = findViewById(R.id.input_username);
        final EditText pass = findViewById(R.id.input_password);
        AppCompatButton loginBTN = findViewById(R.id.btn_login);

        Log.d("androidId", "" + phoneAndroidId);

//        Toast.makeText(this, phoneAndroidId,Toast.LENGTH_LONG).show();
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (phoneAndroidId.equals(userAndroidId)) {
                checkUser(username.getText().toString(), pass.getText().toString());
//                } else {
//                    Toast.makeText(LoginActivity.this, "لا يمكنك الدخول على النظام من هذا الجهاز!", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    public void checkUser(final String username, final String pass) {
        // String ip = "192.168.1.8";
        // String ip = "192.168.1.112";
//         String ip = "10.0.2.2";

//        Log.d("IP HERE", ip);

        File file = new File(JSONPath + "/users.json");
        if (file.exists()) {
            fetchLocalUsers();
            loopAllUsers(username, pass);
        } else {
            fetchServerUsers(username, pass);

        }

    }

    private void loopAllUsers(String username, String pass) {
        final boolean[] found = {false};

        for (int i = 0; i < users.size(); i++)
            if (username.equals(users.get(i).getUsername()) && pass.equals(users.get(i).getPassword())) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("IP", ip);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_down, R.anim.bottom_up);
                found[0] = true;
                break;
            }

        if (!found[0])
            Toast.makeText(LoginActivity.this, "اسم المستخدم أو كلمة المرور غير صحيحة، يرجى المحاولة مرة أخرى!", Toast.LENGTH_SHORT).show();

    }

    private void fetchLocalUsers() {
        readFileOnInternalStorage("users.json");
    }

    private void fetchServerUsers(String username, String pass) {
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
        Call<UsersList> usersCall = service.getAllUsers();

        usersCall.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                users = response.body().getUsers();
                loopAllUsers(username, pass);
                try {
                    writeFileOnInternalStorage("users.json", prepareJSON(users));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this, "يُرجى تشغيل السيرفر عند أول استخدام للتطبيق!", Toast.LENGTH_SHORT).show();

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
//                  Toast.makeText(LoginActivity.this, "يُرجى تشغيل السيرفر عند أول استخدام للتطبيق!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, "يُرجى السماح بالوصول إلى أذونات الهاتف ومساحة التخزين!", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    public void writeFileOnInternalStorage(String sFileName, String sBody) {
        File dir = new File(JSONPath);
//        File dir = new File(String.valueOf(mContext.getFilesDir()));
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File file = new File(dir, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
//            Log.d("Write file", "wwwwwwwwwww");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String prepareJSON(ArrayList<User> usersArrayList) throws JSONException {

        JSONObject result = new JSONObject();

        JSONArray jsonUsersArray = new JSONArray();

        for (int i = 0; i < usersArrayList.size(); i++) {
            JSONObject user = new JSONObject();
            user.put("id", usersArrayList.get(i).getId());
            user.put("username", usersArrayList.get(i).getUsername());
            user.put("password", usersArrayList.get(i).getPassword());

            jsonUsersArray.put(user);
        }

        result.put("data", jsonUsersArray);

//        Log.d("PREPARE_JSON", result.toString());
//        Toast.makeText(AnimalsActivity.this, result.toString(), Toast.LENGTH_LONG).show();
        return result.toString();
    }

    public void readFileOnInternalStorage(String sFileName) {
        File file = new File(JSONPath, sFileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String response = stringBuilder.toString();
//            Log.d("READ_FILE", response);
            JSONObject JSONResponse = new JSONObject(response);
            UsersList localList = new Gson().fromJson(String.valueOf(JSONResponse), UsersList.class);

            users = localList.getUsers();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void refresh(View view) {
        Intent i = new Intent(LoginActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
    }
/*

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

*/

}