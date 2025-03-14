package com.agridata.cdzhdj.net.HttpApi;

import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.data.AHIUserEXBean;
import com.agridata.cdzhdj.data.AddLoginUserBean;
import com.agridata.cdzhdj.data.AdminRegionHomeBean;
import com.agridata.cdzhdj.data.AgainEarTagBean;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.data.AppFunBean;
import com.agridata.cdzhdj.data.AppVerBean;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.CheckXdrBean;
import com.agridata.cdzhdj.data.ChuLiBean;
import com.agridata.cdzhdj.data.ChuLiDetailBean;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.agridata.cdzhdj.data.CustomerBean;
import com.agridata.cdzhdj.data.DanHaoBean;
import com.agridata.cdzhdj.data.DiseaseDictionaryBean;
import com.agridata.cdzhdj.data.DiseaseIDBean;
import com.agridata.cdzhdj.data.FaFangEarTagBean;
import com.agridata.cdzhdj.data.NewSendSmsBean;
import com.agridata.cdzhdj.data.ProvinceData;
import com.agridata.cdzhdj.data.ShowPublishBean;
import com.agridata.cdzhdj.data.ShowPublishParams;
import com.agridata.cdzhdj.data.SlaughterBeiAnBean;
import com.agridata.cdzhdj.data.Status2Bean;
import com.agridata.cdzhdj.data.StatusChangePwd;
import com.agridata.cdzhdj.data.StatusMapBean;
import com.agridata.cdzhdj.data.UserAllRoleBean;
import com.agridata.cdzhdj.data.XdrBindBean;
import com.agridata.cdzhdj.data.XdrBindListData;
import com.agridata.cdzhdj.data.entrycheck.CheckPointCertABean;
import com.agridata.cdzhdj.data.entrycheck.DaiZaiHouseBean;
import com.agridata.cdzhdj.data.entrycheck.DaiZaiHuDetailsBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckABean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckBBean;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.QueryImmuneBean;
import com.agridata.cdzhdj.data.QueryImmuneDetailsBean;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.RuKuBean;
import com.agridata.cdzhdj.data.RuKuDetailBean;
import com.agridata.cdzhdj.data.SendSmsBean;
import com.agridata.cdzhdj.data.SendStatusBean;
import com.agridata.cdzhdj.data.ShouJiBianJiBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.StatusMeBean;
import com.agridata.cdzhdj.data.ToBeCollectedBean;
import com.agridata.cdzhdj.data.ToBeCollectedDetailBean;
import com.agridata.cdzhdj.data.UpDataImmuneDetailsBean;
import com.agridata.cdzhdj.data.UpImmuneBean;
import com.agridata.cdzhdj.data.UpImmuneDetailsBean;
import com.agridata.cdzhdj.data.VaccineBean;
import com.agridata.cdzhdj.data.XdrBean;
import com.agridata.cdzhdj.data.XdrCollectListBean;
import com.agridata.cdzhdj.data.XianChangRenBean;
import com.agridata.cdzhdj.data.entrycheck.CertNoExistsBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryDetailBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogBean;
import com.agridata.cdzhdj.data.entrycheck.IsArrivedReportData;
import com.agridata.cdzhdj.data.entrycheck.IsCheckDZHBean;
import com.agridata.cdzhdj.data.entrycheck.QuarantineDeclareData;
import com.agridata.cdzhdj.data.entrycheck.SlaughterHouseBean;
import com.agridata.cdzhdj.data.entrycheck.UpECETBean;
import com.agridata.cdzhdj.data.entrycheck.UpdataAnimalABean;
import com.agridata.cdzhdj.data.entrycheck.UpdataAnimalBBean;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.data.harmless.ApplyBoHuiBean;
import com.agridata.cdzhdj.data.harmless.BindCarBean;
import com.agridata.cdzhdj.data.harmless.CarNumberBean;
import com.agridata.cdzhdj.data.harmless.CheckEartagIsusedBean;
import com.agridata.cdzhdj.data.harmless.CheckEartagIsusedNewBean;
import com.agridata.cdzhdj.data.harmless.EarTagCheckBean;
import com.agridata.cdzhdj.data.harmless.GetQueryByRoleBean;
import com.agridata.cdzhdj.data.harmless.RedDotsShowPromptsBean;
import com.agridata.cdzhdj.data.immune.EarTagIsInHistoryData;
import com.agridata.cdzhdj.data.immune.ImmuneDetails;
import com.agridata.cdzhdj.data.law.AgencyData;
import com.agridata.cdzhdj.data.law.AgencyPersonData;
import com.agridata.cdzhdj.data.law.AssignmentBean;
import com.agridata.cdzhdj.data.law.CheckTjData;
import com.agridata.cdzhdj.data.law.EnforcementData;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.data.law.ReexaminationFillingBean;
import com.agridata.cdzhdj.data.law.SpotCheckBean;
import com.agridata.cdzhdj.data.logbean.LogData;
import com.agridata.cdzhdj.data.pigbreed.XdrFeedData;
import com.agridata.cdzhdj.data.pigbreed.feed.AddFeedStorageBean;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageDetails;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageListData;
import com.agridata.cdzhdj.data.pigbreed.goods.AddGoodsBean;
import com.agridata.cdzhdj.data.pigbreed.goods.GoodsOutDetailsBean;
import com.agridata.cdzhdj.data.pigbreed.goods.GoodsOutListBean;
import com.agridata.cdzhdj.data.whh.FarmXdrInfoListByUserIdAndMobileBean;
import com.agridata.cdzhdj.data.whh.RuKuListForMidBean;
import com.agridata.cdzhdj.data.whh.ShouJiListForMidBean;
import com.agridata.cdzhdj.data.xdrbind.BindXdrUserIDForMobileBean;
import com.agridata.cdzhdj.data.xdrbind.CreateNaturalLegalBean;
import com.agridata.cdzhdj.data.xdrbind.GetUserBindFarmByMobileBean;
import com.agridata.cdzhdj.data.xdrbind.GetUserBindXdrInfoBean;
import com.agridata.cdzhdj.data.xdrbind.LegalPersonSuccessBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author xushibin
 * @date 12/22/21
 * description：
 */
