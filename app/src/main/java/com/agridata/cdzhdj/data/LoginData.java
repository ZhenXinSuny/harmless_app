package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 9:43.
 */
public class LoginData implements Serializable {


    public int Status;
    public int ErrorCode;
    public String Message;
    public ResultBean Result;

    @Override
    public String toString() {
        return "LoginData{" +
                "Status=" + Status +
                ", ErrorCode=" + ErrorCode +
                ", Message='" + Message + '\'' +
                ", Result=" + Result +
                '}';
    }

    public static class ResultBean implements Serializable {
        public String avatar;
        public String createAt;
        public Object department;
        public DependencyBean dependency;
        public boolean disabled;
        public String email;
        public Object extension;
        public Object identityType;
        public Object job;
        public Object lastLogin;
        public String lastUpdate;
        public int level;
        public String mobile;
        public String name;
        public Object nickname;
        public PartitionBean partition;
        public PartitionSettingsBean partitionSettings;
        public PartitionSystemSettingsBean partitionSystemSettings;
        public boolean readOnly;
        public Object tags;
        public String userId;
        public Object username;
        public List<RolesBean> roles;
        public String token;
        public String sessionId;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "avatar='" + avatar + '\'' +
                    ", createAt='" + createAt + '\'' +
                    ", department=" + department +
                    ", dependency=" + dependency +
                    ", disabled=" + disabled +
                    ", email='" + email + '\'' +
                    ", extension=" + extension +
                    ", identityType=" + identityType +
                    ", job=" + job +
                    ", lastLogin=" + lastLogin +
                    ", lastUpdate='" + lastUpdate + '\'' +
                    ", level=" + level +
                    ", mobile='" + mobile + '\'' +
                    ", name='" + name + '\'' +
                    ", nickname=" + nickname +
                    ", partition=" + partition +
                    ", partitionSettings=" + partitionSettings +
                    ", partitionSystemSettings=" + partitionSystemSettings +
                    ", readOnly=" + readOnly +
                    ", tags=" + tags +
                    ", userId='" + userId + '\'' +
                    ", username=" + username +
                    ", roles=" + roles +
                    '}';
        }

        public String token() {
            return token;
        }


        public static class DependencyBean implements Serializable {
            public String Mid;
            public Object SourceId;
            public String Name;
            public Object old_id;
            public String AgencyMID;
            public String IDCard;
            public boolean IsOV;
            @SerializedName("OVNO")
            public Object ovno;
            public Object HeadPic;
            public String _PartId;
            public Object Degree;
            public String Tel;
            public Object LoginID;
            public int Gender;
            public Object Political;
            public Object JobTitle;
            public Object JobPosition;
            public int UserType;
            public Object region;
            public Object OVAgency;
            public Object OVDate;
            public int IsMaster;
            public String PermissionInfoMid;
            public DepAgencyMIDBean Dep_AgencyMID;
            public DepPermissionInfoMidBean Dep_PermissionInfoMid;

            @Override
            public String toString() {
                return "DependencyBean{" +
                        "Mid='" + Mid + '\'' +
                        ", SourceId=" + SourceId +
                        ", Name='" + Name + '\'' +
                        ", old_id=" + old_id +
                        ", AgencyMID='" + AgencyMID + '\'' +
                        ", IDCard='" + IDCard + '\'' +
                        ", IsOV=" + IsOV +
                        ", ovno=" + ovno +
                        ", HeadPic=" + HeadPic +
                        ", _PartId='" + _PartId + '\'' +
                        ", Degree=" + Degree +
                        ", Tel='" + Tel + '\'' +
                        ", LoginID=" + LoginID +
                        ", Gender=" + Gender +
                        ", Political=" + Political +
                        ", JobTitle=" + JobTitle +
                        ", JobPosition=" + JobPosition +
                        ", UserType=" + UserType +
                        ", region=" + region +
                        ", OVAgency=" + OVAgency +
                        ", OVDate=" + OVDate +
                        ", IsMaster=" + IsMaster +
                        ", PermissionInfoMid='" + PermissionInfoMid + '\'' +
                        ", Dep_AgencyMID=" + Dep_AgencyMID +
                        ", Dep_PermissionInfoMid=" + Dep_PermissionInfoMid +
                        '}';
            }

