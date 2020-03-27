package golhar.cocomo.zinger.service;

import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.model.UserShopListModel;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("/user/customer")
    Call<Response<UserCollegeModel>> insertCustomer(@Body UserModel user);

    @POST("/user/seller")
    Call<Response<UserShopListModel>> insertSeller(@Body UserModel user);

    @PATCH(value = "/user/college")
    Call<Response<String>> updateUserCollegeData(@Body UserCollegeModel userCollegeModel,
                                                 @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @POST(value = "/user/seller/{mobile}/{shopId}")
    public Call<Response<String>> insertSeller(@Path("mobile") String mobile, @Path("shopId") Integer shopId,
                                               @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "")
    public Response<String> updateUser(@Body UserModel userModel,
                                       @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);;

}
