package com.nsu.rpgstats.data.parameters;

import androidx.lifecycle.LiveData;

import com.nsu.rpgstats.entities.Parameter;

import java.util.List;

public interface ParameterRepository {
    LiveData<List<Parameter>> getParameters(int gameSystem);

    Parameter getParameter(int gameSystemId, int id);

    int addParameter(int gameSystem, Parameter parameter);

    void editParameter(int gameSystem, int id, Parameter parameter);

    void removeParameter(int gameSystem, int id);
}
