package com.bwie.myutilsclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * date:2019/1/3
 * author:张自力(DELL)
 * function:  封装类
 */

public class MyUtils {

    /**
     * Intent跳转各种封装（使用单例模式）
     *
     *   1.不带参数的跳转
     *   2.带参数的跳转
     *   3.不带参数传值
     *   4.带参数的传值
     *   5.接收参数然后再返回数值
     *
     * */
    public static MyUtils instance;//静态对象
    public static MyUtils getInstance(){//静态构造
        if(instance==null){//判空
            synchronized (MyUtils.class){//双重锁模式
                instance = new MyUtils();
            }
        }
        return instance;
    }
    //无惨构造
    private MyUtils(){

    }

    /**
     * 1.不带参数的跳转
     *
     * 参数：
     *   @param context:当前页面
     *   @param classs:要跳转的页面
     * */
    public static void intent(Context context, Class<?> classs){
        Intent intent = new Intent(context,classs);
        context.startActivity(intent);
    }

    /**
     * 2.带参数的跳转
     *
     * 参数：
     *   @param context:当前页面
     *   @param classs:要跳转的页面
     *   @param putData:输入值
     *
     * */
    public static void intent(Context context, Class<?> classs, Bundle putData){
        Intent intent = new Intent();
        intent.putExtras(putData);
        context.startActivity(intent);
    }

    /**
     * 3.不带参数传值
     *  @param activity  当前页
     *  @param classs  要跳转的页面
     *  @param requestCode 返回值
     * */
    public static void startActivityForResult(Activity activity, Class<?> classs, int requestCode){
        startActivityForResult(activity,classs,requestCode);
    }

    /**
     * 4.带参数的传值
     *  @param activity  当前页
     *  @param classs  要跳转的页面
     *  @param requestCode 返回值
     * */
    public static void startActivityForResult(Activity activity,Class<?> classs,int requestCode,Bundle putData){
        Intent intent = new Intent();
        intent.setClass(activity,classs);
        //判断传参是否为空
        if(null!=putData){//如果不为空，就进行存储
            intent.putExtras(putData);
        }
        startActivityForResult(activity,classs,requestCode);
    }

    /**
     * 5.接收参数然后再返回数值
     * 参数
     *   @param activity  当前页
     *   @param getData  传过来的参数
     *   @param RESULT_OK 系统规定的标识参数为-1
     *          public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final int RESULT_FIRST_USER = 1;
     *
     * */
    public static void setResult(Activity activity,Bundle getData,int RESULT_OK){
        Intent intent = new Intent();
        intent.putExtras(getData);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }


