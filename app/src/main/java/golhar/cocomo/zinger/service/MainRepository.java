package golhar.cocomo.zinger.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository {

    public static MainService getService() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(MainService.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

        return (MainService) retrofit.create(MainService.class);
    }



    public static class DateTypeDeserializer implements JsonDeserializer<Date> {

        private final String[] DATE_FORMATS = new String[]{"dd/MM/yyyy HH:mm:ss", "HH:mm:ss"};

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format).parse(jsonElement.getAsString());
                } catch (Exception e) {
                }
            }
            throw new JsonParseException("Unparseable date: \""+jsonElement.getAsString());
        }

    }

}

/*
   //startActivity(new Intent(this, RecyclerViewDemoActivity.class));
        UserModel userModel=new UserModel();
        userModel.setMobile("9176019349");
        userModel.setOauthId("auth_9176019349");
        userModel.setRole(UserRole.CUSTOMER);
        userModel.setIsDelete(0);

        MainRepository.getService().insertCustomer(userModel).enqueue(new Callback<Response<UserCollegeModel>>() {
            @Override
            public void onResponse(Call<Response<UserCollegeModel>> call, retrofit2.Response<Response<UserCollegeModel>> response) {
                Response<UserCollegeModel> userCollegeModelResponse=response.body();
                Log.d("RetroFit",userCollegeModelResponse.toString());
            }
            @Override
            public void onFailure(Call<Response<UserCollegeModel>> call, Throwable t) {
                Log.d("RetroFit","errorr"+t);
            }
        });

        MainRepository.getService().getOrderByMobile("9176019349",1,5,"auth_9176019349",
                "9176019349",UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<OrderModel>>>() {
            @Override
            public void onResponse(Call<Response<List<OrderModel>>> call, retrofit2.Response<Response<List<OrderModel>>> response) {

                Response<List<OrderModel>> orderModelList=response.body();
                Log.d("RetroFit",orderModelList.toString());
            }

            @Override
            public void onFailure(Call<Response<List<OrderModel>>> call, Throwable t) {
                Log.d("RetroFit","errorr"+t);

            }
        });

*/