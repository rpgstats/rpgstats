package com.nsu.rpgstats.data.parameters;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.entities.Parameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlugParameterRepository implements ParameterRepository {
    private HashMap<Integer, Parameter> parameters; // <system, param>
    private MutableLiveData<List<Parameter>> mutableLiveData = new MutableLiveData<>();
    private Integer currentId;

    public PlugParameterRepository() {
        parameters = new HashMap<>();
        generateParameterList();
        currentId = 124;
    }

    private void generateParameterList() {
        parameters.put(117, new Parameter(117, "Attack", new Date(), 0, 993));
        parameters.put(118, new Parameter(118, "Health points", new Date(), 1, 994));
        parameters.put(119, new Parameter(119, "Mana points", new Date(), 2, 995));
        parameters.put(120, new Parameter(120, "Defense", new Date(), 3, 996));
        parameters.put(121, new Parameter(121, "Strength", new Date(), 4, 997));
        parameters.put(122, new Parameter(122, "Agility", new Date(), 5, 998));
        parameters.put(123, new Parameter(123, "Intelligence", new Date(), 6, 999));
    }

    @Override
    public LiveData<List<Parameter>> getParameters(int gameSystem) {
        mutableLiveData.postValue(new ArrayList<Parameter>(parameters.values()));
        return mutableLiveData;
    }

    @Override
    public Parameter getParameter(int gameSystemId, int id) {
        return parameters.get(id);
    }

    @Override
    public int addParameter(int gameSystem, Parameter parameter) {
        Parameter p = new Parameter(currentId, parameter.getName(), new Date(),
                parameter.getMin(), parameter.getMax());
        parameters.put(currentId, p);
        mutableLiveData.postValue(new ArrayList<Parameter>(parameters.values()));
        return currentId++;
    }

    @Override
    public void editParameter(int gameSystem, int id, Parameter parameter) {
        Parameter p = new Parameter(id, parameter.getName(), parameter.getCreatedAt(),
                parameter.getMin(), parameter.getMax());
        parameters.remove(id);
        parameters.put(id, p);
        mutableLiveData.postValue(new ArrayList<Parameter>(parameters.values()));
        Log.i("TAG", "params changed in repo");
    }

    public void removeParameter(int gameSystem, int id){
        parameters.remove(id);
        mutableLiveData.postValue(new ArrayList<Parameter>(parameters.values()));
    }

}
