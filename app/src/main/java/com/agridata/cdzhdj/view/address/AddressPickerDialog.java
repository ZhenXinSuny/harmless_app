package com.agridata.cdzhdj.view.address;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ProvinceData;
import com.agridata.cdzhdj.data.law.EnforcementData;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-28 14:15.
 * @Description :描述
 */
public class AddressPickerDialog <T1, T2, T3, T4> extends Dialog {
    private WheelView wvOption1;
    private WheelView wvOption2;
    private WheelView wvOption3;
    private WheelView wvOption4;

    private OptionsAdapter adapter1;
    private OptionsAdapter adapter2;
    private OptionsAdapter adapter3;
    private OptionsAdapter adapter4;
    /**
     * 所有的数据缓存，用于级联效果
     */
    private Map<String, WrapData<ProvinceData.Data.Children.ChildrenX>> map2 = new HashMap<>();
    private Map<String, WrapData<ProvinceData.Data.Children.ChildrenX.ChildrenXX>> map3 = new HashMap<>();
    //private Map<String, WrapData<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean>> map4 = new HashMap<>();

    /**
     * 显示的数据
     */
    private WrapData<ProvinceData.Data.Children> data1;
    private WrapData<ProvinceData.Data.Children.ChildrenX> data2;
    private WrapData<ProvinceData.Data.Children.ChildrenX.ChildrenXX> data3;
   // private WrapData<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean> data4;
    private TextView cancel_button;
    private TextView confirm_button;
    private int id;
    private String address;
    private int type;
    private EnforcementData.RegionBean regionBean  = new EnforcementData.RegionBean();

    public AddressPickerDialog(@NonNull Context context) {
        super(context);
        initWindows();
    }

    public AddressPickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initWindows();
    }

    protected AddressPickerDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initWindows();
    }

    private void initWindows() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.windowAnimations = R.style.custom_dialog;
        window.getDecorView().setBackgroundColor(Color.WHITE);
        window.setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_address_picker_picker, null);
        initView(view);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        setContentView(view, params);
    }

    private void initView(View view) {
        wvOption1 = view.findViewById(R.id.wheel_view_1);
        wvOption2 = view.findViewById(R.id.wheel_view_2);
        wvOption3 = view.findViewById(R.id.wheel_view_3);
        wvOption4 = view.findViewById(R.id.wheel_view_4);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);
        // 设置UI样式
        initSet();

        adapter1 = new OptionsAdapter();
        adapter2 = new OptionsAdapter();
        adapter3 = new OptionsAdapter();
        adapter4 = new OptionsAdapter();

        wvOption1.setAdapter(adapter1);
        wvOption2.setAdapter(adapter2);
        wvOption3.setAdapter(adapter3);
        wvOption4.setAdapter(adapter4);

        wvOption1.setOnItemSelectedListener(listener1);
        wvOption2.setOnItemSelectedListener(listener2);
        wvOption3.setOnItemSelectedListener(listener3);
        wvOption4.setOnItemSelectedListener(listener4);


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                onAddressChangeListener.onAddressChange(address, id, type,regionBean);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressChangeListener.onDismiss(type);
            }
        });
    }

    private OnAddressChangeListener onAddressChangeListener = null;

    public void setOnAddressChangeListener(OnAddressChangeListener onAddressChangeListener) {
        this.onAddressChangeListener = onAddressChangeListener;
    }

    /**
     * 设置UI样式
     */
    private void initSet() {
        wvOption1.setIsOptions(true);
        wvOption2.setIsOptions(true);
        wvOption3.setIsOptions(true);
        wvOption4.setIsOptions(true);

        wvOption1.setCyclic(false);
        wvOption2.setCyclic(false);
        wvOption3.setCyclic(false);
        wvOption4.setCyclic(false);
        // 字体大小
        wvOption1.setTextSize(17);
        wvOption2.setTextSize(17);
        wvOption3.setTextSize(17);
        wvOption4.setTextSize(17);

        wvOption1.isCenterLabel(true);
        wvOption2.isCenterLabel(true);
        wvOption3.isCenterLabel(true);
        wvOption4.isCenterLabel(true);


        wvOption1.setAlphaGradient(true);
        wvOption2.setAlphaGradient(true);
        wvOption3.setAlphaGradient(true);
        wvOption4.setAlphaGradient(true);

        wvOption1.setLineSpacingMultiplier(2.6F);
        wvOption2.setLineSpacingMultiplier(2.6F);
        wvOption3.setLineSpacingMultiplier(2.6F);
        wvOption4.setLineSpacingMultiplier(2.6F);
        // ....
    }

    public void setData(@NonNull WrapData<ProvinceData.Data.Children> data1,
                        Map<String, WrapData<ProvinceData.Data.Children.ChildrenX>> map2,
                        Map<String, WrapData<ProvinceData.Data.Children.ChildrenX.ChildrenXX>> map3
                       ) {
        this.map2 = map2;
        this.map3 = map3;
       // this.map4 = map4;
        this.data1 = data1;

        // 1
        int selectedItemIndex1 = data1.getSelectedItemIndex();
        adapter1.setDatas(data1.getItems());
        wvOption1.setCurrentItem(selectedItemIndex1);
        ProvinceData.Data.Children selectedItem1 = this.data1.getItems().get(selectedItemIndex1);

      this.data1.getItems().get(selectedItemIndex1);
        initData2(selectedItem1.toString() + selectedItem1.hashCode());
    }

    private void initData2(String key) {
        WrapData< ProvinceData.Data.Children.ChildrenX> data2 = map2.get(key);
        if (data2 == null) {
            wvOption2.setVisibility(View.INVISIBLE);
            wvOption3.setVisibility(View.INVISIBLE);
            wvOption4.setVisibility(View.INVISIBLE);
            return;
        }
        this.data2 = data2;
        // 2
        wvOption2.setVisibility(View.VISIBLE);
        int selectedItemIndex2 = data2.getSelectedItemIndex();

        adapter2.setDatas(data2.getItems());
        wvOption2.setCurrentItem(selectedItemIndex2);

        // data3
        ProvinceData.Data.Children.ChildrenX selectedItem2 = data2.getItems().get(selectedItemIndex2);
        initData3(selectedItem2.toString() + selectedItem2.hashCode());
    }

    private void initData3(String key) {
        WrapData<ProvinceData.Data.Children.ChildrenX.ChildrenXX> data3 = map3.get(key);
        if (data3 == null) {
            wvOption3.setVisibility(View.INVISIBLE);
            wvOption4.setVisibility(View.INVISIBLE);
            return;
        }
        this.data3 = data3;
        // 3
        wvOption3.setVisibility(View.VISIBLE);
        int selectedItemIndex3 = data3.getSelectedItemIndex();
        adapter3.setDatas(data3.getItems());
        wvOption3.setCurrentItem(selectedItemIndex3);

        // data4
//        ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX selectedItem3 = data3.getItems().get(selectedItemIndex3);
//        initData4(selectedItem3.toString() + selectedItem3.hashCode());
    }

