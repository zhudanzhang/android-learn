package com.example.helloworld;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

//知晓当前是在哪一个活动

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
//        onCreate()方法中调用了ActivityCollector的addActivity()方法，表明将当前正在创建的活动添加到活动管理器里
        ActivityCollector.addActivity(this);
    }
//    然后在BaseActivity中重写onDestroy()方法，并调用了ActivityCollector的removeActivity()方法，表明将一个马上要销毁的活动从活动管理器里移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

////  当然你还可以在销毁所有活动的代码后面再加上杀掉当前进程的代码，以保证程序完全退出       android.os.Process.killProcess(android.os.Process.myPid());
//killProcess()方法用于杀掉一个进程，它接收一个进程id参数，我们可以通过myPid()方法来获得当前程序的进程id。需要注意的是，killProcess()方法只能用于杀掉当前程序的进程，