            public static class DepAgencyMIDBean implements Serializable {
                public String Mid;
                public Object SourceId;
                public String Name;
                public int old_id;
                public String AgencyTel;
                public String PrincipalMobile;
                public RegionBean Region;
                public String CreditCode;
                public String Address;
                public String Stamp;
                public String _PartId;
                public int AgencyFunction;
                public int AgencyLevel;
                public int AgencyType;
                public int UserCount;
                public String PrincipalUser;
                public Object ServiceType;
                public int SubAgencyCount;
                public Object ParentMID;
                public Object Longitude;
                public Object Latitude;
                public Object Point;
                public Object Cst;


                @Override
                public String toString() {
                    return "DepAgencyMIDBean{" +
                            "Mid='" + Mid + '\'' +
                            ", SourceId=" + SourceId +
                            ", Name='" + Name + '\'' +
                            ", old_id=" + old_id +
                            ", AgencyTel='" + AgencyTel + '\'' +
                            ", PrincipalMobile='" + PrincipalMobile + '\'' +
                            ", Region=" + Region +
                            ", CreditCode='" + CreditCode + '\'' +
                            ", Address='" + Address + '\'' +
                            ", Stamp='" + Stamp + '\'' +
                            ", _PartId='" + _PartId + '\'' +
                            ", AgencyFunction=" + AgencyFunction +
                            ", AgencyLevel=" + AgencyLevel +
                            ", AgencyType=" + AgencyType +
                            ", UserCount=" + UserCount +
                            ", PrincipalUser='" + PrincipalUser + '\'' +
                            ", ServiceType=" + ServiceType +
                            ", SubAgencyCount=" + SubAgencyCount +
                            ", ParentMID=" + ParentMID +
                            ", Longitude=" + Longitude +
                            ", Latitude=" + Latitude +
                            ", Point=" + Point +
                            ", Cst=" + Cst +
                            '}';
                }

                public static class RegionBean implements Serializable {
                    @SerializedName("ID")
                    public int id;
                    public int RI1;
                    public int RI2;
                    public int RI3;
                    public int RI4;
                    public int RI5;
                    public String RegionCode;
                    public String RegionName;
                    public int RegionLevel;
                    public String RegionFullName;
                    public int RegionParentID;

                    @Override
                    public String toString() {
                        return "RegionBean{" +
                                "id=" + id +
                                ", RI1=" + RI1 +
                                ", RI2=" + RI2 +
                                ", RI3=" + RI3 +
                                ", RI4=" + RI4 +
                                ", RI5=" + RI5 +
                                ", RegionCode='" + RegionCode + '\'' +
                                ", RegionName='" + RegionName + '\'' +
                                ", RegionLevel=" + RegionLevel +
                                ", RegionFullName='" + RegionFullName + '\'' +
                                ", RegionParentID=" + RegionParentID +
                                '}';
                    }
                }
            }


            public static class DepPermissionInfoMidBean implements Serializable {
                public String Mid;
                public Object SourceId;
                public Object Name;
                public Object UserID;
                public int IsAsync;
                public Object AsyncTimestamp;
                public String _PartId;
                public boolean IsCertificate;
                public Object QuaryField;
                public boolean Led;
                public boolean Reader;
                public boolean CameraNX;
                public boolean CameraSC;
                public boolean CarNotLimitCurrentProvince;
                public Object HarmlessSetting;
                public List<HarmlessRegionBean> HarmlessRegion;
                public List<PointsBean> Points;

                public ClientSettingBean  ClientSetting;

                @Override
                public String toString() {
                    return "DepPermissionInfoMidBean{" +
                            "Mid='" + Mid + '\'' +
                            ", SourceId=" + SourceId +
                            ", Name=" + Name +
                            ", UserID=" + UserID +
                            ", IsAsync=" + IsAsync +
                            ", AsyncTimestamp=" + AsyncTimestamp +
                            ", _PartId='" + _PartId + '\'' +
                            ", IsCertificate=" + IsCertificate +
                            ", QuaryField=" + QuaryField +
                            ", Led=" + Led +
                            ", Reader=" + Reader +
                            ", CameraNX=" + CameraNX +
                            ", CameraSC=" + CameraSC +
                            ", CarNotLimitCurrentProvince=" + CarNotLimitCurrentProvince +
                            ", HarmlessSetting=" + HarmlessSetting +
                            ", HarmlessRegion=" + HarmlessRegion +
                            ", Points=" + Points +
                            ", ClientSetting=" + ClientSetting +
                            '}';
                }

