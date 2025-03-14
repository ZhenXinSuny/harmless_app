package com.agridata.cdzhdj.view.address;



import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-28 14:16.
 * @Description :描述
 */
public class OptionsAdapter implements WheelAdapter {
    private List<?> datas;

    public OptionsAdapter() {
    }

    public <T> OptionsAdapter(List<T> items) {
        this.datas = items;
    }

    @Override
    public int getItemsCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < datas.size()) {
            return datas.get(index);
        }
        return "";
    }

    @Override
    public int indexOf(Object o) {
        return datas.indexOf(o);
    }

    public <T> void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
