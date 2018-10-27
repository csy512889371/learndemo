package com.ctoedu.business.module.course;


import com.ctoedu.business.module.BaseModel;

public class CourseCommentValue extends BaseModel {

    public String text;
    public String name;
    public String logo;
    public int type;
    public String userId; //评论所属用户ID
}
