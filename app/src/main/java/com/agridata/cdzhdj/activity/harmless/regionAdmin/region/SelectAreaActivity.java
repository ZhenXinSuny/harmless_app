package com.agridata.cdzhdj.activity.harmless.regionAdmin.region;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivitySelectAreaBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.network.utils.LogUtil;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class SelectAreaActivity extends BaseActivity<ActivitySelectAreaBinding> {

    private final static String TAG = "SelectAreaActivity------》";
    private int region_id;

    private int p, ci, c, t;
    private String[] provinces, citys, countys, towns;// , villages;
    private List<TRegion> pList, cList, coList, tList;// , vList;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectAreaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivitySelectAreaBinding getBinding() {
        return ActivitySelectAreaBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        region_id = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
        LogUtil.d(TAG,"获取本地区化" + region_id);
        binding.sureRegionTv.setOnClickListener(v -> {
            LogUtil.d(TAG, "onClick region_id=" + region_id);
            mRxManager.post("CHOOSE_REGION",region_id);
            finish();
        });
        binding.titlebarLeft.setOnClickListener(v -> finish());

    }

    @Override
    protected void initDatas() {
        if (region_id != 0) {
            TRegion tr = DaoManager.queryRegionDao().queryBuilder()
                    .where(TRegionDao.Properties.Region_id.eq(region_id))
                    .list().get(0);
            long Region_level = tr.getRegion_level();
            long tp = 0;
            long cp = 0;
            long cip = 0;
            long pp = 0;
            LogUtil.d(TAG, "initData Region_level=" + Region_level);
            if (Region_level == 4) {
                tp = tr.getRegion_parent_id();
                cp = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(tp)).list()
                        .get(0).getRegion_parent_id();
                cip = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(cp)).list()
                        .get(0).getRegion_parent_id();
                pp = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(cip)).list()
                        .get(0).getRegion_parent_id();
            } else if (Region_level == 3) {
                cp = tr.getRegion_parent_id();
                cip = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(cp)).list()
                        .get(0).getRegion_parent_id();
                pp = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(cip)).list()
                        .get(0).getRegion_parent_id();
            } else if (Region_level == 2) {
                cip = tr.getRegion_parent_id();
                pp = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_id.eq(cip)).list()
                        .get(0).getRegion_parent_id();
            } else if (Region_level == 1) {
                pp = tr.getRegion_parent_id();
            }

            // pList
            pList = DaoManager.queryRegionDao().queryBuilder()
                    .where(TRegionDao.Properties.Region_parent_id.eq(pp))
                    .list();
            provinces = new String[pList.size()];
            for (int i = 0; i < pList.size(); i++) {
                provinces[i] = pList.get(i).getRegion_name();
                if (cip == pList.get(i).getRegion_id()) {
                    p = i;
                    break;
                }
            }
            // cList
            if (Region_level > 1) {
                cList = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_parent_id.eq(cip))
                        .list();
                for (int i = 0; i < cList.size(); i++) {
                    if (Region_level == 2) {
                        if (region_id == cList.get(i).getRegion_id()) {
                            ci = i + 1;
                            break;
                        }
                    } else {
                        if (cp == cList.get(i).getRegion_id()) {
                            ci = i + 1;
                            break;
                        }
                    }
                }
            }
            // coList
            if (Region_level > 2) {
                coList = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_parent_id.eq(cp))
                        .list();
                for (int i = 0; i < coList.size(); i++) {
                    if (Region_level == 3) {
                        if (region_id == coList.get(i).getRegion_id()) {
                            c = i + 1;
                            break;
                        }
                    } else {
                        if (tp == coList.get(i).getRegion_id()) {
                            c = i + 1;
                            break;
                        }
                    }
                }
            }
            // tList
            if (Region_level > 3) {
                tList = DaoManager.queryRegionDao().queryBuilder()
                        .where(TRegionDao.Properties.Region_parent_id.eq(tp))
                        .list();
                for (int i = 0; i < tList.size(); i++) {
                    if (region_id == tList.get(i).getRegion_id()) {
                        t = i + 1;
                        break;
                    }
                }
            }
            LogUtil.d(TAG, "initData region_id=" + region_id + ",tp=" + tp + ",t="
                    + t + ",cp=" + cp + ",c=" + c + ",cip=" + cip + ",ci=" + ci
                    + ",pp=" + pp + ",p=" + p);
        } else {
            pList = DaoManager.queryRegionDao().queryBuilder()
                    .where(TRegionDao.Properties.Region_level.eq(1)).list();
            provinces = new String[pList.size()];
            for (int i = 0; i < provinces.length; i++) {
                provinces[i] = pList.get(i).getRegion_name();
            }
        }
        setSpinner(binding.selectAreaProvince, provinces);
        binding.selectAreaProvince.setSelection(p);
        p = 0;

        binding.selectAreaProvince.setEnabled(false);
        binding.selectAreaVillage.setVisibility(View.GONE);


        TRegion tr = DaoManager.queryRegionDao().queryBuilder()
                .where(TRegionDao.Properties.Region_id.eq(region_id)).list()
                .get(0);
        Log.d("lzx----》","tr" + tr.toString());
        long Region_level = tr.getRegion_level();
        if (Region_level > 1) {
            binding.selectAreaCity.setEnabled(false);//2
        }
        if (Region_level > 2) {
            binding.selectAreaCounty.setEnabled(false);//3
        }
        if (Region_level > 3) {
            binding.selectAreaTown.setEnabled(false);//4
        }
    }
    private void setSpinner(final Spinner spinner, final String[] datas) {
        LogUtil.i(TAG, "setSpinner datas=" + datas[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.simple_spinner_item_my, datas){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = LayoutInflater.from(SelectAreaActivity.this).inflate(R.layout.simple_spinner_dropdown_item_my, null);
                TextView label = (TextView) view
                        .findViewById(R.id.item_tv);
                label.setText(datas[position]);
                if (spinner.getSelectedItemPosition() == position) {
                    label.setTextColor(getResources().getColor(
                            R.color.J5));
                } else {
                    label.setTextColor(getResources().getColor(
                            R.color.black));
                }

                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_my);
        spinner.setAdapter(adapter);
        spinner.setDropDownVerticalOffset(140);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int id = spinner.getId();
                if (id == R.id.select_area_province) {
                    binding.selectAreaCounty.setVisibility(View.GONE);
                    binding.selectAreaTown.setVisibility(View.GONE);

                    // 读取相应的市列表
                    cList = DaoManager.queryRegionDao()
                            .queryBuilder()
                            .where(TRegionDao.Properties.Region_parent_id
                                    .eq(pList.get(arg2).getRegion_id())).list();

                    cList.add(0, new TRegion(0L));

                    citys = new String[cList.size()];
                    for (int i = 0; i < citys.length; i++) {
                        if (i == 0) {
                            citys[i] = "请选择市";
                        } else {
                            citys[i] = cList.get(i).getRegion_name();
                        }
                    }
                    setSpinner(binding.selectAreaCity, citys);
                    LogUtil.d(TAG, "citys=" + citys.toString());
                    binding.selectAreaCity.setSelection(ci);
                    ci = 0;
                } else if (id == R.id.select_area_city) {
                    if (arg2 == 0) {
                        region_id = (int) pList.get(arg2).getRegion_id();
                        binding.selectAreaCounty.setSelection(arg2);
                        binding.selectAreaTown.setSelection(arg2);
                        binding.selectAreaCounty.setVisibility(View.GONE);
                        binding.selectAreaTown.setVisibility(View.GONE);
                    } else {
                        binding.selectAreaCounty.setVisibility(View.VISIBLE);
                        long cid = cList.get(arg2).getRegion_id();
                        if (cid != 0) {
                            region_id = (int) cid;
                        }
                        LogUtil.d(TAG, "setSpinner city region_id=" + region_id);
                        // 读取相应的县列表
                        coList = DaoManager.
                                queryRegionDao()
                                .queryBuilder()
                                .where(TRegionDao.Properties.Region_parent_id
                                        .eq(cid)).list();
                        coList.add(0, new TRegion(0L));
                        countys = new String[coList.size()];
                        for (int i = 0; i < countys.length; i++) {
                            if (i == 0) {
                                countys[i] = "请选择县";
                            } else {
                                countys[i] = coList.get(i).getRegion_name();
                            }
                        }
                        setSpinner(binding.selectAreaCounty, countys);

                        LogUtil.d(TAG, "countys=" + countys.toString());
                        binding.selectAreaCounty.setSelection(c);
                        c = 0;
                    }
                } else if (id == R.id.select_area_county) {
                    if (arg2 == 0) {
                        region_id = (int) cList
                                .get(binding.selectAreaCity.getSelectedItemPosition())
                                .getRegion_id();
                        binding.selectAreaTown.setSelection(arg2);
                        binding.selectAreaTown.setVisibility(View.GONE);
                    } else {
                        binding.selectAreaTown.setVisibility(View.VISIBLE);
                        long coid = coList.get(arg2).getRegion_id();
                        if (coid != 0) {
                            region_id = (int) coid;
                        }
                        LogUtil.d(TAG, "setSpinner county region_id=" + region_id);

                        // 读取相应的乡列表
                        tList = DaoManager.
                                queryRegionDao()
                                .queryBuilder()
                                .where(TRegionDao.Properties.Region_parent_id
                                        .eq(coid)).list();
                        LogUtil.d(TAG, "tList =" + tList.toString());
                        tList.add(0, new TRegion(0L));
                        towns = new String[tList.size()];
                        for (int i = 0; i < towns.length; i++) {
                            if (i == 0) {
                                towns[i] = "请选择乡镇";
                            } else {
                                towns[i] = tList.get(i).getRegion_name();
                            }
                        }
                        setSpinner(binding.selectAreaTown, towns);
                        LogUtil.d(TAG, "towns =" + towns.toString());
                        binding.selectAreaTown.setSelection(t);
                        LogUtil.d(TAG,"市区" + t);
                        t = 0;
                    }
                } else if (id == R.id.select_area_town) {
                    LogUtil.d(TAG,"乡镇");
                    if (arg2 == 0) { //注释：此处 当不选择乡镇 默认第一个 时  id 不能设置为 	sp_county.getSelectedItemPosition() 会造成索引越界
//                        region_id = (int) tList
//                                .get(arg2 + 1)
//                                .getRegion_id();
                        Log.e("===================", region_id + "");
                    } else {
                        long tid = tList.get(arg2).getRegion_id();
                        if (tid != 0) {
                            region_id = (int) tid;
                        }
                        LogUtil.d(TAG, "tList " + tList.toString());
                        LogUtil.d(TAG, "arg2 " + arg2);
                        LogUtil.d(TAG, "setSpinner town region_id=" + region_id);
                    }
                } else if (id == R.id.select_area_village) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                LogUtil.v(TAG, "setSpinner onNothingSelected...");
            }
        });
    }
}
