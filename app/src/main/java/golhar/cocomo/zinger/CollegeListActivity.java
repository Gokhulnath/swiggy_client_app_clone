package golhar.cocomo.zinger;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import golhar.cocomo.zinger.adapter.CollegeListAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class CollegeListActivity extends AppCompatActivity {
    RecyclerView itemListRV;
    CollegeListAdapter collegeAdapter;
    EditText collegeName;
    ArrayList<CollegeModel> collegeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        collegeName = findViewById(R.id.collegeNameET);
        collegeAdapter = new CollegeListAdapter(new ArrayList<>(), this);
        itemListRV = findViewById(R.id.itemListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        itemListRV.setLayoutManager(linearLayoutManager);
        itemListRV.setAdapter(collegeAdapter);
        collegeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<CollegeModel> modifiedCollegeList = (ArrayList<CollegeModel>) collegeList
                        .stream()
                        .filter(collegeModel -> collegeModel.getName().toLowerCase().contains(s.toString().toLowerCase()))
                        .collect(Collectors.toList());
                collegeAdapter.setCollegeArrayList(modifiedCollegeList);
                collegeAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //todo shared pref name change to constant DONE G
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authid = SharedPref.getString(getApplicationContext(),Constants.authId);
        MainRepository.getCollegeService().getAllColleges(authid, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<CollegeModel>>>() {
            @Override
            public void onResponse(Call<Response<List<CollegeModel>>> call, retrofit2.Response<Response<List<CollegeModel>>> response) {
                Response<List<CollegeModel>> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    collegeAdapter.setCollegeArrayList((ArrayList<CollegeModel>) responseFromServer.getData());
                    collegeAdapter.notifyDataSetChanged();
                    collegeList = (ArrayList<CollegeModel>) responseFromServer.getData();
                } else {
                    Toast.makeText(CollegeListActivity.this, responseFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<List<CollegeModel>>> call, Throwable t) {
                Toast.makeText(CollegeListActivity.this, "Unable to reach the server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
