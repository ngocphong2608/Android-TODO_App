package com.jingoteam.ngocphong.miniproject2_v1;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class Task implements java.io.Serializable{
    private String content;
    private DateTime createdDate;

    public Task(String content, DateTime createdDate){
        this.content = content;
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }
}
