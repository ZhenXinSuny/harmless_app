package com.agridata.cdzhdj.view.address;

import android.text.TextUtils;


import com.agridata.cdzhdj.data.ProvinceData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDialogUtil {


    public static AddressPickerDialog.WrapData<ProvinceData.Data.Children> data1;
    public static Map<String, AddressPickerDialog.WrapData<ProvinceData.Data.Children.ChildrenX>> map2 = new HashMap<>();
    public static Map<String, AddressPickerDialog.WrapData<ProvinceData.Data.Children.ChildrenX.ChildrenXX>> map3 = new HashMap<>();
   // public static Map<String, AddressPickerDialog.WrapData<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean>> map4 = new HashMap<>();
    /**
     * 设置地址信息
     *
     * @param data
     */
    public static void setAddressInfo(List<ProvinceData.Data.Children> data) {
        data1 = new AddressPickerDialog.WrapData<>(data, 0);
        for (int i = 0; i < data.size(); i++) {
            ProvinceData.Data.Children dataBean = data.get(i);
            List<ProvinceData.Data.Children.ChildrenX> children = dataBean.children;
            if (children == null || children.size() == 0) {
                continue;
            }
            //--------------------------------------------
            map2.put(dataBean.toString() + dataBean.hashCode(), new AddressPickerDialog.WrapData<>(children, 0));

            for (int j = 0; j < children.size(); j++) {
                ProvinceData.Data.Children.ChildrenX childrenBeanXX = children.get(j);
                List<ProvinceData.Data.Children.ChildrenX.ChildrenXX> children1 = childrenBeanXX.children;
                if (children1 == null || children1.size() == 0) {
                    continue;
                }
                //--------------------------------------------
                map3.put(childrenBeanXX.toString() + childrenBeanXX.hashCode(),
                        new AddressPickerDialog.WrapData<>(children1,
                                0));

//                for (int k = 0; k < children1.size(); k++) {
//                    ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX childrenBeanX = children1.get(k);
//                    List<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean> children2 = childrenBeanX.getChildren();
//                    if (children2 == null || children2.size() == 0) {
//                        continue;
//                    }
//                    //--------------------------------------------
//                    map4.put(childrenBeanX.toString() + childrenBeanX.hashCode(),
//                            new AddressPickerDialog.WrapData<>(children2, 0));
//                }
            }
        }
    }

    /**
     * 设置地址信息
     * 城市 地区 县  村
     * @param data
     */
    public static void setAddressInfoSelect(List<ProvinceData.Data.Children> data,String city,String area,String county,String village) {
        // 第一列的选中指针
        int selectedItem1 = 0;
        for (int i = 0; i < data.size(); i++) {
            // 第一列item数据
            ProvinceData.Data.Children dataBean = data.get(i);
            // 判断相同，也可以用名称
            if (TextUtils.equals(dataBean.regionName, city)) {
                selectedItem1 = i;
            }
            List<ProvinceData.Data.Children.ChildrenX> children = dataBean.children;
            if (children == null || children.size() == 0) {
                // 第一列item数据，下面没有第二列的子数据，就不需要处理第2,3,4列了，直接返回进行下一个
                continue;
            }
            //--------------------------------------------
            // 第二列的选中指针
            int selectedItem2 = 0;

            for (int j = 0; j < children.size(); j++) {
                // 第二列item数据
                ProvinceData.Data.Children.ChildrenX childrenBeanXX = children.get(j);
                // 判断相同，也可以用名称，注意同名的，比如北京有个朝阳区，天津下面也有个朝阳区，code是唯一的
                if (TextUtils.equals(childrenBeanXX.regionName, area)) {
                    selectedItem2 = j;
                }
                List<ProvinceData.Data.Children.ChildrenX.ChildrenXX> children1 = childrenBeanXX.children;
                if (children1 == null || children1.size() == 0) {
                    continue;
                }
                //--------------------------------------------

                // 第三列的选中指针
                int selectedItem3 = 0;
                for (int k = 0; k < children1.size(); k++) {
                    // 第三列item数据
                    ProvinceData.Data.Children.ChildrenX.ChildrenXX childrenXX = children1.get(k);
                    if (TextUtils.equals(childrenXX.regionName, county)) {
                        selectedItem3 = k;
                    }
//                    List<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX> children2 = childrenBeanX.getChildren();
//                    if (children2 == null || children2.size() == 0) {
//                        continue;
//                    }
                    //--------------------------------------------
                    // 第四列的选中指针
//                    int selectedItem4 = 0;
//                    for (int m = 0; m < children2.size(); m++) {
//                        // 第四列item数据
//                        ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean childrenBean = children2.get(m);
//                        if (TextUtils.equals(childrenBean.getName(), village)) {
//                            selectedItem4 = m;
//                        }
//                    }
//                    map4.put(childrenBeanX.toString() + childrenBeanX.hashCode(), new AddressPickerDialog.WrapData<>(children2, selectedItem4));
                    //  LogUtil.d("AddressDialogUtil-----》",selectedItem4 +  "4");
                }
                map3.put(childrenBeanXX.toString() + childrenBeanXX.hashCode(), new AddressPickerDialog.WrapData<>(children1, selectedItem3));
                // LogUtil.d("AddressDialogUtil-----》",selectedItem3 +  "3");
            }
            map2.put(dataBean.toString() + dataBean.hashCode(), new AddressPickerDialog.WrapData<>(children, selectedItem2));
            //  LogUtil.d("AddressDialogUtil-----》",selectedItem2 +  "2");
        }
        // 第一列的数据
        data1 = new AddressPickerDialog.WrapData<>(data, selectedItem1);
        //  LogUtil.d("AddressDialogUtil-----》",selectedItem1 +  "1");
    }
}
