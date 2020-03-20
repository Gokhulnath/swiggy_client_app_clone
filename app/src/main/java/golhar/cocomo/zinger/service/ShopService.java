package golhar.cocomo.zinger.service;

import java.util.List;

import golhar.cocomo.zinger.model.ConfigurationModel;
import golhar.cocomo.zinger.model.ShopConfigurationModel;
import golhar.cocomo.zinger.model.ShopModel;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShopService {

    @POST("/shop")
    public Call<Response<String>> insertShop(@Body ShopModel shopModel,
                                             @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET(value = "/shop/college/{id}")
    public Call<Response<List<ShopConfigurationModel>>> getShopsByCollegeId(@Path("id") String  id,
                                                                            @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "/shop/config")
    public Call<Response<String>> updateShopConfiguration(@Body ConfigurationModel configurationModel,
                                                          @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);
}
