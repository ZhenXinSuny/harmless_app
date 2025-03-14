package com.agridata.cdzhdj.utils;

import android.view.View;
import android.widget.RadioButton;

import com.agridata.cdzhdj.data.law.SeedFieldBean;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 16:44.
 * @Description :描述
 */
public class LayoutSeedRBUtils {


    public static SeedFieldBean setRBSeed(SeedFieldBean seedField , View view, RadioButton infringePlantOk, RadioButton infringePlantNo,
                                          RadioButton fakePlantOk, RadioButton fakePlantNo,
                                          RadioButton registerOk, RadioButton registerNo,
                                          RadioButton bao_zhaungOk, RadioButton bao_zhaungNo,
                                          RadioButton biaoqianOk, RadioButton biaoqianNo,
                                          RadioButton biaoqian_neirongOk, RadioButton biaoqian_neirongNo,
                                          RadioButton shengchandanganOk, RadioButton shengchandanganNo,
                                          RadioButton jingyingdanganOK, RadioButton jingyingdanganNo,
                                          RadioButton beianOK, RadioButton beianNo,
                                          RadioButton chouyangOK, RadioButton chouyangNo) {

        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();



        if (view == infringePlantOk) {
            LogUtil.d("lzx----》","是否侵犯植物新品种权 ");
            if (isChecked) {
                seedField.infringePlant = 1;
            }
        } else if (view == infringePlantNo) {
            if (isChecked) {
                seedField.infringePlant = 0;
            }
        } else if (view == fakePlantOk) {
            if (isChecked) {
                seedField.fakePlant = 1;
            }
        } else if (view == fakePlantNo) {
            if (isChecked) {
                seedField.fakePlant = 0;
            }
        } else if (view == registerOk) {
            if (isChecked) {
                seedField.dengJi = 1;
            }
        } else if (view == registerNo) {
            if (isChecked) {
                seedField.dengJi = 0;
            }
        } else if (view == bao_zhaungOk) {
            if (isChecked) {
                seedField.baozhaung = 1;
            }
        } else if (view == bao_zhaungNo) {
            if (isChecked) {
                seedField.baozhaung = 0;
            }
        } else if (view == biaoqianOk) {
            if (isChecked) {
                seedField.biaoqian = 1;
            }
        } else if (view == biaoqianNo) {
            if (isChecked) {
                seedField.biaoqian = 0;
            }
        } else if (view == biaoqian_neirongOk) {
            if (isChecked) {
                seedField.biaoqianneirong = 1;
            }
        } else if (view == biaoqian_neirongNo) {
            if (isChecked) {
                seedField.biaoqianneirong = 0;
            }
        } else if (view == shengchandanganOk) {
            if (isChecked) {
                seedField.shengchandangan = 1;
            }
        } else if (view == shengchandanganNo) {
            if (isChecked) {
                seedField.shengchandangan = 0;
            }
        } else if (view == jingyingdanganOK) {
            if (isChecked) {
                seedField.jingyingdangan = 1;
            }
        } else if (view == jingyingdanganNo) {
            if (isChecked) {
                seedField.jingyingdangan = 0;
            }
        } else if (view == beianOK) {
            if (isChecked) {
                seedField.beian = 1;
            }
        } else if (view == beianNo) {
            if (isChecked) {
                seedField.beian = 0;
            }
        } else if (view == chouyangOK) {
            if (isChecked) {
                seedField.songjian = 1;
            }
        } else if (view == chouyangNo) {
            if (isChecked) {
                seedField.songjian = 0;
            }
        }
        LogUtil.d("lzx-----》",seedField.toString());
        return seedField;
    }
}
