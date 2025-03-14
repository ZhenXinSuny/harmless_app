package com.agridata.cdzhdj.utils;

import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.network.BuildConfig;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 10:04.
 * @Description :描述
 */
public class UrlPostUtils {

    public  static String UpLoadImmuneDetails = "data/add?categoryId=a7126bcc198a41228172b4c97315411b&partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;



    public  static String UpLoadImmune = "data/add?categoryId=d0559e21622549148de9911439202147&partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;




    public  static String UpDataImmuneDetails = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;




    public  static String UpDateECET = "data/add?category=7EFF7BD6B670DEA1-T_Entry_Check&partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public static  String UpDataEntryCheck = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public static  String UpDataEntryCheckLog = "data/add?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    //更新动物B证回收状态
    public static  String HUISHOUANIMALB = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public static  String HUISHOUANIMALA = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;

    //提交用户跟相对人绑定接口
    public  static String AddUserXdrBind = "data/add?categoryId=28764ad161434828a93fe12dde9936f5&partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    //修改养殖户动物
   // public  static String AddUserXdrBind = "data/add?categoryId=28764ad161434828a93fe12dde9936f5&partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;

    //提交执法检查信息填报
    public static  String UpDataLAW = "data/add?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    //修改执法检查数据 指派机构或者人员
    public static  String UpDataAssignment = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


   public static  String UpDataFeedAdd = "data/add?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;

    public static  String UpDataFeed = BuildConfig.baseUrlAhiapi + "/inputGoods/use";


    public static  String AddCollectionXLog = "data/add?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public static  String GOCAR = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public static  String APPLYBOHUI = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d&cst=1" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;


    public  static String commitArriveInfoUrl = "data/update?partitionId=0b1d178c499043a2aeeef591a3d8f03d" + "&userid=" + UserDataSP.getInstance().getUserInfo().Result.userId;
}
