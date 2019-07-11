# CustomerView

CustomerView是一个可拓展的自定义View，支持虚线，曲线，点线等视图；
可通过调节属性来控制线条风格，属性简介如下：

mStrokeColor   	    //描边颜色

mStrokeWidth   	    //描边宽度

mStartPosition 	    //起始坐标

mEndPosition   	    //截至坐标

mIsAnim    		      //是否有动画

mAdvance       	    //点间距

mDashIntervalsOn    //虚线中实线间距

mDashIntervalsOff   //虚线中实线宽度

mCornerRadius  	    //折线转角处圆滑角度

mSlideDirection	    //动画运动方向

mOrientation   	    //线条方向，水平或者垂直

mStyle         	    //图形风格，支持实线，折线，虚线，圆点

在xml文件中使用如下：

<com.jaymz.customerview.view.PathEffectView
 android:id="@+id/path_effect_view2"
 android:layout_width="300px"
 android:layout_height="300px"
 app:dotAdvance="30px"
 app:isAnim="true"
 app:orientation="horizontal"
 app:slideDirection="start"
 app:strokeWidth="3px"
 app:style="dotted"/>
