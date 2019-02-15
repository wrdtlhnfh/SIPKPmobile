package app.sipkp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;

import app.sipkp.R;
import app.sipkp.helper.AppPreference;
import app.sipkp.pojo.Profile;
import app.sipkp.retrofit.BaseApiService;
import app.sipkp.retrofit.Client;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    SpinKitView spinKitView;
    TextView namaTxt, instansiTxt, pembimbingTxt, mulaiTxt, selesaiTxt, nimTxt;
    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //init views
        spinKitView = findViewById(R.id.spin_kit);
        namaTxt = findViewById(R.id.detail_nama_txt);
        nimTxt = findViewById(R.id.detail_nim_txt);
        instansiTxt = findViewById(R.id.detail_instansi_txt);
        pembimbingTxt = findViewById(R.id.detail_pembimbing_txt);
        mulaiTxt = findViewById(R.id.detail_mulai_txt);
        selesaiTxt = findViewById(R.id.detail_selesai_txt);

        appPreference = new AppPreference(getApplicationContext());

        nimTxt.setText(appPreference.getNimProfile());

        CircleImageView profileImg = findViewById(R.id.profile_img_detail);
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.logo_unand)
                .placeholder(R.drawable.logo_unand);
        Glide.with(getApplicationContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(appPreference.getImageProfile())
                .into(profileImg);

        getData();
    }

    private void getData(){
        BaseApiService api = Client.createService(BaseApiService.class);
        api.getProfileData(appPreference.getIdProfile())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(@NonNull Call<Profile> call,
                                           @NonNull Response<Profile> response) {
                        assert response.body() != null;
                        namaTxt.setText(response.body().getNama());
                        instansiTxt.setText(response.body().getInstansi());
                        pembimbingTxt.setText(response.body().getPembimbing());
                        mulaiTxt.setText(response.body().getMulaiKp());
                        selesaiTxt.setText(response.body().getSelesaiKp());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this)
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
}
