package com.chenlong.project.vo.resp;

import com.chenlong.project.pojo.TProjectImages;
import com.chenlong.project.pojo.TProjectTag;
import com.chenlong.project.pojo.TProjectType;
import com.chenlong.project.pojo.TReturn;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailVo {

    private Integer id; //项目id

    private String name; //项目名称

    private String remark; //项目简介

    private Long money; //筹资金额

    private Integer day; //筹资天数

    private String status;  //状态

    private Integer memberid;  //发起人id
    private String createdate;  //创建日期

    private String deploydate;//发布日期

    private Long supportmoney = 0L;//支持金额
    private Integer supporter = 0;//支持者数量
    private Integer completion = 0;//完成度
    private Integer follower = 100;//关注者数量

    //项目回报信息
    private List<TReturn> returns;
    //项目分类信息
    private List<TProjectType> types;
    //项目标签
    private List<TProjectTag> tags;
    // 项目头部图片
    private String headerImage;
    // 项目详情图片
    private List<TProjectImages> images;

}
