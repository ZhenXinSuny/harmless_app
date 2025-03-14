package com.agridata.cdzhdj.activity.entrycheck.loglist

import android.content.Context
import android.widget.TextView
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogData
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-08 15:44.
 * @Description :描述
 */
class LogEntryCheckAdapter(layoutResId: Int, mContext: Context?):BaseRecyclerViewAdapter<EntryCheckLogData.ResultBean, BaseRecyclerViewHolder>(layoutResId, null) {

    val mContext: Context? = null

    override fun convert(viewHolder: BaseRecyclerViewHolder?, data: EntryCheckLogData.ResultBean?, position: Int) {
        val certNo = viewHolder?.getView<TextView>(R.id.cert_no_tv)
        certNo!!.text= data!!.certNo
        val quantityConformTv = viewHolder.getView<TextView>(R.id.quantity_conform_tv)
        if (data.changeInfo.numberIsPass==1){
            quantityConformTv.text= "是"
        }else{
            quantityConformTv.text= "否"
        }
        val actualQuantityTv = viewHolder.getView<TextView>(R.id.actual_quantity_tv)
        val abnormalQuantityTv = viewHolder.getView<TextView>(R.id.abnormal_quantity_tv)
        val deathsNumTv = viewHolder.getView<TextView>(R.id.deaths_num_tv)
        val changePeopleTv = viewHolder.getView<TextView>(R.id.change_people_tv)
        val changeTimesTv = viewHolder.getView<TextView>(R.id.change_times_tv)
        actualQuantityTv.text = data.changeInfo.actualNumber
        abnormalQuantityTv.text = data.changeInfo.anomalyNumber
        deathsNumTv.text = data.changeInfo.deathNumber
        changePeopleTv.text = data.modifier.name
        changeTimesTv.text = data.changeTime
    }

}