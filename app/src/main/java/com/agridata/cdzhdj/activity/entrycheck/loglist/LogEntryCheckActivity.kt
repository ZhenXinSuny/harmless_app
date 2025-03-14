package com.agridata.cdzhdj.activity.entrycheck.loglist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.agridata.cdzhdj.net.HttpApi.HttpRequest
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogData
import com.agridata.cdzhdj.databinding.ActivityLogEntryCheckBinding
import com.agridata.cdzhdj.view.CustomLoadingDialog
import com.agridata.network.listener.CallBackLis

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-08 15:05.
 * @Description :描述
 */
class LogEntryCheckActivity : BaseActivity<ActivityLogEntryCheckBinding>() {

    private var mid: String? = null
    private var mLoadingDialog: CustomLoadingDialog? = null
    private var logEntryCheckAdapter: LogEntryCheckAdapter? = null

    companion object {
        fun start(context: Context, mid: String) {
            val intent = Intent(context, LogEntryCheckActivity::class.java)
            val data = Bundle()
            data.putString("mid", mid)
            intent.putExtra("data", data)
            context.startActivity(intent)
        }
    }

    private fun getArguments() {
        val bundle = intent.getBundleExtra("data")!!
        mid = bundle.getString("mid")

    }

    override fun getBinding(): ActivityLogEntryCheckBinding {
        return ActivityLogEntryCheckBinding.inflate(layoutInflater)
    }

    override fun initView() {
        getArguments()
        initLoadingView()
        binding.titlebarLeft.setOnClickListener {
            finish()
        }
        binding.recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        logEntryCheckAdapter = LogEntryCheckAdapter(R.layout.item_log_entry_check, this)
        binding.recyclerView.setAdapter(logEntryCheckAdapter)

    }

    override fun initDatas() {
        getLogEntryInfo()
    }

    private fun getLogEntryInfo(){
        showLoading("加载中...")
        HttpRequest.getEntryCheckLog(this@LogEntryCheckActivity, mid,
                object : CallBackLis<EntryCheckLogData> {
                    override fun onSuccess(method: String?, content: EntryCheckLogData) {
                        hideLoading()
                        if (content.errorCode==0){
                            if (content.result.isNotEmpty()){
                               logEntryCheckAdapter!!.refreshDataList(content.result)
                            }
                        }
                    }
                    override fun onFailure(method: String?, error: String?) {
                        hideLoading()
                    }
                })
    }


    private fun initLoadingView() {
        this.mLoadingDialog = CustomLoadingDialog(this)
        this.mLoadingDialog!!.setCanceledOnTouchOutside(false)
    }


    /**
     * 显示加载框
     */
    fun showLoading(tips: String?) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog!!.isShowing) {
            this.mLoadingDialog!!.show()
            this.mLoadingDialog!!.setTitleVisibility(0)
            this.mLoadingDialog!!.setTitle(tips)
        }
    }

    /**
     * 隐藏 加载框
     */
    fun hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog!!.isShowing) {
            this.mLoadingDialog!!.hide()
        }
    }
}