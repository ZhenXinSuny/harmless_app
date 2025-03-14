package com.agridata.cdzhdj.activity.epidemic.xdr;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-09 14:04.
 * @Description :描述
 */
public interface OnXdrCheckListener {
    /**
     *  点击审核按钮 回调Mid。进行 xdr资源更新
     * @param mid
     *  @param pos
     */
    void check(String mid, int pos);


    void  reject(String mid, int pos);
}
