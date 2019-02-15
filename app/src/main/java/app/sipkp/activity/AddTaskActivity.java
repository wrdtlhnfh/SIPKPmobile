package app.sipkp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.sipkp.R;
import app.sipkp.helper.AppPreference;
import app.sipkp.retrofit.BaseApiService;
import app.sipkp.retrofit.Client;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends AppCompatActivity {
    TextView dateTxt;
    EditText taskEdt;
    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        dateTxt = findViewById(R.id.add_date);
        taskEdt = findViewById(R.id.add_task_edt);

        appPreference = new AppPreference(getApplicationContext());

        //get today data
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = simpleDateFormat.format(calendar.getTime());
        dateTxt.setText(dateStr);


    }

    public void onClickSaveTask(View view) {
        //dialog view
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this)
                .setTitle("Simpan Data")
                .setMessage("Apakah anda yakin akan menyimpan kegiatan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertData();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void insertData(){

        BaseApiService apiService = Client.createService(BaseApiService.class);
        apiService.insertTask(appPreference.getDataMhsKp(),
                dateTxt.getText().toString(), taskEdt.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                assert response.body() != null;
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //if login success
                                if (jsonRESULTS.getInt("response_code")== 200){
                                    String message = jsonRESULTS.getString("message");
                                    Toast.makeText(AddTaskActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                    setResult(Activity.RESULT_OK);
                                    finish();
                                } else {
                                    // if not
                                    String message = jsonRESULTS.getString("message");
                                    Toast.makeText(AddTaskActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Error get data response",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(AddTaskActivity.this, t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