//    private void initData4(String key) {
//        WrapData<ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean> data4 = map4.get(key);
//        if (data4 == null) {
//            wvOption4.setVisibility(View.GONE);
//            return;
//        }
//        this.data4 = data4;
//        //4
//        wvOption4.setVisibility(View.VISIBLE);
//        int selectedItemIndex4 = data4.getSelectedItemIndex();
//        adapter4.setDatas(data4.getItems());
//        wvOption4.setCurrentItem(selectedItemIndex4);
//        wvOption4.setAdapter(adapter4);
//    }

    public static class WrapData<T> {
        private List<T> items;
        private int selectedItem;

        /**
         * @param items        大小最小为1
         * @param selectedItem 选中的指针
         */
        public WrapData(@NonNull List<T> items, int selectedItem) {
            this.items = items;
            int size = items.size();
            if (selectedItem > size - 1) {
                selectedItem = size - 1;
            }
            if (selectedItem < 0) {
                selectedItem = 0;
            }
            this.selectedItem = selectedItem;
        }

        public List<T> getItems() {
            return items;
        }

        public int getSelectedItemIndex() {
            return selectedItem;
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }

        @Override
        public String toString() {
            return items.toString();
        }
    }

    /**
     * 联动监听器
     */
    OnItemSelectedListener listener1 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(int index) {
            data1.setSelectedItem(index);
            ProvinceData.Data.Children selectedItem1 = data1.getItems().get(index);
            initData2(selectedItem1.toString() + selectedItem1.hashCode());
        }
    };

    OnItemSelectedListener listener2 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(int index) {
            data2.setSelectedItem(index);
            ProvinceData.Data.Children.ChildrenX selectedItem2 = data2.getItems().get(index);
            initData3(selectedItem2.toString() + selectedItem2.hashCode());
        }
    };

    OnItemSelectedListener listener3 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(int index) {
            data3.setSelectedItem(index);
            ProvinceData.Data.Children.ChildrenX.ChildrenXX selectedItem3 = data3.getItems().get(index);
            //initData4(selectedItem3.toString() + selectedItem3.hashCode());
        }
    };

    OnItemSelectedListener listener4 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(int index) {
          //  data4.setSelectedItem(index);
        }
    };

    /**
     * 点击确定调用此方法
     */
    public void submit() {
        // 省
        ProvinceData.Data.Children t1 = data1.getItems().get(data1.getSelectedItemIndex());
        // 市
        ProvinceData.Data.Children.ChildrenX t2 = data2.getItems().get(data2.getSelectedItemIndex());
        // 县
        ProvinceData.Data.Children.ChildrenX.ChildrenXX t3 = data3.getItems().get(data3.getSelectedItemIndex());
        // 乡
       // ProvinceData.DataBean.ChildrenBeanXX.ChildrenBeanX.ChildrenBean t4 = data4.getItems().get(data4.getSelectedItemIndex());
        onItemSelected(t1,t2,t3);
    }

    /**
     * 回调
     */
    private void onItemSelected( ProvinceData.Data.Children t1,  ProvinceData.Data.Children.ChildrenX t2, ProvinceData.Data.Children.ChildrenX.ChildrenXX t3) {
        Log.i("xxxx", "t1" + t1.toString() + t1.getClass().getSimpleName());
        Log.i("xxxx", "t2" + t2.toString() + t2.getClass().getSimpleName());
        Log.i("xxxx", "t3" + t3.toString() + t3.getClass().getSimpleName());
        address = t1.toString() + "-" + t2.toString() + "-" + t3.toString();
        id = t3.iD;
        regionBean.id =  t3.iD;
        regionBean.RegionCode = String.valueOf(t3.iD);
        regionBean.RegionName = t3.regionName;
        regionBean.RegionLevel = t3.regionLevel;
        regionBean.RegionFullName = t3.regionFullName;
        regionBean.RegionParentID = t2.iD;
        regionBean.RI1=2293;
        regionBean.RI2=t1.iD;
        regionBean.RI3=t2.iD;
        regionBean.RI4=t3.iD;
        regionBean.RI5=-1;
    }


}
