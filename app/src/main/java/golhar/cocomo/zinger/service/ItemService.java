package golhar.cocomo.zinger.service;

import java.util.List;

import golhar.cocomo.zinger.model.ItemModel;
import golhar.cocomo.zinger.model.ShopModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Path;

public interface ItemService {

    @POST("/menu")
    public Response<String> insertItem(@Body ItemModel itemModel,
                                       @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/menu/shop")
    public Response<List<ItemModel>> getItemsByShopId(@Body ShopModel shopModel,
                                                      @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/menu/{collegeId}/{itemName}")
    public Response<List<ItemModel>> getItemsByName(@Path("collegeId") Integer collegeId, @Path("itemName") String itemName,
                                                    @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu")
    public Response<String> updateItemById(@Body ItemModel itemModel,
                                           @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu/delete")
    public Response<String> deleteItemById(@Body ItemModel itemModel,
                                           @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH("/menu/undelete")
    public Response<String> unDeleteItemById(@Body ItemModel itemModel,
                                             @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);
}
