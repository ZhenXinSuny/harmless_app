package com.agridata.cdzhdj.activity.epidemic.immune.patch

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-19 16:24.
 * @Description :描述
 */
class ImmunePatchCodeCommitAdapter  (layoutResId: Int, mContextActivity: Context?):
    BaseRecyclerViewAdapter<String, BaseRecyclerViewHolder>(layoutResId, null) {

    val mContext: Context? = mContextActivity

    override fun convert(
        viewHolder: BaseRecyclerViewHolder?,
        data: String?,
        position: Int
    ) {
        val codeTv = viewHolder!!.getView<TextView>(R.id.code_tv)
        codeTv.text = data
        val deleteIv = viewHolder.getView<ImageView>(R.id.delete_iv)
        deleteIv.setOnClickListener { mOnDelItemClickListener?.onDelItemClick(position)}
    }
    private var mOnDelItemClickListener: OnDelItemClickListener? = null

    fun setDelItemClickListener(l: OnDelItemClickListener?) {
        mOnDelItemClickListener = l
    }
}