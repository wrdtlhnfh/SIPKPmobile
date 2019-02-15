package app.sipkp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.sipkp.R;
import app.sipkp.adapter.TaskAdapter;
import app.sipkp.helper.AppPreference;
import app.sipkp.helper.Config;
import app.sipkp.pojo.Profile;
import app.sipkp.pojo.Task;
import app.sipkp.pojo.TaskResponse;
import app.sipkp.retrofit.BaseApiService;
import app.sipkp.retrofit.Client;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView nameTxt, placeTxt, nimTxt;
    RecyclerView recyclerView;
    AppPreference appPreference;
    SpinKitView spinKitView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        Toolbar toolbar = findViewById(R.id.toolbar);
        nameTxt = findViewById(R.id.name_txt);
        placeTxt = findViewById(R.id.place_txt);
        nimTxt = findViewById(R.id.nim_txt);
        spinKitView = findViewById(R.id.spin_kit);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        CircleImageView profileImg = findViewById(R.id.profile_img);

        setSupportActionBar(toolbar);
        appPreference = new AppPreference(getApplicationContext());

        nimTxt.setText(appPreference.getNimProfile());
        //fetch image data to view
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.logo_unand)
                .placeholder(R.drawable.logo_unand);
        Glide.with(getApplicationContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(appPreference.getImageProfile())
                .into(profileImg);

        getDataProfile();
        getDataKP();

    }

    private void getDataProfile(){
        BaseApiService api = Client.createService(BaseApiService.class);
        api.getProfileData(appPreference.getIdProfile())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(@NonNull Call<Profile> call,
                                           @NonNull Response<Profile> response) {
                        assert response.body() != null;
                            nameTxt.setText(response.body().getNama());
                            placeTxt.setText(response.body().getInstansi());
                            spinKitView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Profile> call,
                                          @NonNull Throwable t) {
                        Log.v(MainActivity.class.getSimpleName(), t.getMessage());
                        spinKitView.setVisibility(View.GONE);
                    }
                });
    }

    private void getDataKP(){
        BaseApiService api = Client.createService(BaseApiService.class);
        api.getTaskData(appPreference.getDataMhsKp())
                .enqueue(new Callback<TaskResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TaskResponse> call,
                                           @NonNull Response<TaskResponse> response) {
                        assert response.body() != null;
                        List<Task> tasks = response.body().getHarian();

                        Collections.reverse(tasks);

                        TaskAdapter adapter = new TaskAdapter(getApplicationContext(), tasks);
                        recyclerView.setAdapter(adapter);
                        spinKitView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<TaskResponse> call,
                                          @NonNull Throwable t) {
                        Log.v(MainActivity.class.getSimpleName(), t.getMessage());
                        spinKitView.setVisibility(View.GONE);
                    }
                });
    }


    public void onClickAddTask(View view) {
        startActivityForResult(new Intent(getApplicationContext(),
                AddTaskActivity.class), Config.REQUEST_ADD_TASK);
    }

    public void onClickLogout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Log Out")
                .setMessage("Apakah anda yakin akan logout ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appPreference.setIdProfile(0);
                        appPreference.setDataMhsKp(0);
                        appPreference.setImageProfile(null);
                        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_ADD_TASK){
            if (resultCode == Activity.RESULT_OK){
                getDataProfile();
                getDataKP();
            } else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Tambah data batal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Apakah anda yakin akan logout ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appPreference.setIdProfile(0);
                                appPreference.setDataMhsKp(0);
                                appPreference.setImageProfile(null);
                                appPreference.setNIMProfile(null);
                                startActivity(new Intent(getApplicationContext(),
                                        LoginActivity.class));
                                finish();
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
                break;
        }
        return true;
    }

    public void onClickProfile(View view) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }
}
