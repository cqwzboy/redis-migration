package com.qc.itaojin.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class ScanResult<T> {
    private int cursor;
    private List<T> results;

    ScanResult(){}

    public ScanResult(int cursor, List<T> results){
        this.cursor = cursor;
        this.results = results==null?new ArrayList<>():results;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScanResult{");
        sb.append("cursor=").append(cursor);
        sb.append(", results=").append(results);
        sb.append('}');
        return sb.toString();
    }
}
