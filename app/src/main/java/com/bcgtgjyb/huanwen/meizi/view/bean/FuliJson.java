package com.bcgtgjyb.huanwen.meizi.view.bean;

import java.util.List;

/**
 * Created by huanwen on 2015/9/5.
 */
public class FuliJson {
    private boolean error;
    private List<FuliDetil> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<FuliDetil> getResults() {
        return results;
    }

    public void setResult(List<FuliDetil> results) {
        this.results = results;
    }



}
