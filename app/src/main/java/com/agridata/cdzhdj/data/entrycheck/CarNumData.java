package com.agridata.cdzhdj.data.entrycheck;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-28 16:12.
 * @Description :描述
 */
public class CarNumData {


    public WordsResultBean words_result;
    public String log_id;

    public static class WordsResultBean {
        public String number;
        public List<VertexesLocationBean> vertexes_location;
        public String color;
        public List<Double> probability;

        public static class VertexesLocationBean {
            public int x;
            public int y;
        }
    }
}