public interface Api {


    static String getUserID() {
        return UserDataSP.getInstance().getUserInfo().Result.userId;
    };


    @POST("sms/code/send")
    Observable<StatusData> getSendSms(@Body Map<String, String> params);

    /**
     * 验证短信是否正确
     * @param params
     * @return
     */
   
    @POST("sms/code/verify")
    Observable<StatusData> verifySms(@Body Map<String, String> params);


    /**
     * 修改密码
     * @param params
     * @return
     */
    @POST("ahiapi/ahiUser/updatePasswordAndTel")
    Observable<StatusChangePwd> updatePassword (@QueryMap Map<String, String> params);


    @POST("user/code/login")
    Observable<LoginData> getLogin(@Body Map<String, String> params);



   
    @POST("user/logout")
    Observable<StatusData> getLogout(@Body Map<String, String> params);

    /**
     * 获取单号
     * @param
     * @return
     */
    @GET("api/getHarmlessNo?notype=SJ")
    Observable<DanHaoBean> getDanHao();



    /**
     * 提交收集信息
     * @param collectInfoData
     * @return
     */
    @POST("api/checkInCar")
    Observable<StatusMeBean> getCollect(@HeaderMap Map<String,String> header, @Body CollectInfoData collectInfoData);


    /**
     * 上傳圖片
     * @param
     * @return
     */
    @Multipart
   
    @POST("api/file/upload?appid=d5896b31964e425382df52f655dedfc2")
    Observable<ImgBean> upLoadImg(@Part MultipartBody.Part photo);



