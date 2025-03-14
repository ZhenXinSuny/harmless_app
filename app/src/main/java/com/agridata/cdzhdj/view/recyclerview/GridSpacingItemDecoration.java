package com.agridata.cdzhdj.view.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

/**
 * 自定义分割线
 *
 * @author Wangxx
 * @date 2017/2/13
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private HashMap<String, Integer> mSpaceValueMap;

    public static final String TOP_DECORATION = "top_decoration";
    public static final String BOTTOM_DECORATION = "bottom_decoration";
    public static final String LEFT_DECORATION = "left_decoration";
    public static final String RIGHT_DECORATION = "right_decoration";

    public GridSpacingItemDecoration(HashMap<String, Integer> mSpaceValueMap) {
        this.mSpaceValueMap = mSpaceValueMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (mSpaceValueMap.get(TOP_DECORATION) != null) {
            outRect.top = mSpaceValueMap.get(TOP_DECORATION);
        }
        if (mSpaceValueMap.get(LEFT_DECORATION) != null) {
            outRect.left = mSpaceValueMap.get(LEFT_DECORATION);
        }
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null) {
            outRect.right = mSpaceValueMap.get(RIGHT_DECORATION);
        }
        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null) {
            outRect.bottom = mSpaceValueMap.get(BOTTOM_DECORATION);
        }

    }


}