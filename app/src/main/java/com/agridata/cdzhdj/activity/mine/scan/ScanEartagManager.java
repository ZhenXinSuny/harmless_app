package com.agridata.cdzhdj.activity.mine.scan;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 16:40.
 * @Description :描述
 */
public class ScanEartagManager {

    // 定义扫描设备枚举类型
    public enum ScanDeviceEnum {

        DEVICE_CAMERA_MOTO(1), // moto camera scan
        DEVICE_BLUETOOTH_BIO(2); // bio bluetooth scan

        private int mVal;

        private ScanDeviceEnum(int type) {
            mVal = type;
        }

        public int GetVal() {
            return mVal;
        }
    }

    public static final int SCAN_OK = 0; // 扫描成功
    public static final int SCAN_CANCEL = -11; // 扫描取消
    public static final int SCAN_ERROR_UNREGIST = -1; // 设备未注册
    public static final int SCAN_ERROR_OPEN_CAMERA_FAIL = -2; // 打开照相机失败
    public static final int SCAN_ERROR_START_HANDSFREE_FAIL = -3;
    public static final int SCAN_TIMEOUT = -10; // 超时
    public static final int SCAN_STOP_BY_HANDLE = -11; // 手动停止
    public static final int SCAN_ERROR_UNKNOWN = -99; // 扫描未知错误

    private static ScanEartagManager mScanEntity = null;

    // 默认扫描设备为蓝牙 读取 RFID标签
    private ScanDeviceEnum mScanDeviceType = ScanDeviceEnum.DEVICE_CAMERA_MOTO;

    private int mScanEartagType = 1;

    public static ScanEartagManager getInstance() {

        if (mScanEntity == null) {
            mScanEntity = new ScanEartagManager();
        }
        return mScanEntity;
    }

    public ScanEartagManager() {

    }

    // 设置扫描设备类型
    public void SetScanDeviceType(ScanDeviceEnum type) {

        mScanDeviceType = type;
    }

    // 获取扫描设备类型
    public ScanDeviceEnum getScanDeviceType() {

        return mScanDeviceType;
    }

    // 设置扫描耳标号类型
    public void SetScanEartagType(int nType) {
        mScanEartagType = nType;
    }

    // 获取扫描耳标号的类型 即动物的耳标号匹配类型
    public int GetScanEartagTypeInt() {
        return mScanEartagType;
    }

    public String GetScanEartagTypeString() {

        return String.valueOf(mScanEartagType);
    }

}
