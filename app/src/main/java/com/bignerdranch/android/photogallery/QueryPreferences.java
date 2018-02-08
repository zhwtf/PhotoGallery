package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by zhenghao on 2017-07-19.
 */

public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_RESULT_ID = "lastResultId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);

    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null);
    }

    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_IS_ALARM_ON,false);
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }

}

/*
    取出查询字符串值非常简单，调用SharedPreferences.getString(...)就可以了。如果
是其他类型数据，就调用对应的取值方法，比如getInt(...)。SharedPreferences.getString
(PREF_SEARCH_QUERY, null)方法的第二个参数指定默认返回值；如果找不到PREF_SEARCH_
QUERY对应的值，就返回null值。
    setStoredQuery(Context)方法向指定context的默认shared preferences写入查询输入值。在
以上代码中，调用SharedPreferences.edit()方法，可获取一个SharedPreferences.Editor
实例。它就是在SharedPreferences中保存查询信息要用到的类。与FragmentTransaction的
使用类似，利用SharedPreferences.Editor，可将一组数据操作放入一个事务中。如有一批数
据要更新，在一个事务中进行批量数据存储写入操作就可以了。
    完成所有数据的变更准备后，调用SharedPreferences.Editor的apply()异步方法写入数
据。这样，该SharedPreferences文件的其他用户就能看到写入的数据了。apply()方法首先在
内存中执行数据变更，然后在后台线程上真正把数据写入文件。
    QueryPreferences是PhotoGallery应用的数据存储引擎。既然已经搞定了查询信息的读取和
写入方法，现在就来在PhotoGalleryFragment中应用它们。
 */