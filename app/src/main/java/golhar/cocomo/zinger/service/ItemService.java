package golhar.cocomo.zinger.service;

import java.util.List;

import golhar.cocomo.zinger.model.ItemModel;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemService {

    @POST("/menu")
    public Call<Response<String>> insertItem(@Body ItemModel itemModel,
                                       @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/menu/shop/{collegeId}")
    public Call<Response<List<ItemModel>>> getItemsByShopId(@Path("collegeId") Integer collegeId,
                                                            @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/menu/{collegeId}/{itemName}")
    public Call<Response<List<ItemModel>>> getItemsByName(@Path("collegeId") Integer collegeId, @Path("itemName") String itemName,
                                                    @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu")
    public Call<Response<String>> updateItemById(@Body ItemModel itemModel,
                                           @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu/delete")
    public Call<Response<String>> deleteItemById(@Body ItemModel itemModel,
                                           @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu/undelete")
    public Call<Response<String>> unDeleteItemById(@Body ItemModel itemModel,
                                             @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);
}
