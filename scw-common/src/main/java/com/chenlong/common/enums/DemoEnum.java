package com.chenlong.common.enums;

public enum DemoEnum {
    //1 构造方法是被封装的
    //2 对象是在内部创建的
    FIRST(0, "张三"),
    SECOND(1, "李四");


    private Integer id;
    private String name;

    //默认private
    private DemoEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
