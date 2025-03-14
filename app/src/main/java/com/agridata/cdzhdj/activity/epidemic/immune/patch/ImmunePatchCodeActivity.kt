package com.agridata.cdzhdj.activity.epidemic.immune.patch

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.agridata.cdzhdj.R
import com.agridata.cdzhdj.SPUtil.UserDataSP
import com.agridata.cdzhdj.activity.epidemic.immune.query.QueryImmuneDetailsActivity
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity
import com.agridata.cdzhdj.base.BaseActivity
import com.agridata.cdzhdj.dao.TRegionDao
import com.agridata.cdzhdj.data.QueryImmuneBean
import com.agridata.cdzhdj.databinding.ActivityImmunePatchCodeBinding
import com.agridata.cdzhdj.dbutils.DaoManager
import com.agridata.cdzhdj.net.HttpApi.HttpRequest
import com.agridata.cdzhdj.utils.AppConstants
import com.agridata.cdzhdj.utils.MoveToPosUtils
import com.agridata.cdzhdj.view.CustomLoadingDialog
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder
import com.agridata.network.listener.CallBackLis
import com.agridata.network.utils.LogUtil
import com.lzx.utils.RxToast
import java.util.Objects


/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-16 17:22.
 * @Description :描述
 */
class ImmunePatchCodeActivity : BaseActivity<ActivityImmunePatchCodeBinding>(),
    OnItemLabelClickListener {
    private var mIsSelfWrite = 0
    private var mRegionID = 0
    private var mRegionLevel: Int = 0
    private var mMobile: String? = null
    private val mXdrMid: String? = null
    private val mAnimalID: String? = null
    private var page = 0
    private val size = 5
    private var mLoadingDialog: CustomLoadingDialog? = null
    private var immunePatchCodeAdapter: ImmunePatchCodeAdapter? = null
    private var mDrawer: View? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ImmunePatchCodeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getBinding(): ActivityImmunePatchCodeBinding {
        return ActivityImmunePatchCodeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initLoadingView()
        mDrawer = findViewById(R.id.nar_drawer_immune_xdr)
        binding.queryTv.setOnClickListener {
            showDrawer()
        }
        binding.titleBarLeft.setOnClickListener { finish() }
        binding.recyclerView.setLayoutManager(
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
        )
        immunePatchCodeAdapter = ImmunePatchCodeAdapter(R.layout.item_immune_patch, this)
        binding.recyclerView.setAdapter(immunePatchCodeAdapter)
        immunePatchCodeAdapter!!.setItemLabelClickListener(this@ImmunePatchCodeActivity)
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        binding.refreshLayout.setOnRefreshListener {
            page = 0
            getRole()
        }

        //加载的监听事件
        binding.refreshLayout.setOnLoadMoreListener {
            LogUtil.d("lzx-----》", "加载")
            page++
            getImmuneList()
        }
        immunePatchCodeAdapter!!.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View, viewHolder: BaseRecyclerViewHolder, position: Int
            ) {
                QueryImmuneDetailsActivity.start(
                    this@ImmunePatchCodeActivity, immunePatchCodeAdapter!!.getData(position)
                )
            }

            override fun onItemLongClick(
                view: View, viewHolder: BaseRecyclerViewHolder, position: Int
            ): Boolean {
                return false
            }
        })
     

        binding.narDrawerImmuneXdr.regionTv.setOnClickListener { SelectAreaActivity.start(this@ImmunePatchCodeActivity) }
        binding.narDrawerImmuneXdr.sureBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(mDrawer as View)
            binding.refreshLayout.visibility = View.VISIBLE
            binding.notDataRl.visibility = View.GONE
            binding.refreshLayout.setEnableRefresh(true)
            binding.refreshLayout.autoRefresh(500)
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
            MoveToPosUtils.smoothMoveToPosition(binding.recyclerView, 0)
        }

        binding.narDrawerImmuneXdr.noBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(
                mDrawer as View
            )
        }


        //这里设置clickable(true)  必须动态设置  静态设置没有效果
        //解决问题   侧滑菜单出来的时候 点击菜单上的区域会有点击穿透问题
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                drawerView.isClickable = true
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun initDatas() {

    }

    override fun OnEventMainThread() {
        super.OnEventMainThread()
        mRxManager.on("CHOOSE_REGION") { o: Any ->
            val mRegionIDInfo = o as Int
            LogUtil.d(
                "lzx----》", "回传回来的区划数据$mRegionIDInfo"
            )
            val tr = DaoManager.queryRegionDao().queryBuilder()
                .where(TRegionDao.Properties.Region_id.eq(mRegionIDInfo)).list()[0]
            LogUtil.d(
                "lzx----》", "回传回来的区划数据$tr"
            )
            binding.narDrawerImmuneXdr.regionTv.text = tr.region_shortname
            mRegionID = tr.region_id.toInt()
            mRegionLevel = tr.region_level.toInt()
        }
    }

    private fun getRole() {
        val roleList: MutableList<String> = ArrayList()
        for (role in UserDataSP.getInstance().userInfo.Result.roles) {
            roleList.add(role.id)
        }
        if (roleList.contains(AppConstants.FangYiYuanID) || roleList.contains(AppConstants.XZFYMaster) || roleList.contains(
                AppConstants.XianMaster
            ) || roleList.contains(AppConstants.ShiMaster)
        ) {
            binding.queryTv.visibility = View.VISIBLE
            val regionID =
                UserDataSP.getInstance().userInfo.Result.dependency.Dep_AgencyMID.Region.id
            val tRegion = DaoManager.queryRegionDao().queryBuilder()
                .where(TRegionDao.Properties.Region_id.eq(regionID)).list()[0]
            mIsSelfWrite = AppConstants.IsSelfWrite.FYYMY
            mRegionID = regionID
            mRegionLevel = tRegion.region_level.toInt()
            LogUtil.d("lzx-------->", "防疫员")
            getImmuneList()
        } else {
            LogUtil.d("lzx-------->", "自主免疫")
            mIsSelfWrite = AppConstants.IsSelfWrite.ZZMY
            mMobile = UserDataSP.getInstance().userInfo.Result.mobile
            //TODO:这个判断自主免疫
            binding.queryTv.visibility = View.GONE
            getImmuneList()
        }
    }

    private fun getImmuneList() {
        HttpRequest.getImmuneBranch(this@ImmunePatchCodeActivity,
            page,
            size,
            mXdrMid,
            mAnimalID,
            binding.narDrawerImmuneXdr.xdrPersonTv.text.toString(),
            mRegionLevel,
            mRegionID,
            mIsSelfWrite,
            UserDataSP.getInstance().userInfo.Result.userId,
            object : CallBackLis<QueryImmuneBean> {
                override fun onSuccess(method: String?, content: QueryImmuneBean) {
                    if (content.errorCode == 0) {
                        if (content.result.pageItems.isNotEmpty()) {
                            binding.refreshLayout.visibility = View.VISIBLE
                            binding.notDataRl.visibility = View.GONE
                            if (page == 0) {
                                immunePatchCodeAdapter!!.refreshDataList(content.result.pageItems)
                                if (immunePatchCodeAdapter!!.dataList.size >= content.result.totalCount) {
                                    binding.refreshLayout.finishRefreshWithNoMoreData() //完成加载并标记没有更多数据
                                } else {
                                    binding.refreshLayout.finishRefresh()
                                    binding.refreshLayout.autoLoadMore()
                                    LogUtil.d("lzx------>", "自动加载")
                                }
                            } else {
                                if (immunePatchCodeAdapter!!.dataList.size == content.result.totalCount) {
                                    immunePatchCodeAdapter!!.addDataList(content.result.pageItems)
                                    binding.refreshLayout.finishLoadMoreWithNoMoreData() //完成加载并标记没有更多数据

                                } else {
                                    immunePatchCodeAdapter!!.addDataList(content.result.pageItems)
                                    if (immunePatchCodeAdapter!!.dataList.size == content.result.totalCount) {
                                        binding.refreshLayout.finishLoadMoreWithNoMoreData() //将不会再次触发加载更多事件
                                        return
                                    }
                                    binding.refreshLayout.finishLoadMore()
                                    LogUtil.d("lzx-----", "====数据加载")
                                }
                            }
                        } else {
                            binding.refreshLayout.setEnableRefresh(false)
                            binding.refreshLayout.finishRefresh()
                            binding.refreshLayout.visibility = View.GONE
                            binding.notDataRl.visibility = View.VISIBLE
                            Objects.requireNonNull(
                                RxToast.success(
                                    this@ImmunePatchCodeActivity, "当前暂无防疫数据"
                                )
                            )
                        }
                    }
                }

                override fun onFailure(method: String?, error: String?) {
                    hideLoading()
                }
            })
    }


    /**
     * 点击补戴标
     */
    override fun onItemClick(position: Int) {
        val data = immunePatchCodeAdapter?.getData(position)
        if (data!!.dep_AnimalID.eartagCode <= 0) {
            Objects.requireNonNull(
                RxToast.error(
                    this@ImmunePatchCodeActivity, "当前动物为不戴标动物，无法进行补戴标操作"
                )
            )
        } else {
            ImmunePatchCodeCommitActivity.start(this@ImmunePatchCodeActivity, data.mid)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        showDrawer()
        return false
    }

    private fun showDrawer() {
        if (binding.drawerLayout.isDrawerOpen(mDrawer!!)) {
            binding.drawerLayout.closeDrawer(mDrawer!!)
        } else {
            binding.drawerLayout.openDrawer(mDrawer!!)
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