package app.sipkp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.sipkp.R;


/**
 *
 */
public class AppPreference {
//digunakan untuk menyimpan data sementara
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public AppPreference(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mContext = context;
    }

    public void setDataMhsKp(int dataMhsKp){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mContext.getResources().getString(R.string.data_kp);
        editor.putInt(key, dataMhsKp);
        editor.apply();
    }

    public int getDataMhsKp(){
        String key = mContext.getResources().getString(R.string.data_kp);
        return mSharedPreferences.getInt(key, 0);
    }

    public void setIdProfile(int dataId){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mContext.getResources().getString(R.string.data_id);
        editor.putInt(key, dataId);
        editor.apply();
    }

    public int getIdProfile(){
        String key = mContext.getResources().getString(R.string.data_id);
        return mSharedPreferences.getInt(key, 0);
    }

    public void setImageProfile(String urlImage){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mContext.getResources().getString(R.string.url_image);
        editor.putString(key, urlImage);
        editor.apply();
    }

    public String getImageProfile(){
        String key = mContext.getResources().getString(R.string.url_image);
        return mSharedPreferences.getString(key, null);
    }

    public void setNIMProfile(String nim){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mContext.getResources().getString(R.string.profileNim);
        editor.putString(key, nim);
        editor.apply();
    }

    public String getNimProfile(){
        String key = mContext.getResources().getString(R.string.profileNim);
        return mSharedPreferences.getString(key, null);
    }



}
