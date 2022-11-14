package com.nsu.rpgstats.data.parameters;

import com.nsu.rpgstats.entities.Parameter;

import java.util.List;

public interface ParameterRepository {
    List<Parameter> getParameters(int gameSystem);

    Parameter getParameter(int gameSystemId, int id);

    int addParameter(int gameSystem, Parameter parameter);

    void editParameter(int gameSystem, int id, Parameter parameter);
}
