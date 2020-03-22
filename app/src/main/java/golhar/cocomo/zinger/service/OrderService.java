package golhar.cocomo.zinger.service;

import java.util.List;

import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {

    @POST("/order")
    public Call<Response<String>> insertOrder(@Body OrderItemListModel orderItemList,
                                              @Header("oauth_id") String oauthId, @Header("mobile") String mobile, @Header("role") String role);

    @POST("/order/verify")
    public Call<Response<String>> verifyOrder(@Body OrderItemListModel orderItemList,
                                              @Header("oauth_id") String oauthId, @Header("mobile") String mobile, @Header("role") String role);

    @GET("/order/customer/{mobile}/{pageNum}/{pageCount}")
    Call<Response<List<OrderItemListModel>>> getOrderByMobile(@Path("mobile") String mobile, @Path("pageNum") Integer pageNum, @Path("pageCount") Integer pageCount,
                                                      @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/order/seller/{shopId}/{pageNum}/{pageCount}")
    Call<Response<List<OrderModel>>> getOrderByShopIdPagination(@Path("shopId") String shopId, @Path("pageNum") Integer pageNum, @Path("pageCount") Integer pageCount,
                                                                @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET("/order/seller/{shopId}")
    public Call<Response<List<OrderModel>>> getOrderByShopId(@Path("shopId") Integer shopId,
                                                             @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @GET(value = "/order/{id}")
    public Call<Response<OrderModel>> getOrderById(@Path("id") String id,
                                                   @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "/order/rating")
    public Call<Response<String>> updateOrderRating(@Body OrderModel orderModel,
                                              @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "/order/key")
    public Call<Response<String>> updateOrderKey(@Body OrderModel orderModel,
                                                    @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);

    @PATCH(value = "/order/status")
    public Call<Response<String>> updateOrderStatus(@Body OrderModel orderModel,
                                                    @Header("oauth_id") String oauthId, @Header("mobile") String mobileRh, @Header("role") String role);
}
