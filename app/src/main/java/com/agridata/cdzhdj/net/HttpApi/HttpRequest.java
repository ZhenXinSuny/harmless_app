package com.agridata.cdzhdj.net.HttpApi;

import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;

import com.agridata.cdzhdj.SPUtil.RoleSP;
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
import com.agridata.cdzhdj.data.entrycheck.EntryCheckDZHBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryDetailBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogData;
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
import com.agridata.cdzhdj.data.xdrbind.LegalPersonSuccessBean;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.ConstantUtils;
import com.agridata.cdzhdj.utils.UrlPostUtils;
import com.agridata.network.HttpCall;
import com.agridata.network.RetrofitHelper;
import com.agridata.network.listener.CallBackLis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author xushibin
 * @date 12/22/21
 * description：
 * getRetrofit(1) 1 为万备 2为自己的接口  3上传图片接口
 */
public class HttpRequest {
    /**
     * 獲取驗證碼
     *
     * @param owner
     * @param mobile
     * @param callBackLis
     */
    public static void getSendSms(LifecycleOwner owner, String mobile, CallBackLis<StatusData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("partId", ConstantUtils.PartId);
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getSendSms(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 验证短信是否正确
     *
     * @param owner
     * @param mobile
     * @param code
     * @param callBackLis
     */
    public static void verifySms(LifecycleOwner owner, String mobile, String code, CallBackLis<StatusData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("partId", ConstantUtils.PartId);
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).verifySms(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 修改密码
     *
     * @param owner
     * @param authinfo
     * @param callBackLis
     */
    public static void updatePassword(LifecycleOwner owner, String authinfo, CallBackLis<StatusChangePwd> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("baseUrl", authinfo);
        Observable<StatusChangePwd> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).updatePassword(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 登錄接口
     *
     * @param owner
     * @param mobile
     * @param code
     * @param callBackLis
     */
    public static void getLogin(LifecycleOwner owner, String mobile, String code, CallBackLis<LoginData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("partitionId", ConstantUtils.PartId);
        Observable<LoginData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getLogin(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 退出登录
     *
     * @param owner
     * @param callBackLis
     */
    public static void getLogout(LifecycleOwner owner, String userId, CallBackLis<StatusData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("partitionId", ConstantUtils.PartId);
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getLogout(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取单号
     *
     * @param owner
     * @param callBackLis
     */
    public static void getDanHao(LifecycleOwner owner, CallBackLis<DanHaoBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("notype", "SJ");
        Observable<DanHaoBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getDanHao();
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 上传
     *
     * @param owner
     * @param collectInfoData
     * @param callBackLis     添加Headers  07-10 2024
     */
    public static void getCollect(LifecycleOwner owner, CollectInfoData collectInfoData, CallBackLis<StatusMeBean> callBackLis) {
        Map<String, String> mapHeaders = new HashMap<>();
        String times = String.valueOf(System.currentTimeMillis());
        mapHeaders.put("Idempotency-Key", UserDataSP.getInstance().getUserInfo().Result.userId + System.currentTimeMillis());
        Observable<StatusMeBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getCollect(mapHeaders, collectInfoData);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 上傳圖片
     *
     * @param owner
     * @param filePath
     * @param callBackLis
     */
    public static void upLoadImg(LifecycleOwner owner, String filePath, CallBackLis<ImgBean> callBackLis) {
        File file = new File(filePath);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), fbody);
        Observable<ImgBean> observable = RetrofitHelper.getInstance().getRetrofit(3).create(Api.class).upLoadImg(photo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 批量上传
     *
     * @param owner
     * @param imagePathList
     * @param callBackLis
     */
    public static void upLoadBatchImg(LifecycleOwner owner, List<String> imagePathList, CallBackLis<BatchImgBean> callBackLis) {
        List<MultipartBody.Part> files = new ArrayList<>();
        for (String s : imagePathList) {
            File file = new File(s);
            RequestBody fbody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), fbody);
            files.add(photo);
        }
        Observable<BatchImgBean> observable = RetrofitHelper.getInstance().getRetrofit(3).create(Api.class).BatchUploadImg(files);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 调用登录注册
     *
     * @param owner
     * @param mobile
     * @param callBackLis
     */
    public static void addUserLogin(LifecycleOwner owner, String mobile, String name, CallBackLis<AddLoginUserBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("name", name);
        params.put("roleId", "37757bda379e4d77b4ad35b631501439");
        Observable<AddLoginUserBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).addLoginUser(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取权限
     *
     * @param callBackLis
     */
    public static void queryAuth(LifecycleOwner owner, String userId, CallBackLis<RoleBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        Observable<RoleBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).queryAuth(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取app 升级接口
     *
     * @param owner
     * @param callBackLis
     */
    public static void getVer(LifecycleOwner owner, CallBackLis<AppVerBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        Observable<AppVerBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAppVer(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取编辑
     *
     * @param owner
     * @param callBackLis
     */
    public static void getBianJi(LifecycleOwner owner, String Mid, CallBackLis<ShouJiBianJiBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "2d2498157f95465eb531dfe429407479");
        params.put("cst", "1");
        Observable<ShouJiBianJiBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getBianJi(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 删除
     *
     * @param owner
     * @param callBackLis
     */
    public static void getDel(LifecycleOwner owner, String Mid, String UserID, CallBackLis<StatusData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "2d2498157f95465eb531dfe429407479");
        params.put("cst", "1");
        params.put("force", "false");
        params.put("userid", UserID);
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).delInfo(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取动物列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getHarAnimal(LifecycleOwner owner, CallBackLis<HarmlessAnimalBean> callBackLis) {
        String[] paixun = new String[]{"SortOrder|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("HarmlessStatus", "1");
        params.put("SystemStatus", "1");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<HarmlessAnimalBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getHarAnimal(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取现场监督员
     *
     * @param owner
     * @param regionIds
     * @param callBackLis
     */
    public static void getXianChangJianDuYuan(LifecycleOwner owner, String regionIds, CallBackLis<XianChangRenBean> callBackLis) {
        HashMap<String, String> params = new HashMap<>();
        params.put("regionIds", regionIds);
        Observable<XianChangRenBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXianChangJianDuRen(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void sendRenYuan(LifecycleOwner owner, SendSmsBean sendSmsBean, CallBackLis<SendStatusBean> callBackLis) {
        Observable<SendStatusBean> observable = RetrofitHelper.getInstance().getRetrofit(4).create(Api.class).sendTiShiDuanXin(sendSmsBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取区划管理员待收集列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getToBeCollected(LifecycleOwner owner, String startTimes, String endTimes, int RegionID, int level, int page, int size, CallBackLis<ToBeCollectedBean> callBackLis) {
        String[] paixun = new String[]{"ApplyTime|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("ApplyTime", "@>=|<=@" + startTimes + "|" + endTimes);
        params.put("CheckStatus", "@con@0");
        if (level == 1) {
            params.put("RegionRI1", RegionID);
        } else if (level == 2) {
            params.put("RegionRI2", RegionID);
        }
        if (level == 3) {
            params.put("RegionRI3", RegionID);
        }
        if (level == 4) {
            params.put("RegionRI4", RegionID);
        }
        params.put("AnimalID", "@in@1|2|3");


        mapList.add(params);

        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);

        Observable<ToBeCollectedBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getToBeCollected(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 上傳圖片
     *
     * @param owner
     * @param filePath
     * @param callBackLis
     */
    public static void upLoadHeadImg(LifecycleOwner owner, String filePath, String userID, CallBackLis<ImgBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userID);
        File file = new File(filePath);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photoHead = MultipartBody.Part.createFormData("file", file.getName(), fbody);
        Observable<ImgBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upLoadHead(params, photoHead);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取待收集详情
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getToBeCollectedDetail(LifecycleOwner owner, String Mid, CallBackLis<ToBeCollectedDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "aa5fb811a9d34b559db2f8144a225f99");
        params.put("cst", "1");
        Observable<ToBeCollectedDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getToBeCollectedDetail(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取新版区划管理员首页统计
     *
     * @param owner
     * @param startTime
     * @param endTime
     * @param regionIds
     * @param regionLevel
     * @param callBackLis
     */
    public static void getAdminRegionHome(LifecycleOwner owner, String startTime, String endTime, int regionIds, int regionLevel, CallBackLis<AdminRegionHomeBean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("regionIds", regionIds);
        params.put("regionLevel", regionLevel);
        Observable<AdminRegionHomeBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getAdminRegionHome(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取区划管理员待收集列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getCollected(LifecycleOwner owner, String startTimes, String endTimes, int RegionID, int level, int page, int size, int type, int CheckStatus, String animalID, String CollectNo, String WorkerName, CallBackLis<CollectedBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (level == 1) {
            params.put("RegionRI1", RegionID);
        } else if (level == 2) {
            params.put("RegionRI2", RegionID);
        }
        if (level == 3) {
            params.put("RegionRI3", RegionID);
        }
        if (level == 4) {
            params.put("RegionRI4", RegionID);
        }
        if (type != 1) {//当type不等于1时  需要  审核状态、查询日期、畜种类、单号、收集人，区划；列表项显示养殖户
            if (CheckStatus == 0) {
                params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//收集时间
                params.put("CheckStatus", CheckStatus);

            } else if (CheckStatus == 1) {
                params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//审核时间
                params.put("CheckStatus", CheckStatus);
            } else {
                params.put("CheckStatus", CheckStatus);
                params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//审核时间
            }

            if (!animalID.equals("-1")) {
                params.put("Animal::key", animalID);
            }
            if (!TextUtils.isEmpty(CollectNo)) {
                params.put("CollectNo", CollectNo);
            }
            if (!TextUtils.isEmpty(WorkerName)) {
                params.put("Worker::Name", WorkerName);
            }
        } else {
            params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//收集时间
            params.put("Animal::key", "@in@1|2|3|6|7|8|16|250|95");
        }
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<CollectedBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCollected(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取待收集详情
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getCollectedDetail(LifecycleOwner owner, String Mid, CallBackLis<CollectedDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "2d2498157f95465eb531dfe429407479");
        params.put("cst", "1");
        Observable<CollectedDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCollectedDetail(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取区划管理员待收集列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getRuKu(LifecycleOwner owner, String startTimes, String endTimes, int RegionID, int level, int page, int size, int type, String rkPerson, CallBackLis<RuKuBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        if (level == 1) {
            params.put("InRegionRI1", RegionID);
        } else if (level == 2) {
            params.put("InRegionRI2", RegionID);
        }
        if (level == 3) {
            params.put("InRegionRI3", RegionID);
        }
        if (level == 4) {
            params.put("InRegionRI4", RegionID);
        }
        if (type != 1) {
            if (!TextUtils.isEmpty(rkPerson)) {
                params.put("CollectStockUser::Name", rkPerson);
            }
        }
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<RuKuBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getRuKu(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * get rk details api
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getRuKuDetail(LifecycleOwner owner, String Mid, CallBackLis<RuKuDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "55981cda99de4cf0936233a49ebcd207");
        params.put("cst", "1");
        Observable<RuKuDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getRuDetail(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取处理列表
     *
     * @param owner
     * @param startTimes
     * @param endTimes
     * @param RegionID
     * @param level
     * @param page
     * @param size
     * @param callBackLis
     */
    public static void getChuLi(LifecycleOwner owner, String startTimes, String endTimes, int RegionID, int level, int page, int size, CallBackLis<ChuLiBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        if (level == 1) {
            params.put("RegionRI1", RegionID);
        } else if (level == 2) {
            params.put("RegionRI2", RegionID);
        }
        if (level == 3) {
            params.put("RegionRI3", RegionID);
        }
        if (level == 4) {
            params.put("RegionRI4", RegionID);
        }
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<ChuLiBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getChuLi(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * get cl  details api
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getChuLiDetails(LifecycleOwner owner, String Mid, CallBackLis<ChuLiDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        params.put("categoryId", "7b5a12a60807492a9b218dae47207673");
        params.put("cst", "1");
        Observable<ChuLiDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCLDetail(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取养殖户列表
     *
     * @param owner
     * @param RegionId
     * @param regionLevel
     * @param displayName
     * @param mobile
     * @param page
     * @param callBackLis
     */
    public static void getXdrList(LifecycleOwner owner, int RegionId, int regionLevel, String displayName, String mobile, int page, CallBackLis<XdrBean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("command", "FarmIsHasHarmlessApply");
        params.put("regionID", RegionId);
        params.put("regionLevel", regionLevel);
        params.put("displayName", displayName);
        params.put("mobile", mobile);
        params.put("page", page);

        Observable<XdrBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXdrList(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取在养殖户收集列表
     *
     * @param owner
     * @param farmMid
     * @param mobile
     * @param callBackLis
     */
    public static void getXdrCollectList(LifecycleOwner owner, String farmMid, String mobile, CallBackLis<XdrCollectListBean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("command", "FarmHarmlessApplyHasCollected");
        params.put("farmMid", farmMid);
        params.put("mobile", mobile);
        Observable<XdrCollectListBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXdrCollectList(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void login(LifecycleOwner owner, String authinfo, CallBackLis<LoginData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("authinfo", authinfo);
        params.put("cst", "1");
        Observable<LoginData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).login(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取免疫动物类表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getImmuneAnimal(LifecycleOwner owner, CallBackLis<ImmuneAnimalBean> callBackLis) {

        String[] paixun = new String[]{"SortOrder|0"};

        List<Map> mapList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("AnimalLevel", "@>@0");
        params.put("SystemStatus", "1");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);

        Observable<ImmuneAnimalBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getImmuneAnimal(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取免疫畜主列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getImmuneXdr(LifecycleOwner owner, int RegionID, int level, int page, int size, int type, String DisplayName, String Mobile, boolean check, CallBackLis<ImmuneXdrBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (check) {
            params.put("ExamineStatus", 941);
        } else {
            params.put("ExamineStatus", 940);
        }

        if (type != AppConstants.IsSelfWrite.ZZMY) {//当type不等于1 也就是自主免疫时不传入下列条件
            params.put("Region" + "::RI" + level, RegionID);
            if (!TextUtils.isEmpty(DisplayName)) {
                params.put("DisplayName", "@con@" + DisplayName);
            }
            if (!TextUtils.isEmpty(Mobile)) {
                params.put("LegalPersonTel", "@con@" + Mobile);
            }
        } else {
            params.put("LegalPersonTel", "@con@" + Mobile);
        }
        mapList.add(params);

        Map<String, Object> params1 = new HashMap<>();
        if (check) {
            params1.put("ExamineStatus", 941);
        } else {
            params1.put("ExamineStatus", 940);
        }
        if (type != AppConstants.IsSelfWrite.ZZMY) {//当type不等于1 也就是自主免疫时不传入下列条件
            params1.put("Region" + "::RI" + level, RegionID);
            if (!TextUtils.isEmpty(DisplayName)) {
                params1.put("DisplayName", "@con@" + DisplayName);
            }
            if (!TextUtils.isEmpty(Mobile)) {
                params1.put("Mobile", "@con@" + Mobile);
            }
        } else {
            params1.put("Mobile", "@con@" + Mobile);
        }
        mapList.add(params1);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<ImmuneXdrBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getImmuneXdr(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取签收的耳标list
     *
     * @param owner
     * @param XdrMid
     * @param AnimalId
     * @param callBackLis
     */
    public static void getCheckEarTagNum(LifecycleOwner owner, String XdrMid, int AnimalId, CallBackLis<FaFangEarTagBean> callBackLis) {
        String[] paixun = new String[]{"Mid|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("InUser", XdrMid);
        params.put("AnimalID", AnimalId);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<FaFangEarTagBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCheckEarTagNum(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取动物关联的疫病
     *
     * @param owner
     * @param AnimalId
     * @param callBackLis
     */
    public static void getAnimalToDiseaseID(LifecycleOwner owner, int AnimalId, CallBackLis<DiseaseIDBean> callBackLis) {
        String[] paixun = new String[]{"Mid|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("AnimalID", AnimalId);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<DiseaseIDBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAnimalToDiseaseID(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取动物关联的疫病
     *
     * @param owner
     * @param callBackLis
     */
    public static void getDiseaseDictionary(LifecycleOwner owner, CallBackLis<DiseaseDictionaryBean> callBackLis) {
        String[] paixun = new String[]{"ordinal|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("group", "Disease");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<DiseaseDictionaryBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getDiseaseDictionary(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取疫苗种类
     *
     * @param owner
     * @param DiseaseID
     * @param AnimalId
     * @param callBackLis
     */
    public static void getVaccineInfo(LifecycleOwner owner, int DiseaseID, int AnimalId, CallBackLis<VaccineBean> callBackLis) {
        String[] paixun = new String[]{"Mid|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("DiseaseID", DiseaseID);
        params.put("AnimalID", AnimalId);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<VaccineBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getVaccineInfo(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 上传免疫详细信息
     *
     * @param owner
     * @param upImmuneDetailsBean
     * @param callBackLis
     */
    public static void upLoadImmuneDetails(LifecycleOwner owner, UpImmuneDetailsBean upImmuneDetailsBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).UpLoadImmuneDetails(UrlPostUtils.UpLoadImmuneDetails, upImmuneDetailsBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 上传免疫系信息
     *
     * @param owner
     * @param callBackLis
     */
    public static void upLoadImmune(LifecycleOwner owner, UpImmuneBean upImmuneBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).UpLoadImmune(UrlPostUtils.UpLoadImmune, upImmuneBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取防疫信息查询
     *
     * @param owner
     * @param XdrMid
     * @param AnimalID
     * @param level
     * @param RegionID
     * @param IsSelfWrite
     * @param callBackLis
     */
    public static void getHistoryImmune(LifecycleOwner owner, int page, int size, String XdrMid, String AnimalID, String name, int level, int RegionID, int IsSelfWrite, CallBackLis<QueryImmuneBean> callBackLis) {
        String[] paixun = new String[]{"Immuned|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (IsSelfWrite == 1010) {
            params.put("creatorId", UserDataSP.getInstance().getUserInfo().Result.userId);
            params.put("Region" + "::RI" + level, RegionID);
            if (!TextUtils.isEmpty(XdrMid)) {
                params.put("XDRCoreID", XdrMid);
            }
            if (!TextUtils.isEmpty(AnimalID)) {
                params.put("AnimalID", AnimalID);
            }
            if (!TextUtils.isEmpty(name)) {
                params.put("XDRCoreInfo::Name", "@con@" + name);
            }
            params.put("AHIUser::Key", "@=@" + UserDataSP.getInstance().getUserInfo().Result.userId);
        } else {
            //params.put("XDRCoreID", XdrMid);
            //params.put("AnimalID", "@in@" + AnimalID);
            params.put("creatorId", UserDataSP.getInstance().getUserInfo().Result.userId);
        }

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<QueryImmuneBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getHistoryImmune(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void getHistoryImmuneDetails(LifecycleOwner owner, String Mid, CallBackLis<QueryImmuneDetailsBean> callBackLis) {
        String[] paixun = new String[]{"CreateAtStr|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("Mid", "@=@" + Mid);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<QueryImmuneDetailsBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getHistoryImmuneDetails(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 修改免疫信息
     *
     * @param owner
     * @param upImmuneDetailsBean
     * @param callBackLis
     */
    public static void upDataImmuneDetails(LifecycleOwner owner, UpDataImmuneDetailsBean upImmuneDetailsBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).UpDataImmuneDetails(UrlPostUtils.UpDataImmuneDetails, upImmuneDetailsBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取再次免疫的耳标
     *
     * @param owner
     * @param XDRCoreID
     * @param AnimalID
     * @param callBackLis
     */
    public static void getAgainEarTag(LifecycleOwner owner, String XDRCoreID, String AnimalID, CallBackLis<AgainEarTagBean> callBackLis) {
        String[] paixun = new String[]{"Immuned|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("XDRCoreID", "@=@" + XDRCoreID);
        params.put("AnimalID", "@=@" + AnimalID);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<AgainEarTagBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAgainEarTag(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取再次免疫的耳标
     */
    public static void getECAB(LifecycleOwner owner, String FactoryCode, CallBackLis<EntryCheckBBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("FactoryCode", "@=@" + FactoryCode);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<EntryCheckBBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getECAB(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 提交入场查验信息
     *
     * @param owner
     * @param upECETBean
     * @param callBackLis
     */
    public static void UpDataECET(LifecycleOwner owner, UpECETBean upECETBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).UpDataECET(UrlPostUtils.UpDateECET, upECETBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取再次免疫的耳标
     */
    public static void getCertNoExists(LifecycleOwner owner, String CertNo, CallBackLis<CertNoExistsBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("CertNo", "@con@" + CertNo);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<CertNoExistsBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCertNoExists(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 修改入场查验接口
     *
     * @param owner
     * @param upECETBean
     * @param callBackLis
     */
    public static void upDataEntryCheck(LifecycleOwner owner, UpECETBean upECETBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upDataEntryCheck(UrlPostUtils.UpDataEntryCheck, upECETBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取A证详细信息
     */
    public static void getECAA(LifecycleOwner owner, String FactoryCode, CallBackLis<EntryCheckABean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("FactoryCode", "@=@" + FactoryCode);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<EntryCheckABean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getECAA(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 修改入场查验接口
     *
     * @param owner
     * @param updataAnimalBBean
     * @param callBackLis
     */
    public static void UpDataAnimalBHuiShou(LifecycleOwner owner, UpdataAnimalBBean updataAnimalBBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upDataAnimalB(UrlPostUtils.HUISHOUANIMALB, updataAnimalBBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void UpDataAnimalAHuiShou(LifecycleOwner owner, UpdataAnimalABean updataAnimalABean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upDataAnimalA(UrlPostUtils.HUISHOUANIMALA, updataAnimalABean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getFunAppInfo(LifecycleOwner owner, CallBackLis<AppFunBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "2f2d5605c036438fa0278fcbcdac51bc");
        Observable<AppFunBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getFunAppInfo(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void getCustomerInfo(LifecycleOwner owner, CallBackLis<CustomerBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("partitionId", "0b1d178c499043a2aeeef591a3d8f03d");
        Observable<CustomerBean> observable = RetrofitHelper.getInstance().getRetrofit("http://www.cdzhdj.cn/v3.0/api/", -1).create(Api.class).getCustomerInfo(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取屠宰场备案关联
     */
    public static void getSlaughterBei(LifecycleOwner owner, String PhoneNum, CallBackLis<SlaughterBeiAnBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("PhoneNum", "@con@" + PhoneNum);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<SlaughterBeiAnBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getSlaughterBei(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取xdr列表
     *
     * @param owner
     * @param
     * @param callBackLis
     */
    public static void getXdrBindInfo(LifecycleOwner owner, String UserID, String Mobile, CallBackLis<XdrBindListData> callBackLis) {

        GetUserBindFarmByMobileBean getUserBindFarmByMobileBean = new GetUserBindFarmByMobileBean();
        getUserBindFarmByMobileBean.params = new GetUserBindFarmByMobileBean.ParamsBean();
        getUserBindFarmByMobileBean.params.Mobile = Mobile;
        getUserBindFarmByMobileBean.params.UserId = UserID;

        Observable<XdrBindListData> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXdrBindInfo(getUserBindFarmByMobileBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取模糊查询手机号+UserID，备案信息 TODO://2023-12-26
     *
     * @param owner
     * @param UserID
     * @param Mobile
     * @param callBackLis
     */
    public static void getXdrBindInfoNew(LifecycleOwner owner, String UserID, String Mobile, CallBackLis<FarmXdrInfoListByUserIdAndMobileBean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", Mobile);
        params.put("userid", UserID);
        Observable<FarmXdrInfoListByUserIdAndMobileBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXdrBindInfoNew(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 根据用户id查询备案的养殖场及绑定信息
     *
     * @param owner
     * @param callBackLis
     */

    public static void getXdrBindList(LifecycleOwner owner, String Mobile, CallBackLis<XdrBindListData> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", Mobile);
        params.put("userId", "");
        Observable<XdrBindListData> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getXdrBindList(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 创建法人/自然人 并绑定相对人 ai
     *
     * @param owner
     * @param UserID
     * @param XDRMid
     * @param Mobile
     * @param UserName
     * @param UserType
     * @param CorpName
     * @param CertificateSno
     * @param callBackLis
     */
    public static void mCreateNaturalLegal(LifecycleOwner owner, String UserID, String XDRMid, String Mobile, String UserName, String IDCard, int UserType, String CorpName, String CertificateSno, CallBackLis<LegalPersonSuccessBean> callBackLis) {

        CreateNaturalLegalBean createNaturalLegalBean = new CreateNaturalLegalBean();
        createNaturalLegalBean.params = new CreateNaturalLegalBean.ParamsBean();
        createNaturalLegalBean.params.UserId = UserID;
        createNaturalLegalBean.params.XDRMid = XDRMid;
        createNaturalLegalBean.params.CertNo = IDCard;
        createNaturalLegalBean.params.Mobile = Mobile;
        createNaturalLegalBean.params.UserName = UserName;
        createNaturalLegalBean.params.UserType = UserType;
        createNaturalLegalBean.params.CorpName = CorpName;
        createNaturalLegalBean.params.CertificateSno = CertificateSno;
        Observable<LegalPersonSuccessBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).CreateNaturalLegal(createNaturalLegalBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 据用户id相对人mid及备案法人手机号，验证绑定
     *
     * @param owner
     * @param bindXdrUserIDForMobileBean
     * @param callBackLis
     */
    public static void BindXdrUserIDForMobile(LifecycleOwner owner, BindXdrUserIDForMobileBean bindXdrUserIDForMobileBean, CallBackLis<StatusMeBean> callBackLis) {
        Observable<StatusMeBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).BindXdrUserIDForMobile(bindXdrUserIDForMobileBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getRegionInfo(LifecycleOwner owner, int regionid, int regionlevel, CallBackLis<ProvinceData> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("regionid", regionid);
        params.put("regionlevel", regionlevel);
        params.put("regionrange", "0");
        Observable<ProvinceData> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getRegionInfo(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取个人信息
     *
     * @param owner
     * @param authinfo
     * @param callBackLis
     */
    public static void getUsserInfo(LifecycleOwner owner, String authinfo, CallBackLis<LoginData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("_af", authinfo);
        Observable<LoginData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getUserInfo(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取
     *
     * @param owner
     * @param callBackLis
     */
    public static void getAgencyData(LifecycleOwner owner, CallBackLis<AgencyData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|0"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("AgencyFunctions", "@con@" + "b5f273579811489092a1af3f0f615283");
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        params.put("AgencyType", "@=@201");

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<AgencyData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAgencyData(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取机构人员
     *
     * @param owner
     * @param AgencyMID
     * @param callBackLis
     */
    public static void getAgencyPersonData(LifecycleOwner owner, String AgencyMID, CallBackLis<AgencyPersonData> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("AgencyMID", "@=@" + AgencyMID);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<AgencyPersonData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAgencyPersonData(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 提交执法检查信息填报
     *
     * @param owner
     * @param enforcementData
     * @param callBackLis
     */
    public static void addDataEnforcementData(LifecycleOwner owner, EnforcementData enforcementData, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).addDataEnforcementData(UrlPostUtils.UpDataLAW, enforcementData);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取区划管理员待收集列表
     *
     * @param owner
     * @param callBackLis
     */
    public static void getEnforcementList(LifecycleOwner owner, String startTimes, String endTimes, String UserId, String Mid, int type, CallBackLis<EnforcementHomeData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (type == 1) {//填报 主办
            params.put("Organizer::UserID", UserId);
            params.put("CheckType", "@=@" + 1);
        } else if (type == 2) {//协办
            params.put("CoOrganizer::Mid", Mid);
            params.put("CheckType", "@=@" + 1);
        } else if (type == 3) {//抽查
            params.put("Organizer::UserID", UserId);
            params.put("CheckType", "@=@" + 2);
        } else if (type == 4) {//待办事项
            params.put("Organizer::UserID", UserId);//主办人的User、
            params.put("AssignPerson::Mid", Mid);//指派人的Mid
            params.put("AssignmentStatus", "@in@1|3");//已经指派的
        }
        params.put("SourceType", 0);//已经指派的
        params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//创建时间

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<EnforcementHomeData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getEnforcementList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * @param owner
     * @param name
     * @param callBackLis
     */
    public static void getAHIUserEXMid(LifecycleOwner owner, String name, CallBackLis<AHIUserEXBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("Name", "@con@" + name);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<AHIUserEXBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAHIUserEXMid(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取在填报时选择的支队长的手机号 查询数据
     *
     * @param owner
     * @param callBackLis
     */
    public static void getAssignList(LifecycleOwner owner, String Mid, CallBackLis<EnforcementHomeData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("ReportingAgency::Mid", Mid);
        params.put("CheckType", "@=@" + 1);
        params.put("SourceType", 0);//已经指派的

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<EnforcementHomeData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getAssignList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 修改执法检查数据 指派机构或者人员
     *
     * @param owner
     * @param assignmentBean
     * @param callBackLis
     */
    public static void updataAssignment(LifecycleOwner owner, AssignmentBean assignmentBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).updataAssignment(UrlPostUtils.UpDataAssignment, assignmentBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取执法检查详细
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getEnforcementDetails(LifecycleOwner owner, String mid, CallBackLis<EnforcementDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "78c21f0133c6471bb3d6fa5421020137");
        Observable<EnforcementDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getEnforcementDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getToDoMianList(LifecycleOwner owner, String startTimes, String endTimes, String UserId, String Mid, int type, CallBackLis<EnforcementHomeData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        //待办事项
        params.put("Organizer::UserID", UserId);//主办人的User、
//        params.put("AssignPerson::Mid",Mid);//指派人的Mid
        params.put("AssignmentStatus", "@in@1|3");//已经指派的
        params.put("SourceType", 0);//已经指派的
        params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//创建时间
        mapList.add(params);
        Map<String, Object> params1 = new HashMap<>();
        //待办事项
//        params1.put("Organizer::UserID",UserId);//主办人的User、
        params1.put("AssignPerson::Mid", Mid);//指派人的Mid
        params1.put("AssignmentStatus", "@in@1|3");//已经指派的
        params1.put("SourceType", 0);//已经指派的
        params1.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//创建时间
        mapList.add(params1);


        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<EnforcementHomeData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getEnforcementList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 提交执法复查信息填报
     *
     * @param owner
     * @param reexaminationFillingBean
     * @param callBackLis
     */
    public static void updataReviewCheck(LifecycleOwner owner, ReexaminationFillingBean reexaminationFillingBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).updataReviewCheck(UrlPostUtils.UpDataAssignment, reexaminationFillingBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 提交执法复查信息抽查天博
     *
     * @param owner
     * @param spotCheckBean
     * @param callBackLis
     */
    public static void updataChouCha(LifecycleOwner owner, SpotCheckBean spotCheckBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).updataChouCha(UrlPostUtils.UpDataAssignment, spotCheckBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 通过手机号获取备案的数据
     *
     * @param owner
     * @param Mobile
     * @param callBackLis
     */
    public static void getMobileXdr(LifecycleOwner owner, String Mobile, CallBackLis<XdrFeedData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("ExamineStatus", 940);
        params.put("Mobile", "@con@" + Mobile);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<XdrFeedData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getMobileXdr(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 添加饲料页面
     *
     * @param owner
     * @param addFeedStorageBean
     * @param callBackLis
     */
    public static void addFeed(LifecycleOwner owner, AddFeedStorageBean addFeedStorageBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).addFeed(UrlPostUtils.UpDataFeedAdd, addFeedStorageBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void sendNewSms(LifecycleOwner owner, NewSendSmsBean sendSmsBean, CallBackLis<SendStatusBean> callBackLis) {
        Observable<SendStatusBean> observable = RetrofitHelper.getInstance().getRetrofit(6).create(Api.class).sendWHHSms(sendSmsBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取饲料入库list
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getFeedStorageList(LifecycleOwner owner, String mid, int type, CallBackLis<FeedStorageListData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("DepositorUser::mid", "@=@" + mid);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        if (type == 1) {
            params.put("GoodsType::key", "@=@" + 6102);//	饲料
        } else {
            params.put("GoodsType::key", "@=@" + 6101);//  兽药
        }
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);

        Observable<FeedStorageListData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getFeedStorageList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void getFeedStorageFarmList(LifecycleOwner owner, String mid, String FarmMid, CallBackLis<FeedStorageListData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("DepositorUser::mid", "@=@" + mid);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        params.put("GoodsType::key", "@=@" + 6101);//  兽药
        params.put("Farm::mid", "@=@" + FarmMid);//  兽药
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);

        Observable<FeedStorageListData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getFeedStorageList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取饲料投入品详情
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getFeedStorageDetails(LifecycleOwner owner, String mid, CallBackLis<FeedStorageDetails> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "5bfa949f6e294b4e80474fd57dec3d24");
        Observable<FeedStorageDetails> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getFeedStorageDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 添加兽药出库
     *
     * @param owner
     * @param addGoodsBean
     * @param callBackLis
     */
    public static void addGoodsOut(LifecycleOwner owner, AddGoodsBean addGoodsBean, CallBackLis<StatusMapBean> callBackLis) {
        Observable<StatusMapBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).addGoodsOut(UrlPostUtils.UpDataFeed, addGoodsBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取兽药出库列表
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getGoodsList(LifecycleOwner owner, String mid, CallBackLis<GoodsOutListBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("UseUser::mid", "@=@" + mid);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);

        Observable<GoodsOutListBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getGoodsList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取物品出库详细
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getGoodsOutDetails(LifecycleOwner owner, String mid, CallBackLis<GoodsOutDetailsBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "d7546d00b04d4a3fac175773d0afbe36");
        Observable<GoodsOutDetailsBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getGoodsOutDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 更新相对人状态
     *
     * @param owner
     * @param checkXdrBean
     * @param callBackLis
     */
    public static void checkXdrStatus(LifecycleOwner owner, CheckXdrBean checkXdrBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).checkXdrStatus(UrlPostUtils.UpDataAssignment, checkXdrBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取屠宰场备案详细
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getSlaughterHouseDetails(LifecycleOwner owner, String mid, CallBackLis<SlaughterHouseBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "531dead53910453898b1203294791595");
        Observable<SlaughterHouseBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getSlaughterHouseDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void getCheckTj(LifecycleOwner owner, CallBackLis<CheckTjData> callBackLis) {
        Observable<CheckTjData> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getCheckTj();
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * @param owner       this
     * @param logData     实体
     * @param callBackLis 回调
     */
    public static void addLog(LifecycleOwner owner, LogData logData, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).addCollectionxlog(UrlPostUtils.AddCollectionXLog, logData);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取入场查验历史记录
     *
     * @param owner
     * @param CheckResult
     * @param CheckStartTime
     * @param CheckEndTime
     * @param callBackLis
     */
    public static void getHistoryEntryCheck(LifecycleOwner owner, int page, int size, String CheckResult, String CheckStartTime, String CheckEndTime, String CertNo, String SlaughterHouseID, CallBackLis<EntryCheckHistoryData> callBackLis) {
        String[] paixun = new String[]{"CheckTime|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        //params.put("CheckUser::Key", "@=@" + UserDataSP.getInstance().getUserInfo().Result.userId);
        if (!TextUtils.isEmpty(CheckStartTime) && !TextUtils.isEmpty(CheckEndTime)) {
            params.put("CheckTime", "@>=|<=@" + CheckStartTime + "|" + CheckEndTime);
        }
        if (!TextUtils.isEmpty(CheckResult) && !"-1".equals(CheckResult)) {
            params.put("CheckResult", "@=@" + CheckResult);
        }
        if (!TextUtils.isEmpty(CertNo)) {
            params.put("CertNo", "@con@" + CertNo);
        }
        params.put("SlaughterHouseID", "@=@" + SlaughterHouseID);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<EntryCheckHistoryData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getHistoryEntryCheck(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取入场查验列表详细
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getHistoryEntryCheckDetails(LifecycleOwner owner, String mid, CallBackLis<EntryCheckHistoryDetailBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "e4ce92598ac54a06ae03d3c597f506be");
        Observable<EntryCheckHistoryDetailBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getHistoryEntryCheckDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取代宰户列表
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getDaiZai(LifecycleOwner owner, String Mid, CallBackLis<DaiZaiHouseBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        Observable<DaiZaiHouseBean> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getDaiZai(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取该屠宰场是否有待宰户权限
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getIsCheckDZH(LifecycleOwner owner, String Mid, CallBackLis<IsCheckDZHBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", Mid);
        Observable<IsCheckDZHBean> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getIsCheckDZH(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 上传代宰户
     *
     * @param owner
     * @param entryCheckDZHBeanList
     * @param callBackLis
     */
    public static void AddCheckDZHList(LifecycleOwner owner, List<EntryCheckDZHBean> entryCheckDZHBeanList, CallBackLis<IsCheckDZHBean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("list", entryCheckDZHBeanList);
        Observable<IsCheckDZHBean> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).AddCheckDZHList(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取代宰户详细
     *
     * @param owner
     * @param Mid
     * @param callBackLis
     */
    public static void getDZHDetails(LifecycleOwner owner, String Mid, CallBackLis<DaiZaiHuDetailsBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("CheckMid", "@=@" + Mid);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<DaiZaiHuDetailsBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getDZHDetails(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 微信推送
     *
     * @param owner
     * @param mids
     * @param message1
     * @param message2
     * @param callBackLis
     */
    public static void sendWxMessage(LifecycleOwner owner, String mids, String message1, String message2, CallBackLis<Status2Bean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("mids", mids);
        params.put("messages1", message1);
        params.put("messages2", message2);
        params.put("url", "-1");
        Observable<Status2Bean> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).sendWxMessage(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取入库详细
     *
     * @param owner
     * @param midList
     * @param callBackLis
     */
    public static void getRuKuForMidList(LifecycleOwner owner, List<String> midList, CallBackLis<RuKuListForMidBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        for (String midListBean : midList) {
            Map<String, Object> params = new HashMap<>();
            params.put("Mid", midListBean);
            mapList.add(params);
        }
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<RuKuListForMidBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getRuKuForMidList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取收集详细
     *
     * @param owner
     * @param midList
     * @param callBackLis
     */
    public static void getShouJiForMidList(LifecycleOwner owner, List<String> midList, CallBackLis<ShouJiListForMidBean> callBackLis) {
        List<Map> mapList = new ArrayList<>();
        for (String midListBean : midList) {
            Map<String, Object> params = new HashMap<>();
            params.put("Mid", midListBean);
            mapList.add(params);
        }
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        Observable<ShouJiListForMidBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getShouJiForMidList(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取申报列表
     *
     * @param owner
     * @param region
     * @param callBackLis
     */
    public static void getApply(LifecycleOwner owner, List<String> region, String startTime, String endTime, int status, CallBackLis<ApplyBean> callBackLis) {

        String[] paixun = new String[]{"CreateAt|1"};

        List<Map> mapList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < region.size(); i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(region.get(i));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("Region::RI4", "@in@" + sb.toString());
        params.put("CheckStatus", status);
        params.put("ApplyPoint::ID", RoleSP.getInstance().getRoleInfo().harmlessUser.factory.Mid);
        params.put("CreateAt", "@>=|<=@" + startTime + "|" + endTime);//创建时间
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<ApplyBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getApply(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getCarNumberInfo(LifecycleOwner owner, CallBackLis<CarNumberBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("Factory::Mid", RoleSP.getInstance().getRoleInfo().harmlessUser.factory.Mid);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<CarNumberBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCarNumberInfo(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 出车
     *
     * @param owner
     * @param bindCarBean
     * @param callBackLis
     */
    public static void upDataCar(LifecycleOwner owner, BindCarBean bindCarBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upDataCar(UrlPostUtils.GOCAR, bindCarBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 驳回
     *
     * @param owner
     * @param applyBoHuiBean
     * @param callBackLis
     */
    public static void applyBoHui(LifecycleOwner owner, ApplyBoHuiBean applyBoHuiBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).applyBoHui(UrlPostUtils.APPLYBOHUI, applyBoHuiBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 收运人收集列表
     *
     * @param owner
     * @param startTimes
     * @param endTimes
     * @param CheckStatus
     * @param FactoryGUID
     * @param CreatorId
     * @param callBackLis
     */
    public static void getCollectedInfo(LifecycleOwner owner, String startTimes, String endTimes, int CheckStatus, String FactoryGUID, String CreatorId, CallBackLis<CollectedBean> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("CreateAt", "@>=|<=@" + startTimes + "|" + endTimes);//收集时间
        if (CheckStatus != -1) {
            params.put("CheckStatus", CheckStatus);
        }
        params.put("FactoryGUID", FactoryGUID);
        params.put("CreatorId", CreatorId);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", 0);
        paramsInfo.put("size", 99999);
        Observable<CollectedBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCollected(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 耳标号验证
     *
     * @param owner
     * @param eartagCode
     * @param callBackLis
     */

    public static void checkEartagIsused(LifecycleOwner owner, String eartagCode, CallBackLis<StatusMeBean> callBackLis) {
        CheckEartagIsusedBean checkEartagIsusedBean = new CheckEartagIsusedBean();
        checkEartagIsusedBean.params = new CheckEartagIsusedBean.ParamsBean();
        checkEartagIsusedBean.params.eartag = eartagCode;
        Observable<StatusMeBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).checkEartagIsused(checkEartagIsusedBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取无害化红点提示
     *
     * @param owner
     * @param getQueryByRoleBean
     * @param callBackLis
     */
    public static void getQueryByRole(LifecycleOwner owner, GetQueryByRoleBean getQueryByRoleBean, CallBackLis<RedDotsShowPromptsBean> callBackLis) {
        Observable<RedDotsShowPromptsBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).getQueryByRole(getQueryByRoleBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getUserRole(LifecycleOwner owner, CallBackLis<UserAllRoleBean> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", UserDataSP.getInstance().getUserInfo().Result.userId);
        params.put("partId", "d5896b31964e425382df52f655dedfc2");
        Observable<UserAllRoleBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getUserRole(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 查询耳标是否使用
     *
     * @param owner
     * @param eartagCode
     * @param callBackLis
     */

    public static void checkEartagIsusedNew(LifecycleOwner owner, String eartagCode, CallBackLis<EarTagCheckBean> callBackLis) {
        CheckEartagIsusedNewBean checkEartagIsusedBean = new CheckEartagIsusedNewBean();
        checkEartagIsusedBean.params = new CheckEartagIsusedNewBean.ParamsBean();
        checkEartagIsusedBean.params.eartag = eartagCode;
        Observable<EarTagCheckBean> observable = RetrofitHelper.getInstance().getRetrofit(2).create(Api.class).checkEartagIsusedNew(checkEartagIsusedBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取公告是否显示
     *
     * @param owner
     * @param callBackLis
     */
    public static void getShowPublish(LifecycleOwner owner, CallBackLis<ShowPublishBean> callBackLis) {
        ShowPublishParams showPublishParams = new ShowPublishParams();
        showPublishParams.params = new ShowPublishParams.ParamsBean();
        Observable<ShowPublishBean> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getShowPublish(showPublishParams);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 修改入场查验数据
     *
     * @param owner
     * @param callBackLis
     */
    public static void mModifyAdmissionCheck(LifecycleOwner owner, String mid, int CheckResult, int NumberIsPass, String ActualNumber, String AnomalyNumber, String DeathNumber, CallBackLis<StatusData> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("Mid", mid);
        params.put("CategoryId", "e4ce92598ac54a06ae03d3c597f506be");
        params.put("NumberIsPass", NumberIsPass);
        params.put("ActualNumber", ActualNumber);
        params.put("AnomalyNumber", AnomalyNumber);
        params.put("DeathNumber", DeathNumber);
        params.put("IsModify", 1);
        params.put("CheckResult", CheckResult);
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).mModifyAdmissionCheck(UrlPostUtils.UpDataEntryCheck, params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 上传入场查验日志
     *
     * @param owner
     * @param entryCheckLogBean
     * @param callBackLis
     */
    public static void upDataEntryCheckLog(LifecycleOwner owner, EntryCheckLogBean entryCheckLogBean, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).upDataEntryCheckLog(UrlPostUtils.UpDataEntryCheckLog, entryCheckLogBean);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取入场查验日志列表
     *
     * @param owner
     * @param EntryCheckMid
     * @param callBackLis
     */
    public static void getEntryCheckLog(LifecycleOwner owner, String EntryCheckMid, CallBackLis<EntryCheckLogData> callBackLis) {
        String[] paixun = new String[]{"CreateAt|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("EntryCheckMid", EntryCheckMid);
        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        Observable<EntryCheckLogData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getEntryCheckLog(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    public static void getQuarantineDeclare(LifecycleOwner owner, String mid, String startTime, String endTime, CallBackLis<QuarantineDeclareData> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("mid", mid);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        Observable<QuarantineDeclareData> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).getQuarantineDeclare(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void queryEarTagIsInHistory(LifecycleOwner owner, String startNo, String endNo, CallBackLis<EarTagIsInHistoryData> callBackLis) {
        // 创建数据
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> item1 = new HashMap<>();
        item1.put("startNo", startNo);
        item1.put("endNo", endNo);
        data.add(item1);
        Observable<EarTagIsInHistoryData> observable = RetrofitHelper.getInstance().getRetrofit(5).create(Api.class).queryEarTagIsInHistory(data);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getCheckPointCertA(LifecycleOwner owner, String certNo, CallBackLis<CheckPointCertABean> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("CertNo", certNo);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        Observable<CheckPointCertABean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getCheckPointCertA(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    public static void getImmuneBranch(LifecycleOwner owner, int page, int size, String XdrMid, String AnimalID, String name, int level, int RegionID, int IsSelfWrite, String userId, CallBackLis<QueryImmuneBean> callBackLis) {
        String[] paixun = new String[]{"Immuned|1"};
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (IsSelfWrite == 1010) {
            params.put("Region" + "::RI" + level, RegionID);
            if (!TextUtils.isEmpty(XdrMid)) {
                params.put("XDRCoreID", XdrMid);
            }
            if (!TextUtils.isEmpty(AnimalID)) {
                params.put("AnimalID", AnimalID);
            }
            if (!TextUtils.isEmpty(name)) {
                params.put("XDRCoreInfo::Name", "@con@" + name);
            }
            params.put("creatorId", userId);
            params.put("AHIUser::Key", "@=@" + UserDataSP.getInstance().getUserInfo().Result.userId);
        } else {
            params.put("creatorId", userId);
        }

        mapList.add(params);
        HashMap<String, Object> paramsInfo = new HashMap<>();
        paramsInfo.put("dataArgs", mapList);
        paramsInfo.put("orderBy", paixun);
        paramsInfo.put("page", page);
        paramsInfo.put("size", size);
        Observable<QueryImmuneBean> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getImmuneBranch(paramsInfo);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }

    /**
     * 获取免疫详情
     *
     * @param owner
     * @param mid
     * @param callBackLis
     */
    public static void getImmuneDetails(LifecycleOwner owner, String mid, CallBackLis<ImmuneDetails> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        params.put("categoryId", "d0559e21622549148de9911439202147");
        Observable<ImmuneDetails> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).getImmuneDetails(params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 提交补戴标
     *
     * @param owner
     * @param mid
     * @param earTags
     * @param replenishEarTagCode
     * @param callBackLis
     */
    public static void commitImmunePatchEarTagCode(LifecycleOwner owner, String mid, String earTags, String replenishEarTagCode, String count, CallBackLis<StatusData> callBackLis) {
        Map<String, String> params = new HashMap<>();
        params.put("Mid", mid);
        params.put("CategoryId", "d0559e21622549148de9911439202147");
        params.put("EarTags", earTags);
        params.put("ImmuneCount", count);
        params.put("ImmuneQuantity", count);
        params.put("ReplenishEarTagCode", replenishEarTagCode);
        params.put("_PartId", "d5896b31964e425382df52f655dedfc2");
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1).create(Api.class).commitImmunePatchEarTagCode(UrlPostUtils.UpDataImmuneDetails, params);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     *成都出具的屠宰用途的证，目的地屠宰场回收，证状态变为已回收后，默认向启运地报告；
     * 功能先做出去暂不发布。
     * @param owner
     * @param isArrivedReportData
     * @param callBackLis
     */
    public static void commitArriveInfo(LifecycleOwner owner,  IsArrivedReportData isArrivedReportData, CallBackLis<StatusData> callBackLis) {
        Observable<StatusData> observable = RetrofitHelper.getInstance().getRetrofit(1)
                .create(Api.class).commitArriveInfo(UrlPostUtils.commitArriveInfoUrl, isArrivedReportData);
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }


    /**
     * 获取app验证配置信息
     * @param owner
     * @param callBackLis
     */
    public static void getAppConfiguration(LifecycleOwner owner,  CallBackLis<AppConfigurationBean> callBackLis) {
        Observable<AppConfigurationBean> observable = RetrofitHelper.getInstance().getRetrofit(5)
                .create(Api.class).getAppConfiguration();
        HttpCall.doCallWithoutIntercept(owner, observable, callBackLis, null);
    }
}
