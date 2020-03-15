package golhar.cocomo.zinger.service;

import java.util.List;

import golhar.cocomo.zinger.model.*;
import golhar.cocomo.zinger.utils.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CollegeService {

    @POST("/college")
    public Call<Response<String>> insertCollege(@Body CollegeModel collegeModel,
                                                @Header("oauth_id") String oauthId, @Header("mobile") String mobile, @Header("role") String role);

    @GET("/college")
    public Call<Response<List<CollegeModel>>> getAllColleges(@Header("oauth_id") String oauthId, @Header("mobile") String mobile, @Header("role") String role);
}