    /**
     * 6 设置全屏的工具类封装
     *
     *   参数设置
     *   @param activity 当前页
     *   @param isFull 是否设置为全屏  （true为全屏  false 为非全屏）
     * */
    public static void setFullScreen(Activity activity,boolean isFull){
        //1 得到当前Activity的视图窗
        Window window = activity.getWindow();
        //2 得到窗口属性
        WindowManager.LayoutParams params = window.getAttributes();
        //3 判断是否设置为全屏
        if(isFull){//如果是设置为全屏
            //(Params:参数  FLAG：标记 FULLSCREEN: 全屏版面 SCREEN:屏幕 )
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //给当前窗口设置全屏属性( Attributes:属性)
            window.setAttributes(params);
            //添加标记 (FLAG_LAYOUT_NO_LIMITS:标识布局没有限制)
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{//如果是不设置为全屏
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //设置属性
            window.setAttributes(params);
            //添加标记
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 7 强制设置屏幕方向垂直方向(ScreenVertical)
     *参数
     *   @param activity 当前界面
     *
     * */
    public static void setScreenVertical(Activity activity){
        //Requested:要求的、被请求的  PORTRAIT:格式
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 8 强制设置屏幕方向水平方向(ScreenHorizontal)
     *参数
     *   @param activity 当前界面
     *
     * */
    public static void setScreenHorizontal(Activity activity){
        //Requested:要求的、被请求的  LANDSCAPE:风景
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 9.设置隐藏系统标题栏
     * 参数
     *   @param activity 当前界面
     *
     * 注意：在调用此方法的时候，一定要在添加内容之前，调用requestFeature()
     *      也就是在super.onCreate(savedInstanceState);之下
     *             setContentView(R.layout.activity_main)之前调用
     *      否则会报错"requestFeature() must be called before adding content"
     *
     *    原因详细博客网址：https://blog.csdn.net/u012422440/article/details/43888937
     *                   https://892848153.iteye.com/blog/1900830
     *
     * */
    public static void hideTitleBar(Activity activity){
        //参数  Feature:特征  FEATURE_NO_TITLE :特征为无标题
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 10 隐藏软件输入法
     * 参数
     *   @param activity 当前界面
     *
     * */
    public static void hideSoftInput(Activity activity){
        //参数  SOFT_INPUT_STATE_ALWAYS_HIDDEN:软键盘 规定  总是  隐藏
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     * 11 使UI适配软件输入法(adjust:调整 )
     * 参数
     *   @param activity 当前界面
     *
     * */
    public static void adjustSoftInput(Activity activity){
        //参数SOFT_INPUT_ADJUST_RESIZE 软键盘  调整  大小
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 12 小时Toast消息  并且保证运行在UI线程中
     *
     * */
    public static void toastShow(final Activity activity, final String message){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 13 使用SP进行存储数据
     *
     * 保存在手机里面的文件名，在这里要特别注意，因为在Android中已经确定了SharedPreferences是以xml形式保存，
     * 所以，在填写文件名参数时，不要给定“.xml”后缀，android会自动添加。只要直接写上文件名即可
     */
    // 未用到，本例创建SharedPreferences的方式为默认方式，默认配置文件名为preferences.xml，
    // MODE_PRIVATE：表示该配置文件只能被自己的应用程序访问
    //标识
    public static final String FILE_NAME = "share_data";
    /**
     * 13.1 保存到数据文件
     * @param context 上下文
     * @param key  存储对象的标识
     * @param data  存储的数据
     *
     * */
    public static void putData(Context context,String key,Object data){
        //1 通过存储的数据得到要存储的数据类型
        String type = data.getClass().getSimpleName();
        //2 创建sp对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //3 通过要存储数据类型进行判断并存储
        if("Integer".equals(type)){//如果是Int类型
            edit.putInt(key, (Integer) data);
        }else if("Boolean".equals(type)){//如果是布尔类型
            edit.putBoolean(key, (Boolean) data);
        }else if("String".equals(type)){//如果是字符串类型
            edit.putString(key, (String)data);
        }else if("Float".equals(type)){//如果是单精度浮点类型
            edit.putFloat(key, (Float) data);
        }else if("Long".equals(type)){//如果是long类型
            edit.putLong(key, (Long) data);
        }
        //存储
        edit.commit();
    }

    /**
     * 13.2 从文件中读取数据
     *
     * @param context 上下文
     * @param key  存储对象的标识
     * @param defaultvalue  默认的返回值
     *
     * */
    public static Object getData(Context context,String key,Object defaultvalue){
        //1 通过默认的返回值类型 得到要取出的类型
        String type = defaultvalue.getClass().getSimpleName();
        //2 得到SP对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        //3 通过类型  判断要取出的值得类型
        if("Integer".equals(type)){//如果返回值默认为0等,则返回数据类型为
            return sharedPreferences.getInt(key, (Integer) defaultvalue);
        }else if("Boolean".equals(type)){//如果返回值默认为true或false,则
            return sharedPreferences.getBoolean(key, (Boolean) defaultvalue);
        }else if("String".equals(type)){//如果返回值默认为字符串或"",则
            return sharedPreferences.getString(key, (String) defaultvalue);
        }else if("Float".equals(type)){//如果返回值默认为浮点类型0.1……,则
            return sharedPreferences.getFloat(key, (Float) defaultvalue);
        }else if("Long".equals(type)){//如果返回值默认为整数Long类型,则
            return sharedPreferences.getLong(key, (Long) defaultvalue);
        }
        //默认返回为空
        return null;
    }

    /**
     * 14.网络判断
     *
     *
     * */
    public static boolean isNetWork(Context context){
        //设置一个标识
        boolean available = false;
        //调用对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //得到NetWorkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断是否有网
        if(networkInfo!=null){
            available = networkInfo.isAvailable();
        }
        return available;
    }

    /**
     * 15. 将Https替换为Http
     * 参数：
     *   @param url 代表以https开头的路径
     *
     * */
    public static String httpsToHttp(String url){
        return url.replace("https","http");
    }

    /**
     * 16.时间格式转换
     * 参数：
     *   FORMAT_TIME:要转换为的时间格式
     *   @param time  是请求数据后的时间
     *     得到的是一个String类型的数据
     * */

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String dataConversion(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_TIME, Locale.getDefault());
        return dateFormat.format(time);
    }

}
