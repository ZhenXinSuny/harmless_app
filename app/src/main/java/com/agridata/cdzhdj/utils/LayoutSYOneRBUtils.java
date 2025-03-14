package com.agridata.cdzhdj.utils;

import android.view.View;
import android.widget.RadioButton;

import com.agridata.cdzhdj.data.law.VeterinaryDrugFieldBean;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 16:44.
 * @Description :描述
 */
public class LayoutSYOneRBUtils {


    public static VeterinaryDrugFieldBean setRBSeed(VeterinaryDrugFieldBean veterinaryDrugFieldBean , View view,
                                                    RadioButton GMPZhengShuOk, RadioButton GMPZhengShuNo,
                                                    RadioButton youXiaoqiOk, RadioButton youXiaoqiNo,
                                                    RadioButton yuanLiaoFuLiaoFuHeOk, RadioButton yuanLiaoFuLiaoFuHeNo,
                                                    RadioButton StorageAndSafekeepingOk, RadioButton StorageAndSafekeepingNo,
                                                    RadioButton wenJianJiZaiOk, RadioButton wenJianJiZaiNo,
                                                    RadioButton caoZuoRenQianMingOk, RadioButton caoZuoRenQianMingNo,
                                                    RadioButton piHaoGuiDangOk, RadioButton piHaoGuiDangNo,
                                                    RadioButton zhiLiangJianYanOK, RadioButton zhiLiangJianYanNo,
                                                    RadioButton biaoQianshuomingOK, RadioButton biaoQianshuomingNo,
                                                    RadioButton biaozhuerweimaOK, RadioButton biaozhuerweimaNo,
                                                    RadioButton guifanyinzhiOK, RadioButton guifanyinzhiNo,
                                                    RadioButton piqianfaOK, RadioButton piqianfaNo,
                                                    RadioButton zhaohuizhiduOK, RadioButton zhaohuizhiduNo,
                                                    RadioButton anquanshengchananquanshengchanOK, RadioButton anquanshengchananquanshengchanNo,
                                                    RadioButton GMPhouxujianguanOK, RadioButton GMPhouxujianguanNo) {

        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();



        if (view == GMPZhengShuOk) {
            LogUtil.d("lzx----》","是否侵犯植物新品种权 ");
            if (isChecked) {
                veterinaryDrugFieldBean.GMPZhengShu = 1;
            }
        } else if (view == GMPZhengShuNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.GMPZhengShu = 0;
            }
        } else if (view == youXiaoqiOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.youXiaoqi = 1;
            }
        } else if (view == youXiaoqiNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.youXiaoqi = 0;
            }
        } else if (view == yuanLiaoFuLiaoFuHeOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.yuanLiaoFuLiaoFuHe = 1;
            }
        } else if (view == yuanLiaoFuLiaoFuHeNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.yuanLiaoFuLiaoFuHe = 0;
            }
        } else if (view == StorageAndSafekeepingOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.StorageAndSafekeeping = 1;
            }
        } else if (view == StorageAndSafekeepingNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.StorageAndSafekeeping = 0;
            }
        } else if (view == wenJianJiZaiOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.wenJianJiZai = 1;
            }
        } else if (view == wenJianJiZaiNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.wenJianJiZai = 0;
            }
        } else if (view == caoZuoRenQianMingOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.caoZuoRenQianMing = 1;
            }
        } else if (view == caoZuoRenQianMingNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.caoZuoRenQianMing = 0;
            }
        } else if (view == piHaoGuiDangOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.piHaoGuiDang = 1;
            }
        } else if (view == piHaoGuiDangNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.piHaoGuiDang = 0;
            }
        } else if (view == zhiLiangJianYanOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.zhiLiangJianYan = 1;
            }
        } else if (view == zhiLiangJianYanNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.zhiLiangJianYan = 0;
            }
        } else if (view == biaoQianshuomingOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.biaoQianshuoming = 1;
            }
        } else if (view == biaoQianshuomingNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.biaoQianshuoming = 0;
            }
        } else if (view == biaozhuerweimaOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.biaozhuerweima = 1;
            }
        } else if (view == biaozhuerweimaNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.biaozhuerweima = 0;
            }
        }else if (view == guifanyinzhiOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.guifanyinzhi = 1;
            }
        }else if (view == guifanyinzhiNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.guifanyinzhi = 0;
            }
        }else if (view == piqianfaOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.piqianfa = 1;
            }
        }else if (view == piqianfaNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.piqianfa = 0;
            }

        }else if (view == zhaohuizhiduOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.zhaohuizhidu = 1;
            }
        }
        else if (view == zhaohuizhiduNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.zhaohuizhidu = 0;
            }
        } else if (view == anquanshengchananquanshengchanOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.anquanshengchananquanshengchan = 1;
            }
        } else if (view == anquanshengchananquanshengchanNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.anquanshengchananquanshengchan = 0;
            }
        }else if (view == GMPhouxujianguanOK) {
            if (isChecked) {
                veterinaryDrugFieldBean.GMPhouxujianguan = 1;
            }
        }else if (view == GMPhouxujianguanNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.GMPhouxujianguan = 0;
            }
        }
        LogUtil.d("lzx-----》",veterinaryDrugFieldBean.toString());
        return veterinaryDrugFieldBean;
    }

}
