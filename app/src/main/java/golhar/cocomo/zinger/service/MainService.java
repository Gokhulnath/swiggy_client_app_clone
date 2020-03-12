package golhar.cocomo.zinger.service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainService {
    String BASE_URL = "http://analysed.in/analysed/webservices/";

    /*********************************************************************************/
    //Common-Tools

    @GET("common/getProfilePic.php")
    Call<Response<String>> getProfilePicApi(@Query("email") String email, @Query("userRole") String userRole);

    @FormUrlEncoded
    @POST("recruiter/closeJob.php")
    Call<String> closeJob(@Field("id") int id);

}
