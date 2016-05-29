package com.jingoteam.ngocphong.miniproject2_v1;

import java.util.Date;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class Task {
    private String content;
    private Date createdDate;

    public Task(String content, Date createdDate){
        this.content = content;
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
