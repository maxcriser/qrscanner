package com.github.maxcriser.qrscanner.async;

public interface Task<Params, Progress, Result> {

    Result doInBackground(Params pParams, ProgressCallback<Progress> pProgressProgressCallback) throws Exception;

}