package com.agridata.cdzhdj.activity.epidemic.immune.inventory

import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.databinding.ActivityInventoryEarTagSelectionBinding


/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2025-03-06 17:56.
 * @Description :描述 库存耳标选择
 */
class InventoryEarTagSelectionActivity : BaseActivity<ActivityInventoryEarTagSelectionBinding>() {


    override fun getBinding(): ActivityInventoryEarTagSelectionBinding {
        return ActivityInventoryEarTagSelectionBinding.inflate(layoutInflater)
    }
    override fun initView() {
        binding.titlebarLeft.setOnClickListener{finish()}
    }

    override fun initDatas() {

    }
}