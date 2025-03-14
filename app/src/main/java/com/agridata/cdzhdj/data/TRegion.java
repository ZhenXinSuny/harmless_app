package com.agridata.cdzhdj.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @auther 56454
 * @date 2022/2/25
 * @time 10:58.
 */
@Entity
public class TRegion {
    private long region_id;
    /** Not-null value. */
    private String region_guid;
    private long region_parent_id;
    /** Not-null value. */
    private String region_name;
    private long region_level;
    /** Not-null value. */
    private String region_code;
    private long region_serial;
    /** Not-null value. */
    private String region_shortname;
    /** Not-null value. */
    private String region_path;
    /** Not-null value. */
    private String region_spell;
    @Generated(hash = 697380158)
    public TRegion(long region_id, String region_guid, long region_parent_id,
            String region_name, long region_level, String region_code,
            long region_serial, String region_shortname, String region_path,
            String region_spell) {
        this.region_id = region_id;
        this.region_guid = region_guid;
        this.region_parent_id = region_parent_id;
        this.region_name = region_name;
        this.region_level = region_level;
        this.region_code = region_code;
        this.region_serial = region_serial;
        this.region_shortname = region_shortname;
        this.region_path = region_path;
        this.region_spell = region_spell;
    }
    @Generated(hash = 1482578281)
    public TRegion() {
    }
    public TRegion(long region_id) {
        this.region_id = region_id;
    }
    public long getRegion_id() {
        return this.region_id;
    }
    public void setRegion_id(long region_id) {
        this.region_id = region_id;
    }
    public String getRegion_guid() {
        return this.region_guid;
    }
    public void setRegion_guid(String region_guid) {
        this.region_guid = region_guid;
    }
    public long getRegion_parent_id() {
        return this.region_parent_id;
    }
    public void setRegion_parent_id(long region_parent_id) {
        this.region_parent_id = region_parent_id;
    }
    public String getRegion_name() {
        return this.region_name;
    }
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
    public long getRegion_level() {
        return this.region_level;
    }
    public void setRegion_level(long region_level) {
        this.region_level = region_level;
    }
    public String getRegion_code() {
        return this.region_code;
    }
    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }
    public long getRegion_serial() {
        return this.region_serial;
    }
    public void setRegion_serial(long region_serial) {
        this.region_serial = region_serial;
    }
    public String getRegion_shortname() {
        return this.region_shortname;
    }
    public void setRegion_shortname(String region_shortname) {
        this.region_shortname = region_shortname;
    }
    public String getRegion_path() {
        return this.region_path;
    }
    public void setRegion_path(String region_path) {
        this.region_path = region_path;
    }
    public String getRegion_spell() {
        return this.region_spell;
    }
    public void setRegion_spell(String region_spell) {
        this.region_spell = region_spell;
    }

    @Override
    public String toString() {
        return "TRegion{" +
                "region_id=" + region_id +
                ", region_guid='" + region_guid + '\'' +
                ", region_parent_id=" + region_parent_id +
                ", region_name='" + region_name + '\'' +
                ", region_level=" + region_level +
                ", region_code='" + region_code + '\'' +
                ", region_serial=" + region_serial +
                ", region_shortname='" + region_shortname + '\'' +
                ", region_path='" + region_path + '\'' +
                ", region_spell='" + region_spell + '\'' +
                '}';
    }
}
