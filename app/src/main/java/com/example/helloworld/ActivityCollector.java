package com.example.helloworld;

//知晓当前是在哪一个活动

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

// 通过一个List来暂存活动，然后提供了一个addActivity()方法用于向List中添加一个活动，提供了一个removeActivity()方法用于从List中移除活动，
// 最后提供了一个finishAll()方法用于将List中存储的活动全部销毁掉
//不管你想在什么地方退出程序，只需要调用ActivityCollector.finishAll()方法就可以了

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : activities) {
            if (! activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}