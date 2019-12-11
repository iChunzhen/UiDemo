package com.ichunzhen.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.ichunzhen.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuancz
 * @Date 2019/12/3-15:48
 * @Email ichunzhen6@gmail.com
 */
public class Rclv extends ViewGroup {
    private Adapter adapter;
    //当前显示的View
    private List<View> viewList;
    //当前滑动的y值
    private int currentY;
    //行数
    private int rowCount;
    //view的第一行  是占内容的几行
    private int firstRow;
    //y偏移量
    private int scrollY;
    //初始化  第一屏最慢 是否需要重新布局
    private boolean needRelayout;
    private int width;

    private int height;
    private int[] heights;//item  高度
    Recycler recycler;
    //最小滑动距离
    private int touchSlop;

    public Adapter getAdapter() {
        return adapter;
    }

    public Rclv(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        //  获取touchSlop （系统 滑动距离的最小值，大于该值可以认为滑动）
        this.touchSlop = configuration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }


    /**
     * @param adapter 初始化状态
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            recycler = new Recycler(adapter.getViewTypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout();//1  onMeasure   2  onLayout
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;
        if (adapter != null) {
            this.rowCount = adapter.getCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
            }
        }
        //计算高度总和
        int tmpH = sumArray(heights, 0, heights.length);
        h = Math.min(heightSize, tmpH);
        setMeasuredDimension(widthSize, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * @param changed 该参数输出当前ViewGroup的尺寸或者位置是否发生了改变
     * @param l,t,r,b 当前ViewGroup相对于父控件的坐标位置，注意 ，一定是相对于父控件。
     *                函数的参数l,t,r,b，也是由该VieGroup的父控件传过来的
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null) {
                width = r - l;
                height = b - t;
                int left, top = 0, right, bottom;
                for (int i = 0; i < rowCount && top < height; i++) {
                    right = width;
                    bottom = top + heights[i];
                    View view = makeAndStep(i, 0, top, width, bottom);
                    viewList.add(view);
                    //循环摆放
                    top = bottom;
                }

            }

        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int y2 = Math.abs(currentY - (int) event.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
            }
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
//                移动的距离   y方向
                int y2 = (int) event.getRawY();
//         //            上滑正  下滑负
                int diffY = currentY - y2;
//                画布移动  并不影响子控件的位置
                scrollBy(0, diffY);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
//        scrollY表示 第一个可见Item的左上顶点 距离屏幕的左上顶点的距离
        scrollY += y;
        scrollY = scrollBounds(scrollY);
//        scrolly
        if (scrollY > 0) {
//              上滑正  下滑负  边界值
            while (scrollY > heights[firstRow]) {
//      1 上滑移除  2 上划加载  3下滑移除  4 下滑加载
                removeView(viewList.remove(0));
                scrollY -= heights[firstRow];
                firstRow++;
            }
            while (getFillHeight() < height) {
                int addLast = firstRow + viewList.size();
                View view = obtainView(addLast, width, heights[addLast]);
                viewList.add(viewList.size(), view);
            }
        } else if (scrollY < 0) {
//            4 下滑加载
            while (scrollY < 0) {
                int firstAddRow = firstRow - 1;
                View view = obtainView(firstAddRow, width, heights[firstAddRow]);
                viewList.add(0, view);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
//             3下滑移除
            while (sumArray(heights, firstRow, viewList.size()) - scrollY - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }
        } else {
        }
        repositionViews();

    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int key = (int) view.getTag(R.id.tag_type_view);
        recycler.put(view, key);
    }


    private int scrollBounds(int scrollY) {
//        上滑
        if (scrollY > 0) {
        } else {
//            极限值  会取零  非极限值的情况下   socrlly
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));

        }
        return scrollY;
//        下滑

    }

    private void repositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (View view : viewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }
    }

    private int getFillHeight() {
//        数据的高度 -scrollY
        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private int getFilledHeight() {
//        数据高度-scrolly
        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private View makeAndStep(int row, int left, int top, int right, int bottom) {
        View view = obtainView(row, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtainView(int row, int width, int height) {
        int itemType = adapter.getItemViewType(row);
        View recyclerView = recycler.get(itemType);
        View view = null;
        if (recyclerView == null) {
            view = adapter.onCreateViewHodler(row, recyclerView, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHodler  必须填充布局");
            }
        } else {
            view = adapter.onBinderViewHodler(row, recyclerView, this);
        }
        view.setTag(R.id.tag_type_view, itemType);
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        addView(view, 0);
        return view;
    }

    //    firstIndex  firstIndex+count
    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    interface Adapter {
        View onCreateViewHodler(int position, View convertView, ViewGroup parent);

        View onBinderViewHodler(int position, View convertView, ViewGroup parent);

        //Item的类型
        int getItemViewType(int row);

        //Item的类型数量
        int getViewTypeCount();

        int getCount();

        public int getHeight(int index);
    }
}
