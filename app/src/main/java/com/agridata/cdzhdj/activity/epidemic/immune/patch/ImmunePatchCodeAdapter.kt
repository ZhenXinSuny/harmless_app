package com.agridata.cdzhdj.activity.epidemic.immune.patch

import android.content.Context
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.SPUtil.UserDataSP
import com.agridata.cdzhdj.data.QueryImmuneBean
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-19 11:29.
 * @Description :描述
 */
class ImmunePatchCodeAdapter (layoutResId: Int, mContextActivity: Context?):
    BaseRecyclerViewAdapter<QueryImmuneBean.Result.PageItems, BaseRecyclerViewHolder>(layoutResId, null) {

    val mContext: Context? = mContextActivity

    override fun convert(
        viewHolder: BaseRecyclerViewHolder?,
        data: QueryImmuneBean.Result.PageItems?,
        position: Int
    ) {
        val xdr_tv = viewHolder!!.getView<TextView>(R.id.xdr_tv)
        val fyy_tv = viewHolder.getView<TextView>(R.id.fyy_tv)
        val animal_tv = viewHolder.getView<TextView>(R.id.animal_tv)
        val immune_count_tv = viewHolder.getView<TextView>(R.id.immune_count_tv)
        val times_tv = viewHolder.getView<TextView>(R.id.times_tv)
        val region_tv = viewHolder.getView<TextView>(R.id.region_tv)
        val mylx_tv = viewHolder.getView<TextView>(R.id.mylx_tv)
        if (!TextUtils.isEmpty(data!!.xDRCoreInfo.name)) {
            xdr_tv.text = data.xDRCoreInfo.name
        }

        if (data.isSelfWrite == 1010) {
            fyy_tv.text = data.aHIUser.name
        } else {
            fyy_tv.text = UserDataSP.getInstance().userInfo.Result.name
        }
        animal_tv.text = data.animal.name
        immune_count_tv.text = data.immuneCount.toString()
        times_tv.text = data.immuned
        region_tv.text = data.region.regionFullName

        if (data.immuneType == 1007) {
            mylx_tv.text = "首次免疫"
            mylx_tv.background = AppCompatResources.getDrawable(mContext, R.drawable.bg_immune_patcb_text)
        } else  if (data.immuneType == 1008) {
            mylx_tv.text = "再次免疫"
            mylx_tv.background = AppCompatResources.getDrawable(mContext, R.drawable.bg_immune_patcb_text_again)
        }else{
            mylx_tv.text = "二次饲养"
            mylx_tv.background = AppCompatResources.getDrawable(mContext, R.drawable.bg_immune_patcb_text)
        }


        val mReplaceEarTagsBtn = viewHolder.getView<Button>(R.id.replace_ear_tags_btn)
        mReplaceEarTagsBtn.setOnClickListener {
            mOnItemLabelClickListener?.onItemClick(position)
        }
    }

    private var mOnItemLabelClickListener: OnItemLabelClickListener? = null

    fun setItemLabelClickListener(l: OnItemLabelClickListener?) {
        mOnItemLabelClickListener = l
    }
}