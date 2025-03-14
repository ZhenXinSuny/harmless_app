package com.agridata.cdzhdj.data.db.mapper;

import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;
import com.agridata.cdzhdj.data.db.rolebean.RoleHarmlessUserDB;
import com.agridata.cdzhdj.data.db.rolebean.RoleUserInfoDB;

import java.util.ArrayList;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-22 15:59.
 * @Description :描述
 */
public class RoleInfoMapper {



    public  RoleBeanDB convertToDbModel(RoleBean roleBean) {
        if (roleBean == null) {
            throw new IllegalArgumentException("roleBean can not be null");
        }

        RoleBeanDB roleBeanDb = new RoleBeanDB();
        roleBeanDb.roleUserInfoDb  =  new RoleUserInfoDB();
        roleBeanDb.roleUserInfoDb.userId = roleBean.data.userInfo.userId;
        roleBeanDb.roleUserInfoDb.username = roleBean.data.userInfo.username;
        roleBeanDb.roleUserInfoDb.mobile = roleBean.data.userInfo.mobile;
        roleBeanDb.roleUserInfoDb.nickname = roleBean.data.userInfo.nickname;
        roleBeanDb.roleUserInfoDb.appId = roleBean.data.userInfo.appId;
        roleBeanDb.roleUserInfoDb.name = roleBean.data.userInfo.name;
        roleBeanDb.roleUserInfoDb.avatar = roleBean.data.userInfo.avatar;

        //harmlessUser
        roleBeanDb.roleHarmlessUserDb = new RoleHarmlessUserDB();
        roleBeanDb.roleHarmlessUserDb.mid = roleBean.data.harmlessUser.mid;
        roleBeanDb.roleHarmlessUserDb.name = roleBean.data.harmlessUser.name;
        roleBeanDb.roleHarmlessUserDb.partid = roleBean.data.harmlessUser.partid;
        roleBeanDb.roleHarmlessUserDb.userId = roleBean.data.harmlessUser.userId;
        roleBeanDb.roleHarmlessUserDb.mobile = roleBean.data.harmlessUser.mobile;
        roleBeanDb.roleHarmlessUserDb.idcard = roleBean.data.harmlessUser.idcard;

        //role
        roleBeanDb.roleHarmlessUserDb.role = new RoleHarmlessUserDB.RoleDTO();
        roleBeanDb.roleHarmlessUserDb.role.key = roleBean.data.harmlessUser.role.key;
        roleBeanDb.roleHarmlessUserDb.role.name = roleBean.data.harmlessUser.role.Name;

        //factory
        roleBeanDb.roleHarmlessUserDb.factory = new RoleHarmlessUserDB.FactoryDTO();
        roleBeanDb.roleHarmlessUserDb.factory.mid = roleBean.data.harmlessUser.factory.Mid;
        roleBeanDb.roleHarmlessUserDb.factory.name = roleBean.data.harmlessUser.role.Name;



        roleBeanDb.shouYunRegion  = new ArrayList<>();
        for (RoleBean.DataBean.HarmlessRegionBean harmlessRegionBean : roleBean.data.shouYunRegion) {
            RoleBeanDB.HarmlessRegionBean  harmlessRegionBean1  = new RoleBeanDB.HarmlessRegionBean();
            harmlessRegionBean1.id = harmlessRegionBean.id;
            harmlessRegionBean1.RI1 = harmlessRegionBean.RI1;
            harmlessRegionBean1.RI2 = harmlessRegionBean.RI2;
            harmlessRegionBean1.RI3 = harmlessRegionBean.RI3;
            harmlessRegionBean1.RI4 = harmlessRegionBean.RI4;
            harmlessRegionBean1.RI5 = harmlessRegionBean.RI5;
            harmlessRegionBean1.RegionCode = harmlessRegionBean.RegionCode;
            harmlessRegionBean1.RegionName = harmlessRegionBean.RegionName;
            harmlessRegionBean1.RegionLevel = harmlessRegionBean.RegionLevel;
            harmlessRegionBean1.RegionFullName = harmlessRegionBean.RegionFullName;
            harmlessRegionBean1.RegionParentID = harmlessRegionBean.RegionParentID;
            roleBeanDb.shouYunRegion.add(harmlessRegionBean1);

        }


        return roleBeanDb;
    }



}
