package app.sipkp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.sipkp.R;
import app.sipkp.helper.AppPreference;
import app.sipkp.retrofit.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText userNameEdt, passEdt;
    Context mContext;
    AppPreference appPreference;
    SpinKitView spinKitView;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEdt = findViewById(R.id.username_edt);
        passEdt = findViewById(R.id.password_edt);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.GONE);

        mContext = LoginActivity.this;

        appPreference = new AppPreference(getApplicationContext());
    }

    public void onClickLogin(View view) {
        spinKitView.setVisibility(View.VISIBLE);
        //check data in editText
        if (userNameEdt.getText().toString().isEmpty()){
            userNameEdt.setError(getResources().getString(R.string.error_field_required));
            spinKitView.setVisibility(View.GONE);
            return;
        } else if (passEdt.getText().toString().isEmpty()){
            passEdt.setError(getResources().getString(R.string.error_field_required));
            spinKitView.setVisibility(View.GONE);
            return;
        }
        String uName = userNameEdt.getText().toString();
        String pass = passEdt.getText().toString();
        //request login to web server
        UtilsApi.getAPIService().loginRequest(uName, pass)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call,
                                           @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            spinKitView.setVisibility(View.GONE);
                            try {
                                assert response.body() != null;
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //if login success
                                if (jsonRESULTS.getInt("response_code")== 200){
                                    message = jsonRESULTS.getString("message");
                                    //get public data
                                    String urlImage = jsonRESULTS.getString("image");
                                    int idProfile = jsonRESULTS.getInt("id");
                                    int idMhsKp = jsonRESULTS.getInt("id_mhs_kp");
                                    String nimProfile = jsonRESULTS.getString("nim");
                                    //save temporarily
                                    appPreference.setImageProfile(urlImage);
                                    appPreference.setIdProfile(idProfile);
                                    appPreference.setDataMhsKp(idMhsKp);
                                    appPreference.setNIMProfile(nimProfile);
                                    //go to activity
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // if not
                                    message = jsonRESULTS.getString("message");
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            spinKitView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        message = t.getMessage();
                        spinKitView.setVisibility(View.GONE);
                    }
                });
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}
