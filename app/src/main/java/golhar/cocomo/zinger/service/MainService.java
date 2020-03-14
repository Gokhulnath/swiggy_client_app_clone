package golhar.cocomo.zinger.service;

import java.util.List;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface MainService {
    String BASE_URL = "https://food-backend-ssn.herokuapp.com";

    /*********************************************************************************/

//    @GET("common/getProfilePic.php")
//    Call<Response<String>> getProfilePicApi(@Query("email") String email, @Query("userRole") String userRole);
//
//    @FormUrlEncoded
//    @POST("recruiter/closeJob.php")
//    Call<String> closeJob(@Field("id") int id);

    @POST("/user/customer")
    Call<Response<UserCollegeModel>> insertCustomer(@Body UserModel user);

    @GET("/order/customer/{mobile}/{pageNum}/{pageCount}")
    Call<Response<List<OrderModel>>> getOrderByMobile(@Path("mobile") String mobile, @Path("pageNum") Integer pageNum, @Path("pageCount") Integer pageCount,
                                                      @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

}
