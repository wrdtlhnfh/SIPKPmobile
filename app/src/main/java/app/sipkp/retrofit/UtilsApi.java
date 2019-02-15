package app.sipkp.retrofit;

import app.sipkp.helper.Config;

/**
 * .
 */

public class UtilsApi {

    // initialize Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(Config.BASE_URL).create(BaseApiService.class);
    }
}
