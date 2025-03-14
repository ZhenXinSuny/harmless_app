package com.agridata.cdzhdj.activity.harmless.xcsh

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter
import com.agridata.cdzhdj.activity.epidemic.xdr.CheckXdrListActivity
import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.data.MenuData
import com.agridata.cdzhdj.databinding.ActivityOnSiteAuditHarmlessBinding
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-11-20 09:51.
 * @Description :描述
 */
class OnSiteAuditHarmlessActivity : BaseActivity<ActivityOnSiteAuditHarmlessBinding>() {
    private var mMenuDataList: MutableList<MenuData> = mutableListOf()
    private lateinit var mainMenuAdapter: MainMenuAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OnSiteAuditHarmlessActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getBinding(): ActivityOnSiteAuditHarmlessBinding {
        return ActivityOnSiteAuditHarmlessBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.titlebarLeft.setOnClickListener { finish() }
        setMenuData()
        setMenuUi()
    }

    override fun initDatas() {

    }

    private fun setMenuData() {
        mMenuDataList = mutableListOf(
            MenuData("养殖场信息备案审核", R.drawable.ic_immune_tianbao, 4),
            MenuData("无害化收集申报审核", R.drawable.ic_immune_query, 5),
        )
    }

    private fun setMenuUi() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerview.layoutManager = layoutManager
        mainMenuAdapter = MainMenuAdapter(R.layout.item_menu, mMenuDataList, this, layoutManager)
        binding.recyclerview.adapter = mainMenuAdapter
        mainMenuAdapter.refreshDataList(mMenuDataList)
        mainMenuAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View, viewHolder: BaseRecyclerViewHolder, position: Int
            ) {
                val data = mainMenuAdapter.getData(position)
                when (data.id) {
                    4 -> CheckXdrListActivity.start(this@OnSiteAuditHarmlessActivity,"whh")
                    5 -> XianChangShenHeWebViewActivity.start(this@OnSiteAuditHarmlessActivity)
                }
            }

            override fun onItemLongClick(
                view: View, viewHolder: BaseRecyclerViewHolder, position: Int
            ): Boolean {
                return false
            }
        })
    }
}