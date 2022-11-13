package com.nsu.rpgstats.data;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
