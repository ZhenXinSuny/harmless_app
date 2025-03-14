package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-28 14:17.
 * @Description :描述
 */
public class ProvinceData <T> implements Serializable {

    public int code;
    public String message;
    public Data data;
    public boolean ok;


    @Override
    public String toString() {
        return "ProvinceData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", ok=" + ok +
                '}';
    }

    public static class Data {
        public List<Children> children;

        @Override
        public String toString() {
            return "DataMap{" +
                    "children=" + children +
                    '}';
        }

        public static class Children {
            @SerializedName("RegionParentID")
            public int regionParentID;
            @SerializedName("RegionCode")
            public String regionCode;
            @SerializedName("RegionName")
            public String regionName;
            public List<ChildrenX> children;
            @SerializedName("RegionLevel")
            public int regionLevel;
            @SerializedName("ID")
            public int iD;
            @SerializedName("RegionFullName")
            public String regionFullName;

            @Override
            public String toString() {
                return regionName;
            }

            public static class ChildrenX {
                @SerializedName("RegionParentID")
                public int regionParentID;
                @SerializedName("RegionCode")
                public String regionCode;
                @SerializedName("RegionName")
                public String regionName;
                public List<ChildrenXX> children;
                @SerializedName("RegionLevel")
                public int regionLevel;
                @SerializedName("ID")
                public int iD;
                @SerializedName("RegionFullName")
                public String regionFullName;

                @Override
                public String toString() {
                    return regionName;
                }

                public static class ChildrenXX {
                    @SerializedName("RegionParentID")
                    public int regionParentID;
                    @SerializedName("RegionCode")
                    public String regionCode;
                    @SerializedName("RegionName")
                    public String regionName;
                    @SerializedName("RegionLevel")
                    public int regionLevel;
                    @SerializedName("ID")
                    public int iD;
                    @SerializedName("RegionFullName")
                    public String regionFullName;

                    @Override
                    public String toString() {
                        return regionName;
                    }
                }
            }
        }
    }
}
