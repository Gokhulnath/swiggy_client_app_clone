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