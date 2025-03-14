package com.agridata.cdzhdj.data.entrycheck;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-23 15:53.
 * @Description :描述
 */
public class DaiZaiHouseBean {


    /**
     * code
     */
    public Integer code;
    /**
     * message
     */
    public String message;
    /**
     * data
     */
    public List<Data> data;

    public static class Data {
        /**
         * breederId
         */
        public String BreederId;
        /**
         * breederName
         */
        public String BreederName;

        @Override
        public String toString() {
            return "Data{" +
                    "BreederId='" + BreederId + '\'' +
                    ", BreederName='" + BreederName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DaiZaiHouseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
