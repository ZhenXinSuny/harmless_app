package com.agridata.cdzhdj.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * @author 56454
 * @auther 56454
 * @date 2022/6/29
 * @time 19:31.
 */

public class RoleBean implements Serializable {
    public int code;
    public String msg;
    public DataBean data;

    @NonNull
    @Override
    public String toString() {
        return "RoleBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        public UserInfoBean userInfo;
        public HarmlessUserBean harmlessUser;
        public HarmlessRegionBean harmlessRegion;
        public CarInfoBean carInfo;
        public List<HarmlessRegionBean> shouYunRegion;
        public List<UserRoleBean> userRole;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userInfo=" + userInfo +
                    ", harmlessUser=" + harmlessUser +
                    ", harmlessRegion=" + harmlessRegion +
                    ", carInfo=" + carInfo +
                    ", shouYunRegion=" + shouYunRegion +
                    ", userRole=" + userRole +
                    '}';
        }

        public static class UserInfoBean implements Serializable {
            public String userId;
            public String username;
            public String mobile;
            public Object email;
            public String password;
            public String nickname;
            public boolean disabled;
            public boolean deleted;
            public String appId;
            public int ordinal;
            public String name;
            public String avatar;
            public Object department;
            public Object job;
            public Object extension;
            public boolean readOnly;
            public Object tags;
            public Object bindings;
            public Object lastLogin;
            public String createAt;
            public String lastUpdate;
            public Object settings;
            public String passLastUpdate;
            public Object passErrorCount;
            public Object passErrorTime;
            public Object isInitialPass;
            public Object isActiveEmail;

            @Override
            public String toString() {
                return "UserInfoBean{" +
                        "userId='" + userId + '\'' +
                        ", username=" + username +
                        ", mobile='" + mobile + '\'' +
                        ", email=" + email +
                        ", password='" + password + '\'' +
                        ", nickname=" + nickname +
                        ", disabled=" + disabled +
                        ", deleted=" + deleted +
                        ", appId='" + appId + '\'' +
                        ", ordinal=" + ordinal +
                        ", name=" + name +
                        ", avatar=" + avatar +
                        ", department=" + department +
                        ", job=" + job +
                        ", extension=" + extension +
                        ", readOnly=" + readOnly +
                        ", tags=" + tags +
                        ", bindings=" + bindings +
                        ", lastLogin=" + lastLogin +
                        ", createAt='" + createAt + '\'' +
                        ", lastUpdate='" + lastUpdate + '\'' +
                        ", settings=" + settings +
                        ", passLastUpdate='" + passLastUpdate + '\'' +
                        ", passErrorCount=" + passErrorCount +
                        ", passErrorTime=" + passErrorTime +
                        ", isInitialPass=" + isInitialPass +
                        ", isActiveEmail=" + isActiveEmail +
                        '}';
            }
        }


        public static class HarmlessUserBean implements Serializable {
            public String mid;
            public Object sourceId;
            public String name;
            public String partid;
            public Role role;
            public String mobile;
            public String userId;
            public String idcard;
            public FactoryBean factory;

            @Override
            public String toString() {
                return "HarmlessUserBean{" +
                        "mid='" + mid + '\'' +
                        ", sourceId=" + sourceId +
                        ", name='" + name + '\'' +
                        ", partid='" + partid + '\'' +
                        ", role=" + role +
                        ", mobile='" + mobile + '\'' +
                        ", userId='" + userId + '\'' +
                        ", idcard='" + idcard + '\'' +
                        ", factory=" + factory +
                        '}';
            }

            public static class Role implements Serializable {
                public String key;
                public String Name;

                @Override
                public String toString() {
                    return "Role{" +
                            "key='" + key + '\'' +
                            ", Name='" + Name + '\'' +
                            '}';
                }
            }


            public static class FactoryBean implements Serializable {
                public String Mid;
                public String Name;

                @Override
                public String toString() {
                    return "FactoryBean{" +
                            "Mid='" + Mid + '\'' +
                            ", Name='" + Name + '\'' +
                            '}';
                }
            }
        }

        public static class HarmlessRegionBean implements Serializable {
            public int RegionParentID;
            public String RegionCode;
            public String RegionName;
            public int RegionLevel;
            public int RI2;
            @SerializedName("ID")
            public int id;
            public int RI1;
            public int RI4;
            public int RI3;
            public String RegionFullName;
            public int RI5;

            @Override
            public String toString() {
                return "HarmlessRegionBean{" +
                        "RegionParentID=" + RegionParentID +
                        ", RegionCode='" + RegionCode + '\'' +
                        ", RegionName='" + RegionName + '\'' +
                        ", RegionLevel=" + RegionLevel +
                        ", RI2=" + RI2 +
                        ", id=" + id +
                        ", RI1=" + RI1 +
                        ", RI4=" + RI4 +
                        ", RI3=" + RI3 +
                        ", RegionFullName='" + RegionFullName + '\'' +
                        ", RI5=" + RI5 +
                        '}';
            }
        }


        public static class CarInfoBean implements Serializable {
            public String LimitTime;
            @SerializedName("ID")
            public String id;
            public String Name;

            @Override
            public String toString() {
                return "CarInfoBean{" +
                        "LimitTime='" + LimitTime + '\'' +
                        ", id='" + id + '\'' +
                        ", Name='" + Name + '\'' +
                        '}';
            }
        }


        public static class UserRoleBean implements Serializable {
            public String id;
            public String name;
            public String description;

            @Override
            public String toString() {
                return "UserRoleBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        '}';
            }
        }
    }
}
