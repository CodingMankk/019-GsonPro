package com.codingmankk.www.gsonpro.Entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * ================================================
 * 版    本：
 * 创建日期：
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Category {
    @Expose
    public int id;
    @Expose  public String name;
    @Expose  public List<Category> children;
    //parent 字段是因业务需要增加的，那么在序列化是并不需要，所以在序列化 时就必须将其排除
    //不需要序列化,所以不加 @Expose 注解
    //等价于 @Expose(deserialize = false,serialize = false)
    public Category parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
