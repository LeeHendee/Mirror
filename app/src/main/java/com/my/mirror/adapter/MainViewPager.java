package com.my.mirror.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dllo on 16/3/30.
 * 可以上下滑动的自定义viewpager
 */
public class MainViewPager extends ViewPager{
    private float startX,startY;


    public MainViewPager(Context context) {
        super(context);
        init();
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        /**
         * 设置ViewPager.PageTransformer将被调用每当滚动位置改变每个附页。
         * 这使应用程序应用自定义属性变换的每一页，覆盖默认滑动外表和感觉。
         */
        setPageTransformer(true,new VerticalPageTransformer());
        /**
         * 设置此观点的过度滚动模式。有效过滚动模式反弹时始终（默认），
         * OVER_SCROLL_IF_CONTENT滚动（仅允许过滚动如果视图含量比容器大），
         * 或OVER_SCROLL_NEVER。设置一个视图的过滚动模式将只有如果视图是能够滚动的效果。
         */
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    //VerticalPageTransformer
    //这里不懂
    private class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        /**
         * 当前页面的显示透明度为1  看不到的为0
         */
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);

                /**
                 * 设置此观点相对于其左侧位置的水平位置。这有效地定位所述对象后的布局，
                 * 除了无论对象的布局放置它。
                 */
                page.setTranslationX(page.getWidth() * -position);

                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }


    private MotionEvent swapXY(MotionEvent event) {
        //获得当前宽高
        float width = getWidth();
        float height = getHeight();
        //获得运动后的宽高
        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;
        //给运动事件设置运动的位置
        event.setLocation(newX, newY);

        return event;
    }


    /**
     *此方法来拦截悬停事件
     */

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
        swapXY(event);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }


    /**
     * 次方法可拦截所有触屏移动事件
     * @param event
     * @return
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //是否截获
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
        swapXY(event);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN://按下时候的状态
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE://滑动的时候的状态
                float moveX = event.getX();
                float moveY = event.getY();
                if(Math.abs(moveY - startY) - Math.abs(moveX - startX) > 0){
                      return true;
                }
                break;
        }
        return intercepted;
    }
}
