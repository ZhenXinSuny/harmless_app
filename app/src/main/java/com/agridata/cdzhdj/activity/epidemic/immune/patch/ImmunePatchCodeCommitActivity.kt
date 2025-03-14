package com.agridata.cdzhdj.activity.epidemic.immune.patch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.data.EarTagStartEndNumBean
import com.agridata.cdzhdj.data.StatusData
import com.agridata.cdzhdj.data.immune.ImmuneDetails
import com.agridata.cdzhdj.databinding.ActivityImmunePatchCodeDetailsBinding
import com.agridata.cdzhdj.net.HttpApi.HttpRequest
import com.agridata.cdzhdj.view.CustomLoadingDialog
import com.agridata.network.listener.CallBackLis
import com.agridata.network.utils.LogUtil
import com.lzx.utils.RxToast
import java.util.Objects


/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-19 14:48.
 * @Description :描述
 */
class ImmunePatchCodeCommitActivity : BaseActivity<ActivityImmunePatchCodeDetailsBinding>(),
    OnDelItemClickListener {
    private var mLoadingDialog: CustomLoadingDialog? = null
    private var mid: String? = null
    private var mImmuneDetails: ImmuneDetails? = null
    private var immunePatchCodeDetailsAdapter: ImmunePatchCodeCommitAdapter? = null
    private var earTagStartEndNumBean: EarTagStartEndNumBean? = null
    private var replenishEarTagCodeInfo: String? = null

    companion object {
        fun start(context: Context, mid: String) {
            val intent = Intent(context, ImmunePatchCodeCommitActivity::class.java)
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

    override fun getBinding(): ActivityImmunePatchCodeDetailsBinding {
        return ActivityImmunePatchCodeDetailsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        getArguments()
        initLoadingView()
        binding.recyclerView.setLayoutManager(
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
        )
        immunePatchCodeDetailsAdapter =
            ImmunePatchCodeCommitAdapter(R.layout.item_immune_patch_code_details, this)
        binding.recyclerView.setAdapter(immunePatchCodeDetailsAdapter)
        immunePatchCodeDetailsAdapter!!.setDelItemClickListener(this@ImmunePatchCodeCommitActivity)
        binding.editCodeBtn.setOnClickListener {
            ImmunePatchEarTagActivity.start(
                this@ImmunePatchCodeCommitActivity,
                getDataListSize(),
                mImmuneDetails!!.Result.Dep_AnimalID.AnimalName,
                mImmuneDetails!!.Result.ImmuneCount,
                mImmuneDetails!!.Result.Dep_AnimalID.EartagCode.toString()
            )
        }

        binding.commitBt.setOnClickListener {
            if (immunePatchCodeDetailsAdapter!!.dataList.isNotEmpty()) {
                commitReplenishCode()
            }
        }
    }

    private fun commitReplenishCode() {
        showLoading("补戴耳标上传中...")
        val dataList = immunePatchCodeDetailsAdapter!!.dataList
        //添加现有耳标add
        val split = mImmuneDetails!!.Result.EarTags.split(",").toMutableList()
        dataList.forEach { item ->
            split.add(item.toString())
        }

        //添加现有戴标
        replenishEarTagCodeInfo = if (mImmuneDetails?.Result?.ReplenishEarTagCode.isNullOrEmpty()) {
            dataList.joinToString(",")
        }else{
            val replenishEarTagCodeList =
                mImmuneDetails!!.Result.ReplenishEarTagCode.split(",").toMutableList()
            dataList.forEach { item ->
                replenishEarTagCodeList.add(item.toString())
            }
            replenishEarTagCodeList.joinToString(",")
        }
        HttpRequest.commitImmunePatchEarTagCode(this@ImmunePatchCodeCommitActivity,
            mImmuneDetails!!.Result.Mid,
            split.joinToString(","),
            replenishEarTagCodeInfo,split.size.toString(),
            object : CallBackLis<StatusData> {
                override fun onSuccess(method: String?, content: StatusData) {
                    hideLoading()
                    if (content.ErrorCode == 0) {
                        Objects.requireNonNull(
                            RxToast.success(this@ImmunePatchCodeCommitActivity, "补戴耳标上传成功")
                        )
                        finish()
                    }
                }
                override fun onFailure(method: String, error: String) {
                    hideLoading()
                    Objects.requireNonNull(
                        RxToast.error(this@ImmunePatchCodeCommitActivity, error)
                    )
                }
            })
    }

    private fun getDataListSize(): Int {
        return if (immunePatchCodeDetailsAdapter?.dataList?.isNotEmpty() == true) {
            immunePatchCodeDetailsAdapter!!.dataList.size
        } else {
            0
        }
    }

    override fun initDatas() {
        getImmuneDetails()
    }

    private fun getImmuneDetails() {
        HttpRequest.getImmuneDetails(this@ImmunePatchCodeCommitActivity,
            mid,
            object : CallBackLis<ImmuneDetails> {
                override fun onSuccess(method: String?, content: ImmuneDetails) {
                    mImmuneDetails = content
                    val message = getString(
                        R.string.the_number_of_ear_tags_to_be_replaced_cannot_exceed,
                        content.Result.ImmuneCount
                    )
                    val spannableString = SpannableString(message)
                    // 查找数字的位置
                    val numberStartIndex = message.indexOf(content.Result.ImmuneCount.toString())
                    val numberEndIndex =
                        numberStartIndex + content.Result.ImmuneCount.toString().length
                    // 设置数字的颜色
                    spannableString.setSpan(
                        ForegroundColorSpan(Color.RED), // 你可以设置任何颜色
                        numberStartIndex, numberEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.tips.text = spannableString
                }

                override fun onFailure(method: String, error: String) {

                }
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun OnEventMainThread() {
        super.OnEventMainThread()
        mRxManager.on("eartag_num_edit") { o: Any ->
            earTagStartEndNumBean = o as EarTagStartEndNumBean
            LogUtil.d(
                "pageItemsBean" + earTagStartEndNumBean.toString()
            )
            for (i in 0 until earTagStartEndNumBean!!.EndNum - earTagStartEndNumBean!!.StartNum + 1) {
                var flag = false
                val eartagNo = String.format(
                    "%d%08d", earTagStartEndNumBean!!.Num, earTagStartEndNumBean!!.StartNum + i
                )
                for (t in immunePatchCodeDetailsAdapter!!.dataList) {
                    if (eartagNo == t) {
                        flag = true
                        break
                    }
                }
                if (!flag) {
                    immunePatchCodeDetailsAdapter!!.dataList.add(eartagNo)

                }
            }
            binding.notDataRl.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.commitBt.visibility = View.VISIBLE
            immunePatchCodeDetailsAdapter!!.notifyDataSetChanged()

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDelItemClick(position: Int) {
        immunePatchCodeDetailsAdapter!!.removeData(position)
        immunePatchCodeDetailsAdapter!!.notifyDataSetChanged()
        if (immunePatchCodeDetailsAdapter!!.dataList.isNullOrEmpty()) {
            binding.commitBt.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.notDataRl.visibility = View.VISIBLE
        }
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