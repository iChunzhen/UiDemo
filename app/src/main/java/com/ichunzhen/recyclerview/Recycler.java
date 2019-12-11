package com.ichunzhen.recyclerview;

import android.view.View;

import java.util.Stack;

/**
 * @Author yuancz
 * @Date 2019/12/3-15:52
 * @Email ichunzhen6@gmail.com
 */
public class Recycler {
    private Stack<View>[] views ;
    public Recycler(int typeNumber) {
        views = new Stack[typeNumber];
        for (int i=0;i<typeNumber;i++) {
            views[i] = new Stack<View>();
        }
    }
    public void put(View view, int type){
//        把项压入堆栈顶部
        views[type].push(view);
    }
    public View get(int type) {
        try {
//            移除堆栈顶部的对象，并作为此函数的值返回该对象。
            return views[type].pop();
        } catch (Exception e) {
            return null;
        }


    }

}