    @Multipart
   
    @POST("api/file/batchUpload?appid=d5896b31964e425382df52f655dedfc2")
    Observable<BatchImgBean>  BatchUploadImg(@Part List<MultipartBody.Part> images);

    /**
     * 登录
     * @param params
     * @return
     */
    @POST("users/addLoginUser")
    Observable<AddLoginUserBean> addLoginUser(@Body Map<String, String> params);

    /**
     * 获取用户全权限
     * @param params
     * @return
     */
    @POST("users/queryAuth")
    Observable<RoleBean> queryAuth (@Body Map<String, String> params);


   
    @POST("objects/query/all?categoryId=500953fc8fc84369a36d2ce2b5f87496&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<AppVerBean> getAppVer(@Body Map<String, String> params);


    /**
     * 编辑
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<ShouJiBianJiBean> getBianJi(@QueryMap Map<String, String> params);

    //http://sxxc.8918.biz/v3.0/api/data/delete?mid=9aa54c18127440f7af1cb55a75395454&force=false&categoryId=2d2498157f95465eb531dfe429407479&userid=9f4cb3017b314889a230f6140543e423&cst=1

    /**
     * 获取单号
     * @param params
     * @return
     */
   
    @GET("data/delete")
    Observable<StatusData> delInfo(@QueryMap Map<String, String> params);


    /**
     * 获取无害化处理动物
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=0924d8de879d4ac297b41058777797fb")
    Observable<HarmlessAnimalBean> getHarAnimal(@Body HashMap<String,Object> params);


    /*
     获取现场监督员
     */
    @POST("users/getXianChangUser")
    Observable<XianChangRenBean> getXianChangJianDuRen(@Body HashMap<String,String> params);


   
    @POST("sms/trans/api/saas/dynamic?template=7602703b9ab24dd6bcd2c44a2847c4f1")
    Observable<SendStatusBean> sendTiShiDuanXin(@Body SendSmsBean sendSmsBean);


    /**
     * 获取区划管理员待收集列表
     * @param params
     * @return
     */
   
    @POST("categories/aa5fb811a9d34b559db2f8144a225f99/datas/page?partitionId=d5896b31964e425382df52f655dedfc2&sf=3&sd=1")
    Observable<ToBeCollectedBean> getToBeCollected(@Body HashMap<String,Object> params);
    /**
     * 上傳圖片
     * @param
     * @return
     */
    @Multipart
   
    @POST("user/avatar")
    Observable<ImgBean> upLoadHead(@QueryMap Map<String, String> params,@Part MultipartBody.Part photo);



    /**
     * 获取待收集详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<ToBeCollectedDetailBean> getToBeCollectedDetail(@QueryMap Map<String, String> params);



    /**
     * 新版区划管理员首页四个统计
     * @param params
     * @return
     */
    @POST("api/tongjiByRegionAndDatetime")
    Observable<AdminRegionHomeBean> getAdminRegionHome(@Body Map<String, Object> params);


    /**
     * 获取区划管理员待收集列表
     * @param params
     * @return
     */
   
    @POST("categories/2d2498157f95465eb531dfe429407479/datas/page?partitionId=d5896b31964e425382df52f655dedfc2&sf=3&sd=1")
    Observable<CollectedBean> getCollected(@Body HashMap<String,Object> params);




    /**
     * 获取已收集详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<CollectedDetailBean> getCollectedDetail(@QueryMap Map<String, String> params);





    /**
     * 获取区划管理员入库列表
     * @param params
     * @return
     */
   
    @POST("categories/55981cda99de4cf0936233a49ebcd207/datas/page?partitionId=d5896b31964e425382df52f655dedfc2&sf=3&sd=1")
    Observable<RuKuBean> getRuKu(@Body HashMap<String,Object> params);




    /**
     * 获取已收集详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<RuKuDetailBean> getRuDetail(@QueryMap Map<String, String> params);



    /**
     * 获取区划管理员处理列表
     * @param params
     * @return
     */
   
    @POST("categories/7b5a12a60807492a9b218dae47207673/datas/page?partitionId=d5896b31964e425382df52f655dedfc2&sf=3&sd=1")
    Observable<ChuLiBean> getChuLi(@Body HashMap<String,Object> params);


    /**
     * 获取处理详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<ChuLiDetailBean> getCLDetail(@QueryMap Map<String, String> params);



    /**
     * 获取养殖户列表
     * @param params
     * @return
     */
    @POST("api/appCommand")
    Observable<XdrBean> getXdrList (@Body Map<String, Object> params);



    /**
     * 获取养殖户列表
     * @param params
     * @return
     */
    @POST("api/appCommand")
    Observable<XdrCollectListBean> getXdrCollectList (@Body Map<String, Object> params);



    /**
     * 账号密码登录
     * @param params
     * @return
     */


    @GET("user/login")
    Observable<LoginData> login (@QueryMap Map<String, String> params);



    /**
     * 获取动物种类
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=0924d8de879d4ac297b41058777797fb")
    Observable<ImmuneAnimalBean> getImmuneAnimal(@Body HashMap<String,Object> params);



    /**
     * 获取免疫畜主列表
     * @param params
     * @return
     */
   
    @POST("categories/2dc9a1fc321046d28511b0739d850445/datas/page?partitionId=d5896b31964e425382df52f655dedfc2&sf=3&sd=1")
    Observable<ImmuneXdrBean> getImmuneXdr(@Body HashMap<String,Object> params);





    /**
     * 获取免疫畜主列表
     * @param params
     * @return
     */
   
    @POST("objects/query/page?categoryId=9b3e8446f8f44f8a926b690dbac5cad1&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<FaFangEarTagBean> getCheckEarTagNum(@Body HashMap<String,Object> params);




    /**
     * 获取动物所关联的疫病
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=b238b63e90454ef6bbc31fb295687639&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<DiseaseIDBean> getAnimalToDiseaseID(@Body HashMap<String,Object> params);



    /**
     * 获取疫病字典
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=019fbef683f24081968a1a0a9af566f3&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<DiseaseDictionaryBean> getDiseaseDictionary(@Body HashMap<String,Object> params);



    /**
     * 获取疫苗
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=bb42fc4cc15d4fd79e1e8550b276361b&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<VaccineBean> getVaccineInfo(@Body HashMap<String,Object> params);


    public
    /**
     * 提交免疫详情
     * @param upImmuneDetailsBean
     * @return
     */
   
    @POST
    Observable<StatusData> UpLoadImmuneDetails(@Url String url, @Body UpImmuneDetailsBean upImmuneDetailsBean);

    /**
     * 提交收集信息
     * @param upImmuneBean
     * @return
     */
   
    @POST
    Observable<StatusData> UpLoadImmune(@Url String url, @Body UpImmuneBean upImmuneBean);



    /**
     * 获取免疫信息（查询）
     * @param params
     * @return
     */
   
    @POST("objects/query/page?categoryId=d0559e21622549148de9911439202147&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<QueryImmuneBean> getHistoryImmune(@Body HashMap<String,Object> params);




    /**
     * 获取免疫信息（查询）
     * @param params
     * @return
     */
   
    @POST("objects/query/first?categoryId=a7126bcc198a41228172b4c97315411b&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<QueryImmuneDetailsBean> getHistoryImmuneDetails(@Body HashMap<String,Object> params);


    /**
     * 修改免疫信息
     * @param upImmuneDetailsBean
     * @return
     */
   
    @POST
    Observable<StatusData> UpDataImmuneDetails(@Url String url, @Body UpDataImmuneDetailsBean upImmuneDetailsBean);

    /**
     * 获取免疫信息（查询）
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=d0559e21622549148de9911439202147&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<AgainEarTagBean> getAgainEarTag(@Body HashMap<String,Object> params);




    /**
     * 获取B证详细信息
     * @param params
     * @return
     */
   
    @POST("objects/query/first?categoryId=44be5d010ab14aaaa99383eb72875145&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<EntryCheckBBean> getECAB(@Body HashMap<String,Object> params);


    /**
     * 提交入场查验信息
     * @param upECETBean
     * @return
     */
   
    @POST
    Observable<StatusData> UpDataECET(@Url String url, @Body UpECETBean upECETBean);





    /**
     * 获取入场查验表是否存在该条检疫证号入场的信息
     * @param params
     * @return
     */
   
    @POST("objects/query/first?categoryId=e4ce92598ac54a06ae03d3c597f506be&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<CertNoExistsBean> getCertNoExists(@Body HashMap<String,Object> params);




    /**
     * 提交入场查验信息
     * @param upECETBean
     * @return
     */
   
    @POST
    Observable<StatusData> upDataEntryCheck(@Url String url, @Body UpECETBean upECETBean);




    /**
     * 获取A证详细信息
     * @param params
     * @return
     */
   
    @POST("objects/query/first?categoryId=7f346e9b82414eb2bf1f3126906abe29&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<EntryCheckABean> getECAA(@Body HashMap<String,Object> params);



    /**
     * 更新B证回收
     * @param updataAnimalBBean
     * @return
     */
   
    @POST
    Observable<StatusData> upDataAnimalB(@Url String url, @Body UpdataAnimalBBean updataAnimalBBean);


    /**
     * 更新B证回收
     * @param updataAnimalABean
     * @return
     */
   
    @POST
    Observable<StatusData> upDataAnimalA(@Url String url, @Body UpdataAnimalABean updataAnimalABean);




    /**
     * 获取appFun
     * @param params
     * @return
     */
   
    @GET("appfunc/get")
    Observable<AppFunBean> getFunAppInfo(@QueryMap Map<String, String> params);


    /**
     * 获取appFun
     * @param params
     * @return
     */
   
    @GET("out/l1/call/b2a39674cf884f6faf8f22d0d8a3c80a/all")
    Observable<CustomerBean> getCustomerInfo(@QueryMap Map<String, String> params);


    /**
     * 获取关联的屠宰场备案
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=3ee0b669d0024c7eb51f1f423fd2f6f1&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<SlaughterBeiAnBean> getSlaughterBei (@Body HashMap<String,Object> params);



    /**
     * 获取该用户绑定的相对人列表
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=28764ad161434828a93fe12dde9936f5&partitionId=0b1d178c499043a2aeeef591a3d8f03d")
    Observable<XdrBindBean> getXdrBind (@Body HashMap<String,Object> params);


    /**
     *
     * @param getUserBindFarmByMobileBean
     * @return
     */
    @POST("apiCommand")
    Observable<XdrBindListData> getXdrBindInfo (@Body GetUserBindFarmByMobileBean getUserBindFarmByMobileBean);



    /**
     *  todo:// 获取模糊查询手机号+UserID，备案信息   2023-12-26
     * @param params
     * @return
     */
    @POST("api/getFarmXdrInfoListByUserIdAndMobile")
    Observable<FarmXdrInfoListByUserIdAndMobileBean> getXdrBindInfoNew (@Body Map<String,Object> params);


    /**
     *
     * @param
     * @return
     */
    @POST("api/getFarmXdrInfoListByUserIdAndMobile")
    Observable<XdrBindListData> getXdrBindList (@Body Map<String,Object> params);



    /**
     *
     *  获取模糊查询手机号+UserID，备案信息  TODO:2023-12-26 修改新版无害化相对人列表
     * @param mGetUserBindXdrInfoBean
     * @return
     */
    @POST("api/getFarmXdrInfoListByUserIdAndMobile")
    Observable<XdrBindListData> getXdrBindListNew (@Body GetUserBindXdrInfoBean mGetUserBindXdrInfoBean);

    /**
     *
     * @param createNaturalLegalBean
     * @return
     */
    @POST("apiCommand")
    Observable<LegalPersonSuccessBean> CreateNaturalLegal (@Body CreateNaturalLegalBean createNaturalLegalBean);



    @POST("apiCommand")
    Observable<StatusMeBean> BindXdrUserIDForMobile (@Body BindXdrUserIDForMobileBean bindXdrUserIDForMobileBean);



    /**
     *
     * @param params
     * @return
     */
    @POST("farm/region/getRegion")
    Observable<ProvinceData> getRegionInfo (@Body Map<String,Object> params);



    /**
     * 获取个人信息
     * @param params
     * @return
     */
   
    @GET("user/getUserInfoByUserId")
    Observable<LoginData> getUserInfo (@QueryMap Map<String, String> params);




    /**
     * 获取执法机构信息
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=e5f8713e1c9c4cec818e6bee7b083c9d&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<AgencyData> getAgencyData (@Body Map<String, Object> params);


    /**
     * 获取执法机构人员信息
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=75c0a2b2731a40b8ae52c301af5274af&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<AgencyPersonData> getAgencyPersonData (@Body Map<String, Object> params);




    /**
     * 提交执法检查信息填报页面
     * @param enforcementData
     * @return
     */
   
    @POST
    Observable<StatusData> addDataEnforcementData(@Url String url, @Body EnforcementData enforcementData);



    /**
     * 获取执法检查 数据
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=78c21f0133c6471bb3d6fa5421020137&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<EnforcementHomeData> getEnforcementList(@Body HashMap<String,Object> params);


    /**
     * 通过人去查询用户表的mid
     */
   
    @POST("objects/query/first?categoryId=75c0a2b2731a40b8ae52c301af5274af&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<AHIUserEXBean> getAHIUserEXMid(@Body HashMap<String,Object> params);


    /**
     * 获取指派的数据
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=78c21f0133c6471bb3d6fa5421020137&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<EnforcementHomeData> getAssignList(@Body HashMap<String,Object> params);



    /**
     * 提交执法检查信息填报页面
     * @param assignmentBean
     * @return
     */
   
    @POST
    Observable<StatusData> updataAssignment(@Url String url, @Body AssignmentBean assignmentBean);



    /**
     * 获取执法检查填报详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<EnforcementDetailBean> getEnforcementDetails(@QueryMap Map<String, String> params);



    /**
     * 提交执法检查信息填报页面
     * @param reexaminationFillingBean
     * @return
     */
   
    @POST
    Observable<StatusData> updataReviewCheck(@Url String url, @Body ReexaminationFillingBean reexaminationFillingBean);


    /**
     * 提交执法检查信息填报页面
     * @param spotCheckBean
     * @return
     */
   
    @POST
    Observable<StatusData> updataChouCha(@Url String url, @Body SpotCheckBean spotCheckBean);



    /**
     * 获取免疫畜主列表
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=2dc9a1fc321046d28511b0739d850445&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<XdrFeedData> getMobileXdr(@Body HashMap<String,Object> params);



    /**
     * 提交饲料入库
     * @param addFeedStorageBean
     * @return
     */
   
    @POST
    Observable<StatusData> addFeed(@Url String url, @Body AddFeedStorageBean addFeedStorageBean);



   
//    @POST("sms/trans/api/saas/dynamic?template=7602703b9ab24dd6bcd2c44a2847c4f1")
    @POST("sms/trans/api/trans/science")
    Observable<SendStatusBean> sendWHHSms(@Body NewSendSmsBean sendSmsBean);


    /**
     * 获取饲料入库list
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=5bfa949f6e294b4e80474fd57dec3d24&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<FeedStorageListData> getFeedStorageList(@Body Map<String, Object> params);



    /**
     * 获取饲料投入品详情
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<FeedStorageDetails> getFeedStorageDetails(@QueryMap Map<String, String> params);


//   
    @POST
   
    Observable<StatusMapBean> addGoodsOut(@Url String url, @Body AddGoodsBean addGoodsBean);


   
    @POST("objects/query/all?categoryId=d7546d00b04d4a3fac175773d0afbe36&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<GoodsOutListBean> getGoodsList(@Body Map<String, Object> params);

    /**
     * 获取物品出库详细
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<GoodsOutDetailsBean> getGoodsOutDetails(@QueryMap Map<String, String> params);


    /**
     *  更新 相对人审核状态
     * @param url
     * @param checkXdrBean
     * @return
     */
   
    @POST
    Observable<StatusData> checkXdrStatus(@Url String url, @Body CheckXdrBean checkXdrBean);



    /**
     * 获取屠宰场详细
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<SlaughterHouseBean> getSlaughterHouseDetails(@QueryMap Map<String, String> params);


    @GET("farm/enforcement/enforceTongji")
    Observable<CheckTjData> getCheckTj();


    /**
     * 收集上传照片日志接口
     * @param url
     * @param logData
     * @return
     */
    @POST
   
    Observable<StatusData> addCollectionxlog(@Url String url, @Body LogData logData);



    /**
     * 获取入场查验留符合的历史记录（查询）
     * @param params
     * @return
     */
   
    @POST("objects/query/page?categoryId=e4ce92598ac54a06ae03d3c597f506be&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<EntryCheckHistoryData> getHistoryEntryCheck(@Body HashMap<String,Object> params);


    /**
     * 获取入场查验详细
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<EntryCheckHistoryDetailBean> getHistoryEntryCheckDetails(@QueryMap Map<String, String> params);




    /**
     * 获取带宰户列表
     * @param params
     * @return
     */
    @POST("ahiapi/RZT/GetDZHList")
    Observable<DaiZaiHouseBean> getDaiZai (@Body Map<String, String> params);



    /**
     * 获取该屠宰场是否有待宰户权限
     * @param params
     * @return
     */
    @POST("ahiapi/RZT/IsCheckDZH")
    Observable<IsCheckDZHBean> getIsCheckDZH(@Body Map<String, String> params);



    /**
     * 上传带宰户List
     * @param params
     * @return
     */
    @POST("ahiapi/RZT/AddCheckDZHList")
    Observable<IsCheckDZHBean> AddCheckDZHList(@Body Map<String, Object> params);



    /**
     * 获取带宰户详细
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=20bda9f07230420b87ecb9ed48db49cf&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<DaiZaiHuDetailsBean> getDZHDetails(@Body HashMap<String,Object> params);



    @POST("ahiapi/BaseInfo/sendWxMessage")
    Observable<Status2Bean> sendWxMessage(@Body Map<String, Object> params);


    /**
     * 通过入库的Mid 获取到详细
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=55981cda99de4cf0936233a49ebcd207&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<RuKuListForMidBean> getRuKuForMidList(@Body HashMap<String,Object> params);


    /**
     * 通过收集的Mid 获取到详细
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=2d2498157f95465eb531dfe429407479&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<ShouJiListForMidBean> getShouJiForMidList(@Body HashMap<String,Object> params);


    /**
     * 申报列表
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=aa5fb811a9d34b559db2f8144a225f99&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<ApplyBean> getApply(@Body HashMap<String,Object> params);



    /**
     * 获取车辆列表
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=3cb15a5f037949779533b7596e03a46d&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<CarNumberBean> getCarNumberInfo(@Body HashMap<String,Object> params);



    /**
     * 出车
     * @param bindCarBean
     * @return
     */
   
    @POST
    Observable<StatusData> upDataCar(@Url String url, @Body BindCarBean bindCarBean);



    /**
     * 驳回
     * @param applyBoHuiBean
     * @return
     */
   
    @POST
    Observable<StatusData> applyBoHui(@Url String url, @Body ApplyBoHuiBean applyBoHuiBean);



    /**
     *
     * @param checkEartagIsusedBean
     * @return
     */
    @POST("apiCommand")
    Observable<StatusMeBean> checkEartagIsused (@Body CheckEartagIsusedBean checkEartagIsusedBean);




    /**
     *
     * @param getQueryByRoleBean
     * @return
     */
    @POST("api/GetQueryByRole")
    Observable<RedDotsShowPromptsBean> getQueryByRole (@Body GetQueryByRoleBean getQueryByRoleBean);



   
    @GET("role/all")
    Observable<UserAllRoleBean> getUserRole(@QueryMap Map<String, String> params);




    /**
     *查询耳标是否使用,是否开证
     * @param checkEartagIsusedNewBean
     * @return
     */
    @POST("apiCommand")
    Observable<EarTagCheckBean> checkEartagIsusedNew (@Body CheckEartagIsusedNewBean checkEartagIsusedNewBean);


    /**
     *
     * @param showPublishParams
     * @return
     */
    @POST("ahiapi/apiCommand")
    Observable<ShowPublishBean> getShowPublish (@Body ShowPublishParams showPublishParams);




    /**
     * 修改入场查验信息
     * @param params
     * @return
     */
   
    @POST
    Observable<StatusData> mModifyAdmissionCheck(@Url String url, @Body Map<String,Object> params);



    /**
     * 提交入场查验修改日志记录
     * @param entryCheckLogBean
     * @return
     */
   
    @POST
    Observable<StatusData> upDataEntryCheckLog(@Url String url, @Body EntryCheckLogBean entryCheckLogBean);



    /**
     * 申报列表
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=af236cb2dd104532bb9d02f54de5cccf&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<EntryCheckLogData> getEntryCheckLog(@Body HashMap<String,Object> params);


    /**
     * 通过时间获取屠宰场是否屠宰检疫出证
     * @param params
     * @return
     */
    @POST("queryapi/query/getQuarantineDeclare")
    Observable<QuarantineDeclareData> getQuarantineDeclare( @Body Map<String,Object> params);



    /**
     * 查验耳标是否在库存
     * @param earCodes
     * @return
     */
    @POST("ahiapi/immune/queryEartagIsInHistoryNew")
    Observable<EarTagIsInHistoryData> queryEarTagIsInHistory(@Body List<Map<String, String>> earCodes);


    /**
     * 获取动物A证入场查验是否经过指定通道
     * @param params
     * @return
     */
   
    @POST("objects/query/all?categoryId=b8dce497b6fe4b89a6dc480de17394f6&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<CheckPointCertABean> getCheckPointCertA(@Body Map<String,Object> params);


    /**
     * 查询自己跟防疫员免疫的数据
     * @param params
     * @return
     */
   
    @POST("objects/query/page?categoryId=d0559e21622549148de9911439202147&partitionId=d5896b31964e425382df52f655dedfc2")
    Observable<QueryImmuneBean> getImmuneBranch(@Body Map<String,Object> params);



    /**
     * 获取免疫详细
     * @param params
     * @return
     */
   
    @GET("data/get")
    Observable<ImmuneDetails> getImmuneDetails(@QueryMap Map<String, String> params);



    /**
     * 修改免疫信息补戴耳标
     * @param
     * @return
     */
   
    @POST
    Observable<StatusData> commitImmunePatchEarTagCode(@Url String url,@Body Map<String,String> params);


    /**
     * 修改免疫信息补戴耳标
     * @param
     * @return
     */
   
    @POST
    Observable<StatusData> commitArriveInfo(@Url String url, @Body IsArrivedReportData isArrivedReport);


    @POST("ahiapi/BaseInfo/globalConfigLocal")
    Observable<AppConfigurationBean> getAppConfiguration();
}
