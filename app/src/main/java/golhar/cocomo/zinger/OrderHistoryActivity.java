package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.adapter.OrderHistoryAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.Callback;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView order_listRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ArrayList<OrderItemListModel> orderItemListModelArrayList=new ArrayList<>();
        order_listRV=findViewById(R.id.orderListRV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        order_listRV.setLayoutManager(linearLayoutManager);

        MainRepository.getOrderService().getOrderByMobile("9566220635",1,5,"auth_9566220635",
                "9566220635", UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<OrderItemListModel>>>() {
            @Override
            public void onResponse(Call<Response<List<OrderItemListModel>>> call, retrofit2.Response<Response<List<OrderItemListModel>>> response) {

                Response<List<OrderItemListModel>> responseFromServer=response.body();

                if(responseFromServer.getCode().equals(ErrorLog.CodeSuccess)&&responseFromServer.getMessage().equals(ErrorLog.Success)){
                    Log.d("RetroFit",responseFromServer.toString());
                    orderHistoryAdapter=new OrderHistoryAdapter(responseFromServer.getData(),getApplicationContext());
                    order_listRV.setAdapter(orderHistoryAdapter);

                }else{
                    Log.d("RetroFit","errorr");
                }
            }

            @Override
            public void onFailure(Call<Response<List<OrderItemListModel>>> call, Throwable t) {
                Log.d("RetroFit","errorr");
            }
        });



    }
}
