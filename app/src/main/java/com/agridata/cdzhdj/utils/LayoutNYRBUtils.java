package com.agridata.cdzhdj.utils;

import android.view.View;
import android.widget.RadioButton;

import com.agridata.cdzhdj.data.law.PesticideFieldBean;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 16:44.
 * @Description :描述
 */
public class LayoutNYRBUtils {


    public static PesticideFieldBean setRBSeed(PesticideFieldBean pesticideFieldBean , View view,
                                               RadioButton productionAndManagementOneOk, RadioButton productionAndManagementOneNo,
                                               RadioButton productionAndManagementTwoOk, RadioButton sproductionAndManagementTwoNo,
                                               RadioButton productionAndManagementThreeOk, RadioButton productionAndManagementThreeNo,
                                               RadioButton productionAndManagementFourOk, RadioButton productionAndManagementFourNo,
                                               RadioButton chanPinBiaoQianOneOk, RadioButton chanPinBiaoQianOneNo,
                                               RadioButton chanPinBiaoQianTwoOk, RadioButton chanPinBiaoQianTwoNo,
                                               RadioButton chanPinBiaoQianThreeOk, RadioButton chanPinBiaoQianThreeNo,
                                               RadioButton jinXianNongYaoOneOk, RadioButton jinXianNongYaoOneNo,
                                               RadioButton jinXianNongYaoTwoOk, RadioButton jinXianNongYaoTwoNo,
                                               RadioButton taiZhangOk, RadioButton taiZhangNo,
                                               RadioButton wangShangXiaoShouOk, RadioButton wangShangXiaoShouNo,
                                               RadioButton chanPinZhiLiangOneOk, RadioButton chanPinZhiLiangOneNo,
                                               RadioButton chanPinZhiLiangTwoOk, RadioButton chanPinZhiLiangTwoNo) {

        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();

        if (view == productionAndManagementOneOk) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementOne = 1;
            }
        } else if (view == productionAndManagementOneNo) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementOne = 0;
            }
        } else if (view == productionAndManagementTwoOk) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementTwo = 1;
            }
        } else if (view == sproductionAndManagementTwoNo) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementTwo = 0;
            }
        } else if (view == productionAndManagementThreeOk) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementThree = 1;
            }
        } else if (view == productionAndManagementThreeNo) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementThree = 0;
            }
        } else if (view == productionAndManagementFourOk) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementFour = 1;
            }
        } else if (view == productionAndManagementFourNo) {
            if (isChecked) {
                pesticideFieldBean.productionAndManagementFour = 0;
            }
        } else if (view == chanPinBiaoQianOneOk) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianOne = 1;
            }
        } else if (view == chanPinBiaoQianOneNo) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianOne = 0;
            }
        } else if (view == chanPinBiaoQianTwoOk) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianTwo = 1;
            }
        } else if (view == chanPinBiaoQianTwoNo) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianTwo = 0;
            }
        } else if (view == chanPinBiaoQianThreeOk) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianThree = 1;
            }
        } else if (view == chanPinBiaoQianThreeNo) {
            if (isChecked) {
                pesticideFieldBean.chanPinBiaoQianThree = 0;
            }
        } else if (view == jinXianNongYaoOneOk) {
            if (isChecked) {
                pesticideFieldBean.jinXianNongYaoOne = 1;
            }
        } else if (view == jinXianNongYaoOneNo) {
            if (isChecked) {
                pesticideFieldBean.jinXianNongYaoOne = 0;
            }
        } else if (view == jinXianNongYaoTwoOk) {
            if (isChecked) {
                pesticideFieldBean.jinXianNongYaoTwo = 1;
            }
        } else if (view == jinXianNongYaoTwoNo) {
            if (isChecked) {
                pesticideFieldBean.jinXianNongYaoTwo = 0;
            }
        } else if (view == taiZhangOk) {
            if (isChecked) {
                pesticideFieldBean.taiZhang = 1;
            }
        } else if (view == taiZhangNo) {
            if (isChecked) {
                pesticideFieldBean.taiZhang = 0;
            }
        }else if (view == wangShangXiaoShouOk) {
            if (isChecked) {
                pesticideFieldBean.wangShangXiaoShou = 1;
            }
        }else if (view == wangShangXiaoShouNo) {
            if (isChecked) {
                pesticideFieldBean.wangShangXiaoShou = 0;
            }
        }else if (view == chanPinZhiLiangOneOk) {
            if (isChecked) {
                pesticideFieldBean.chanPinZhiLiangOne = 1;
            }
        }else if (view == chanPinZhiLiangOneNo) {
            if (isChecked) {
                pesticideFieldBean.chanPinZhiLiangOne = 0;
            }

        }else if (view == chanPinZhiLiangTwoOk) {
            if (isChecked) {
                pesticideFieldBean.chanPinZhiLiangTwo = 1;
            }
        }else if (view == chanPinZhiLiangTwoNo) {
            if (isChecked) {
                pesticideFieldBean.chanPinZhiLiangTwo = 1;
            }
        }
        LogUtil.d("lzx-----》",pesticideFieldBean.toString());
        return pesticideFieldBean;
    }

}