                public static class    ClientSettingBean implements Serializable {
                    public boolean AnimalA;
                    public boolean AnimalB;
                    public boolean AnimalRecord;
                    public boolean IsOnline;
                    public boolean IsRecycle;
                    public boolean IsSlaughter;
                    public boolean ProductA;
                    public boolean ProductB;
                    public boolean ProductRecord;

                }
                public static class PointsBean implements Serializable {
                    public int LimitAmount;
                    public String PointID;
                    public String PointName;
                    public String PointType;
                    public RegionBeanPoint Region;



                    public static class RegionBeanPoint implements Serializable {
                        @SerializedName("ID")
                        public int id;
                        public int RI1;
                        public int RI2;
                        public int RI3;
                        public int RI4;
                        public int RI5;
                        public String RegionCode;
                        public String RegionName;
                        public int RegionLevel;
                        public String RegionFullName;
                        public int RegionParentID;

                        @Override
                        public String toString() {
                            return "RegionBean{" +
                                    "id=" + id +
                                    ", RI1=" + RI1 +
                                    ", RI2=" + RI2 +
                                    ", RI3=" + RI3 +
                                    ", RI4=" + RI4 +
                                    ", RI5=" + RI5 +
                                    ", RegionCode='" + RegionCode + '\'' +
                                    ", RegionName='" + RegionName + '\'' +
                                    ", RegionLevel=" + RegionLevel +
                                    ", RegionFullName='" + RegionFullName + '\'' +
                                    ", RegionParentID=" + RegionParentID +
                                    '}';
                        }
                }

            }
                public static class HarmlessRegionBean implements Serializable {
                    @SerializedName("ID")
                    public int id;
                    public String Name;

                    @Override
                    public String toString() {
                        return "HarmlessRegionBean{" +
                                "id=" + id +
                                ", Name='" + Name + '\'' +
                                '}';
                    }
                }

            }
        }


        public static class PartitionBean implements Serializable {
            public String code;
            public boolean disabled;
            public Object extension;
            public String funcId;
            public int grade;
            public String id;
            public String name;
            public Object passErrorLimitedDuration;
            public Object passNonInitialEnabled;
            public Object passRetry;
            public Object passUpdateCycle;
            public Object pid;

            @Override
            public String toString() {
                return "PartitionBean{" +
                        "code='" + code + '\'' +
                        ", disabled=" + disabled +
                        ", extension=" + extension +
                        ", funcId='" + funcId + '\'' +
                        ", grade=" + grade +
                        ", id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", passErrorLimitedDuration=" + passErrorLimitedDuration +
                        ", passNonInitialEnabled=" + passNonInitialEnabled +
                        ", passRetry=" + passRetry +
                        ", passUpdateCycle=" + passUpdateCycle +
                        ", pid=" + pid +
                        '}';
            }
        }


        public static class PartitionSettingsBean implements Serializable {
            public int level;
            public String roleId;
            public String roleName;

            @Override
            public String toString() {
                return "PartitionSettingsBean{" +
                        "level=" + level +
                        ", roleId='" + roleId + '\'' +
                        ", roleName='" + roleName + '\'' +
                        '}';
            }
        }


        public static class PartitionSystemSettingsBean implements Serializable {
            public boolean disabled;
            public boolean readOnly;

            @Override
            public String toString() {
                return "PartitionSystemSettingsBean{" +
                        "disabled=" + disabled +
                        ", readOnly=" + readOnly +
                        '}';
            }
        }


        public static class RolesBean implements Serializable {
            public String description;
            public boolean disabled;
            public Object firstId;
            public Object firstMobileId;
            public String id;
            public int level;
            public String name;
            public int orderNo;
            public Object roleType;
            public List<String> tags;
            public Object variables;

            @Override
            public String toString() {
                return "RolesBean{" +
                        "description='" + description + '\'' +
                        ", disabled=" + disabled +
                        ", firstId=" + firstId +
                        ", firstMobileId=" + firstMobileId +
                        ", id='" + id + '\'' +
                        ", level=" + level +
                        ", name='" + name + '\'' +
                        ", orderNo=" + orderNo +
                        ", roleType=" + roleType +
                        ", tags=" + tags +
                        ", variables=" + variables +
                        '}';
            }
        }
    }
}
