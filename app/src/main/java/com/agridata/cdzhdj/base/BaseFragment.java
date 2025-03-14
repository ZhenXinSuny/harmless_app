package com.agridata.cdzhdj.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.RxManager;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

  public abstract class BaseFragment <T extends ViewBinding> extends Fragment {
      protected T binding;
      protected RxManager mRxManager = null;

      private boolean mIsViewPrepared;    // 标识fragment视图已经初始化完毕
      private boolean mHasLoadData;   // 标识已经触发过懒加载数据

      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
          assert type != null;
          Class cls = (Class) type.getActualTypeArguments()[0];
          try {
              Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
              binding = (T) inflate.invoke(null, inflater, container, false);
          }  catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
              e.printStackTrace();
          }
          return binding.getRoot();
      }

      @Override
      public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
          super.onViewCreated(view,savedInstanceState);
          this.mIsViewPrepared = true;
          //this.lazyLoadData();
          this.mRxManager = new RxManager();//实列RxManager 用于发送消息
          initView();
          initDatas();
          OnEventMainThread();
      }
      /**
       * 懒加载数据
       */
      private void lazyLoadData() {
          LogUtil.i("BaseFragment", "===UserVisibleHint==" + super.getUserVisibleHint() + ",mHasLoadData==" + mHasLoadData + ",mIsViewPrepared==" + this.mIsViewPrepared);
          if (super.getUserVisibleHint() && !this.mHasLoadData && this.mIsViewPrepared) {
              LogUtil.i("BaseFragment", "initData");
              this.initDatas();
              this.mHasLoadData = true;
          }
      }

      @Override
      public void setUserVisibleHint(boolean isVisibleToUser) {
          super.setUserVisibleHint(isVisibleToUser);
          LogUtil.i("BaseFragment", "setUserVisibleHint isVisibleToUser=" + isVisibleToUser);
          if (isVisibleToUser) {
              this.lazyLoadData();
          }
      }
      /**
       * RxBus事件处理-主线程
       */
      protected void OnEventMainThread() {

      }

      @Override
      public void onResume() {
          super.onResume();
          if (this.mRxManager == null) {
              this.mRxManager = new RxManager();
          }
      }
      @Override
      public void onDestroy() {

          if (null != this.mRxManager) {
              this.mRxManager.clear();
              this.mRxManager = null;
          }
          super.onDestroy();
      }

      @Override
      public void onDestroyView() {
          LogUtil.i("BaseFragment", "onDestroyView");
          super.onDestroyView();
          this.mIsViewPrepared = false;
      }
      //初始化数据
      protected abstract void initView() ;
      //初始化数据
      protected abstract void initDatas();



}
