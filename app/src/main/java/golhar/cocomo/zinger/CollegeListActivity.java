package golhar.cocomo.zinger;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import golhar.cocomo.zinger.adapter.RecyclerViewCollegeListAdapter;
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class
CollegeListActivity extends AppCompatActivity {
    RecyclerView rv_items;
    RecyclerViewCollegeListAdapter collegeAdapter;
    EditText collegeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        collegeName = (EditText) findViewById(R.id.collegeNameET);

        collegeAdapter =new RecyclerViewCollegeListAdapter(new ArrayList<>(),this);
        rv_items=findViewById(R.id.item_listRV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setAdapter(collegeAdapter);

        MainRepository.getCollegeService().getAllColleges("auth_9566220635","9566220635","CUSTOMER").enqueue(new Callback<Response<List<CollegeModel>>>() {
            @Override
            public void onResponse(Call<Response<List<CollegeModel>>> call, retrofit2.Response<Response<List<CollegeModel>>> response) {
                Response<List<CollegeModel>> responseFromServer=response.body();
                if(responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)){
                    collegeAdapter.setCollegeArrayList((ArrayList<CollegeModel>) responseFromServer.getData());
                    collegeAdapter.notifyDataSetChanged();
                }else{
                    // TODO toast the message
                    Log.d("RetroFit","failure");
                }
            }

            @Override
            public void onFailure(Call<Response<List<CollegeModel>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
