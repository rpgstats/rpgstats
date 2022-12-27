package com.nsu.rpgstats.viewmodel.parameters;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.parameters.ParameterRepository;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.user.AuthInfo;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.entities.user.User;
import com.nsu.rpgstats.ui.parameters.ManageListener;
import com.nsu.rpgstats.ui.user.AuthListener;

import java.util.Date;
import java.util.List;

public class ParameterManageViewModel extends AndroidViewModel {
    private static final String TAG = ParameterManageViewModel.class.getSimpleName();

    private Integer id = 0;
    private String name;
    private Integer min;
    private Integer max;

    private final ParameterRepository mParamRepository;
    private MediatorLiveData<List<Parameter>> liveData;

    public ParameterManageViewModel(Application app) {
        super(app);
        this.mParamRepository = ((RpgstatsApplication)app).appContainer.parameterRepository;
        liveData = new MediatorLiveData<>();

        liveData.postValue(mParamRepository.getParameters(0).getValue());
        liveData.addSource(mParamRepository.getParameters(0), liveData::postValue);
    }

    public void onAddClick() {
        //TODO validate
        Parameter p = new Parameter(id, name, new Date(), min, max);
        mParamRepository.addParameter(0, p);

    }

    public void onEditClick() {
        //TODO validate
        Parameter p = new Parameter(id, name, new Date(), min, max);
        mParamRepository.editParameter(0, id, p);

    }

    public LiveData<List<Parameter>> getParams(){
        return liveData;
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setMin(Integer min) {this.min = min;}

    public void setMax(Integer max) {this.max = max;}

    public void setId(Integer id) {this.id = id;}
}
