package com.agridata.cdzhdj.activity.epidemic.immune.patch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.data.EarTagStartEndNumBean
import com.agridata.cdzhdj.data.immune.EarTagIsInHistoryData
import com.agridata.cdzhdj.databinding.ActivityManualentryEartagBinding
import com.agridata.cdzhdj.net.HttpApi.HttpRequest
import com.agridata.cdzhdj.utils.RxBus
import com.agridata.cdzhdj.view.CustomLoadingDialog
import com.agridata.network.listener.CallBackLis
import com.lzx.utils.RxToast
import com.lzx.utils.RxToast.error
import com.lzx.utils.RxToast.info
import com.lzx.utils.RxToast.warning
import java.util.Objects

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-19 17:09.
 * @Description :描述
 */
class ImmunePatchEarTagActivity : BaseActivity<ActivityManualentryEartagBinding>() {
    private var mLoadingDialog: CustomLoadingDialog? = null
    private var earTagCodeLength: Int? = null
    private var maxCodeLength: Int? = null
    private var mAnimalName: String? = null
    private var animalCode: String? = null

    private var max: Int? = null

    companion object {
        fun start(
            context: Context,
            earTagCodeLength: Int,
            animalName: String,
            maxCodeLength: Int,
            animalCode: String
        ) {
            val intent = Intent(context, ImmunePatchEarTagActivity::class.java)
            val data = Bundle()
            data.putInt("earTagCodeLength", earTagCodeLength)
            data.putInt("maxCodeLength", maxCodeLength)
            data.putString("mAnimalName", animalName)
            data.putString("animalCode", animalCode)
            intent.putExtra("data", data)
            context.startActivity(intent)
        }
    }

    override fun getBinding(): ActivityManualentryEartagBinding {
        return ActivityManualentryEartagBinding.inflate(layoutInflater)
    }

    private fun getArguments() {
        val bundle = intent.getBundleExtra("data")!!
        earTagCodeLength = bundle.getInt("earTagCodeLength")
        mAnimalName = bundle.getString("mAnimalName")
        maxCodeLength = bundle.getInt("maxCodeLength")
        animalCode = bundle.getString("animalCode")
    }

    override fun initView() {
        getArguments()
        initLoadingView()
        binding.titlebarMiddle.text ="手动录入补戴耳标"
        binding.addEartagType.text = Html.fromHtml(
            String.format(
                "请录入<font color=red>%s</font>耳标起始和终止号段", mAnimalName
            )
        )
        max = maxCodeLength
        binding.eartagLength.text = Html.fromHtml(
            String.format(
                "当前最多还可输入<font color=red>%d</font>个耳标记录", max!! - earTagCodeLength!!
            )
        )
        binding.titlebarLeft.setOnClickListener { finish() }
        binding.sureBtn.setOnClickListener { setEarTagNum() }
    }

    override fun initDatas() {

    }

    private fun setEarTagNum() {
        val num = binding.addEartagNum.text.toString().trim()
        val start = binding.addEartagStart.text.toString().trim()
        val end = binding.addEartagEnd.text.toString().trim()
        val substring = num.substring(0, 1)
        if (num.length == 7 && start.length == 8 && end.length == 8) {
            val n = num.toInt()
            val s = start.toInt()
            val e = end.toInt()
            if (e < s) {
                Objects.requireNonNull<Toast?>(
                    error(
                        this@ImmunePatchEarTagActivity, "起始号段不能大于结束号段"
                    )
                )
            } else if (e - s + 1 > max!! - earTagCodeLength!!) {
                Objects.requireNonNull<Toast?>(
                    warning(
                        this@ImmunePatchEarTagActivity,
                        "当前最多可发放" + (max!! - earTagCodeLength!!) + "耳标记录"
                    )
                )
            } else {
                if (substring == animalCode) {
                    if (1 == animalCode!!.toInt()) {
                        val stringStart = num + start
                        val stringEnd = num + end
                        showLoading("耳标库存查询中...")
                        HttpRequest.queryEarTagIsInHistory(this@ImmunePatchEarTagActivity,
                            stringStart,
                            stringEnd,
                            object : CallBackLis<EarTagIsInHistoryData> {
                                override fun onSuccess(
                                    method: String?, content: EarTagIsInHistoryData
                                ) {
                                    hideLoading()
                                    if (content.code == 200) {
                                        if (content.data.isEmpty()) {
                                            val earTagStartEndNumBean = EarTagStartEndNumBean()
                                            earTagStartEndNumBean.Num = n
                                            earTagStartEndNumBean.StartNum = s
                                            earTagStartEndNumBean.EndNum = e
                                            RxBus.getInstance()
                                                .post("eartag_num_edit", earTagStartEndNumBean)
                                            finish()
                                        } else {
                                            Objects.requireNonNull(
                                                warning(
                                                    this@ImmunePatchEarTagActivity,
                                                    "[" + content.data[0].lower + "-" + content.data[0].upper + "]号段不在库存中，请重新填写"
                                                )
                                            )
                                        }
                                    }
                                }

                                override fun onFailure(method: String, error: String) {
                                    hideLoading()
                                }
                            })
                    } else {
                        val earTagStartEndNumBean = EarTagStartEndNumBean()
                        earTagStartEndNumBean.Num = n
                        earTagStartEndNumBean.StartNum = s
                        earTagStartEndNumBean.EndNum = e
                        RxBus.getInstance().post("eartag_num_edit", earTagStartEndNumBean)
                        finish()
                    }
                } else {
                    Objects.requireNonNull<Toast?>(
                        error(
                            this@ImmunePatchEarTagActivity, "耳标号段与动物种类不匹配，请重新输入"
                        )
                    )
                }
            }
        } else {
            Objects.requireNonNull<Toast?>(
                error(
                    this@ImmunePatchEarTagActivity, "号码长度输入有误,请输入完整的号段"
                )
            )
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