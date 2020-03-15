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

public interface UserService {

    @POST("/user/customer")
    Call<Response<UserCollegeModel>> insertCustomer(@Body UserModel user);

    @POST("/user/seller")
    Call<Response<UserShopListModel>> insertSeller(@Body UserModel user);

    @PATCH("/user")
    Call<Response<String>> updateUser(@Body UserModel userModel,
                                      @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "/user/college")
    Call<Response<String>> updateUserCollegeData(@Body UserCollegeModel userCollegeModel,
                                                 @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

}
