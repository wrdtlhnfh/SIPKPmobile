package app.sipkp.retrofit;

import app.sipkp.pojo.Profile;
import app.sipkp.pojo.TaskResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("mobile/login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    //data profile
    @GET("mobile/{id_mhs}/profil")
    Call<Profile> getProfileData(@Path("id_mhs") int idProfile);

    @GET("mobile/{id_mhs_kp}/harian")
    Call<TaskResponse> getTaskData(@Path("id_mhs_kp")int idMhsKp);

    @FormUrlEncoded
    @POST("mobile/store_harian")
    Call<ResponseBody> insertTask(@Field("id_mhs_kp")int idMhsKp,
                                  @Field("tanggal")String tanggal,
                                  @Field("kegiatan")String kegiatan);

}
