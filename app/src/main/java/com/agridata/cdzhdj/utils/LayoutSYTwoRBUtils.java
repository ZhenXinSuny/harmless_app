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
public class LayoutSYTwoRBUtils {


    public static VeterinaryDrugFieldBean setRBSeed(VeterinaryDrugFieldBean veterinaryDrugFieldBean , View view,
                                                    RadioButton shouyaoxukezhengzizhiOk, RadioButton shouyaoxukezhengzizhiNo,
                                                    RadioButton shifouyouxiaoqiOk, RadioButton shifouyouxiaoqiNo,
                                                    RadioButton jingyingchangsuoxiangshiyingOk, RadioButton jingyingchangsuoxiangshiyingNo,
                                                    RadioButton shebeiqiquanOk, RadioButton shebeiqiquanNo,
                                                    RadioButton guizhangzhiduOk, RadioButton guizhangzhiduNo,
                                                    RadioButton caigourukuOk, RadioButton caigourukuNo,
                                                    RadioButton chenliechuxuOneOk, RadioButton chenliechuxuOneNo,
                                                    RadioButton chenliechuxuTwoOk, RadioButton chenliechuxuTwoNo,
                                                    RadioButton chenliechuxuThreeOk, RadioButton chenliechuxuThreeNo,
                                                    RadioButton chenliechuxuFourOk, RadioButton chenliechuxuFourNo,
                                                    RadioButton shouyaoxiaoshouOneOk, RadioButton shouyaoxiaoshouOneNo,
                                                    RadioButton shouyaoxiaoshouTwoOk, RadioButton shouyaoxiaoshouTwoNo,
                                                    RadioButton shouyaoshengwuzhipinOneOk, RadioButton shouyaoshengwuzhipinOneNo,
                                                    RadioButton shouyaoshengwuzhipinOneUninvolved, RadioButton shouyaoshengwuzhipinTwoOk,
                                                    RadioButton shouyaoshengwuzhipinTwoNo, RadioButton shouyaoshengwuzhipinTwoUninvolved,
                                                    RadioButton shouyaoshengwuzhipinThreeOk,
                                                    RadioButton shouyaoshengwuzhipinThreeNo, RadioButton shouyaoshengwuzhipinThreeUninvolved,
                                                    RadioButton GMPhouxujianguanTwoOk,RadioButton GMPhouxujianguanTwoNo) {

        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();

        if (view == shouyaoxukezhengzizhiOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxukezhengzizhi = 1;
            }
        } else if (view == shouyaoxukezhengzizhiNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxukezhengzizhi = 0;
            }
        } else if (view == shifouyouxiaoqiOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shifouyouxiaoqi = 1;
            }
        } else if (view == shifouyouxiaoqiNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shifouyouxiaoqi = 0;
            }
        } else if (view == jingyingchangsuoxiangshiyingOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.jingyingchangsuoxiangshiying = 1;
            }
        } else if (view == jingyingchangsuoxiangshiyingNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.jingyingchangsuoxiangshiying = 0;
            }
        } else if (view == shebeiqiquanOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shebeiqiquan = 1;
            }
        } else if (view == shebeiqiquanNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shebeiqiquan = 0;
            }
        } else if (view == guizhangzhiduOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.guizhangzhidu = 1;
            }
        } else if (view == guizhangzhiduNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.guizhangzhidu = 0;
            }
        } else if (view == caigourukuOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.caigouruku = 1;
            }
        } else if (view == caigourukuNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.caigouruku = 0;
            }
        } else if (view == chenliechuxuOneOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuOne = 1;
            }
        } else if (view == chenliechuxuOneNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuOne = 0;
            }
        } else if (view == chenliechuxuTwoOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuTwo = 1;
            }
        } else if (view == chenliechuxuTwoNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuTwo = 0;
            }
        } else if (view == chenliechuxuThreeOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuThree = 1;
            }
        } else if (view == chenliechuxuThreeNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuThree = 0;
            }
        } else if (view == chenliechuxuFourOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuFour = 1;
            }
        } else if (view == chenliechuxuFourNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.chenliechuxuFour = 0;
            }
        }else if (view == shouyaoxiaoshouOneOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxiaoshouOne = 1;
            }
        }else if (view == shouyaoxiaoshouOneNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxiaoshouOne = 0;
            }
        }else if (view == shouyaoxiaoshouTwoOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxiaoshouTwo = 1;
            }
        }else if (view == shouyaoxiaoshouTwoNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoxiaoshouTwo = 0;
            }

        }else if (view == shouyaoshengwuzhipinOneOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinOne = 1;
            }
        }
        else if (view == shouyaoshengwuzhipinOneNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinOne = 0;
            }
        } else if (view == shouyaoshengwuzhipinOneUninvolved) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinOne = 2;
            }
        } else if (view == shouyaoshengwuzhipinTwoOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinTwo = 1;
            }
        }else if (view == shouyaoshengwuzhipinTwoNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinTwo = 0;
            }
        }else if (view == shouyaoshengwuzhipinTwoUninvolved) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinTwo = 3;
            }
        }else if (view == shouyaoshengwuzhipinThreeOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinThree = 1;
            }
        }else if (view == shouyaoshengwuzhipinThreeNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinThree = 0;
            }
        }else if (view == shouyaoshengwuzhipinThreeUninvolved) {
            if (isChecked) {
                veterinaryDrugFieldBean.shouyaoshengwuzhipinThree = 3;
            }
        }else if (view == GMPhouxujianguanTwoOk) {
            if (isChecked) {
                veterinaryDrugFieldBean.GMPhouxujianguanTwo = 1;
            }
        }else if (view == GMPhouxujianguanTwoNo) {
            if (isChecked) {
                veterinaryDrugFieldBean.GMPhouxujianguanTwo = 0;
            }
        }
        LogUtil.d("lzx-----》",veterinaryDrugFieldBean.toString());
        return veterinaryDrugFieldBean;
    }

}
