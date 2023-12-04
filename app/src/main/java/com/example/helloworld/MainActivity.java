package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//在代码中通过R.string.app_name可以获得该字符串的引用
//在XML中通过@string/app_name可以获得该字符串的引用

// AppCompatActivity 是一种向下兼容的Activity，可以将Activity在各个系统版本中增加的特性和功能最低兼容到Android 2.1系统
// Activity是Android系统提供的一个活动基类，所有的活动都必须继承它或者它的子类才能拥有活动的特性（AppCompatActivity是Activity的子类）
public class MainActivity extends BaseActivity {

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

//    生命周期 1 onCreate 在活动第一次被创建的时候调用
    // 项目中的任何活动都应该重写Activity的onCreate()方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 调用了setContentView()方法，就是这个方法给当前的活动引入了一个布局
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

//        1. 点击按钮跳出弹框
//        findViewById()方法获取到在布局文件中定义的元素，findViewById()方法返回的是一个View对象，我们需要向下转型将它转成Button对象
        Button button1 = (Button) findViewById(R.id.button_1);
        // 通过调用setOnClickListener()方法为按钮注册一个监听器，点击按钮时就会执行监听器中的onClick()方法
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               通过静态方法makeText()创建出一个Toast对象，调用show()将Toast显示出来
//                makeText()方法需要传入3个参数
//                第一个参数是Context，也就是Toast要求的上下文，由于活动本身就是一个Context对象，因此这里直接传入FirstActivity.this即可
//                第二个参数是Toast显示的文本内容
//                第三个参数是Toast显示的时长，有两个内置常量可以选择Toast.LENGTH_SHORT和Toast.LENGTH_LONG
                Toast.makeText(MainActivity.this, "You clicked Button 1",
                        Toast.LENGTH_SHORT).show();

            }
        });

//        3. 点击按钮跳转活动
//        Intent 使用Intent在活动之间穿梭
//        Intent大致可以分为两种：显式Intent和隐式Intent
        Button button2 = (Button) findViewById(R.id.button_2);
//        显式Intent
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent有多个构造函数的重载，其中一个是Intent(Context packageContext,Class<? >cls)。这个构造函数接收两个参数，第一个参数Context要求提供一个启动活动的上下文，第二个参数Class则是指定想要启动的目标活动
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);


                // Activity类中提供了一个startActivity()方法，这个方法是专门用于启动活动的，它接收一个Intent参数，这里我们将构建好的Intent传入startActivity()方法就可以启动目标活动
                startActivity(intent);
            }
        });

//        隐式Intent
//        隐式Intent指定了一系列更为抽象的action和category等信息，然后交由系统去分析这个Intent，并帮我们找出合适的活动去启动，可以响应我们这个隐式Intent的活动
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 只有<action>和<category>中的内容同时能够匹配上Intent中指定的action和category时，这个活动才能响应该Intent
//                因为android.intent.category.DEFAULT是一种默认的category，在调用startActivity()方法的时候会自动将这个category添加到Intent中
                Intent intent = new Intent("com.example.helloworld.ACTION_START");
                startActivity(intent);
            }
        });

//      每个Intent中只能指定一个action，但却能指定多个category
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.helloworld.ACTION_START");
                intent.addCategory("com.example.helloworld.MY_CATEGORY");

//                传递数据
                String data = "Hello SecondActivity";
                // 通过putExtra()方法传递了一个字符串。注意这里putExtra()方法接收两个参数，第一个参数是键，用于后面从Intent中取值，第二个参数才是真正要传递的数据
                intent.putExtra("extra data", data);
                startActivity(intent);
//推荐传递数据的方式
//                SecondActivity.actionStart(MainActivity.this, "data1");

//                返回数据给上一个活动
//                startActivityForResult()方法也是用于启动活动的，但这个方法期望在活动销毁的时候能够返回一个结果给上一个活动
//                startActivityForResult()方法接收两个参数，第一个参数还是Intent，第二个参数是请求码，用于在之后的回调中判断数据的来源
//                startActivityForResult(intent, 1);
            }
        });

        // 4. 生命周期 弹框
        Button startDialogActivity = (Button) findViewById(R.id.button_10);
        startDialogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        //       活动回收 恢复数据
//        onCreate()方法其实也有一个Bundle类型的参数。这个参数在一般情况下都是null，但是如果在活动被系统回收之前有通过onSaveInstanceState()方法来保存数据的话，这个参数就会带有之前所保存的全部数据

        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("data key");
            Log.d(TAG, tempData);
        }
    }

    //onActivityResult()方法带有三个参数，第一个参数requestCode，即我们在启动活动时传入的请求码。第二个参数resultCode，即我们在返回数据时传入的处理结果。第三个参数data，即携带着返回数据的Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 由于在一个活动中有可能调用startActivityForResult()方法去启动很多不同的活动，每一个活动返回的数据都会回调到onActivityResult()这个方法中，因此我们首先要做的就是通过检查requestCode的值来判断数据来源
        switch (requestCode) {
            case 1:
                // 确定数据是从SecondActivity返回的之后，我们再通过resultCode的值来判断处理结果是否成功。最后从data中取值并打印出来，这样就完成了向上一个活动返回数据的工作
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.d("MainActivity", returnedData);
                }
                break;
            default:
        }
    }

//    2. 点击显示菜单
//    onCreateOptionsMenu 给这个方法返回true，表示允许创建的菜单显示出来，如果返回了false，创建的菜单将无法显示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        通过getMenuInflater()方法能够得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单
//        inflate()方法接收两个参数，第一个参数用于指定我们通过哪一个资源文件来创建菜单，这里当然传入R.menu.main。第二个参数用于指定我们的菜单项将添加到哪一个Menu对象当中，这里直接使用onCreateOptionsMenu()方法中传入的menu参数。
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//  重写onOptionsItemSelected()方法  定义菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        通过调用item.getItemId()来判断我们点击的是哪一个菜单项
        switch (item.getItemId()) {
            case R.id.add_item:
            Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
            break;
            case R.id.remove_item:
            Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
            break;
            default:
        }
        return true;
    }


    //    生命周期2 onStart() 这个方法在活动由不可见变为可见的时候调用
    //    生命周期3 onResume() 这个方法在活动准备好和用户进行交互的时候调用。此时的活动一定位于返回栈的栈顶，并且处于运行状态
    //    生命周期4 onPause()  这个方法在系统准备去启动或者恢复另一个活动的时候调用。我们通常会在这个方法中将一些消耗CPU的资源释放掉，以及保存一些关键数据，但这个方法的执行速度一定要快，不然会影响到新的栈顶活动的使用
    //    生命周期5 onStop() 这个方法在活动完全不可见的时候调用。它和onPause()方法的主要区别在于，如果启动的新活动是一个对话框式的活动，那么onPause()方法会得到执行，而onStop()方法并不会执行
    //    生命周期6 onDestroy() 这个方法在活动被销毁之前调用，之后活动的状态将变为销毁状态
    //    生命周期7 onRestart() 这个方法在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了

//    完整生存期
//    活动在onCreate()方法和onDestroy()方法之间所经历的，就是完整生存期。一般情况下，一个活动会在onCreate()方法中完成各种初始化操作，而在onDestroy()方法中完成释放内存的操作。
//    可见生存期
//    活动在onStart()方法和onStop()方法之间所经历的，就是可见生存期。在可见生存期内，活动对于用户总是可见的，即便有可能无法和用户进行交互。
//    我们可以通过这两个方法，合理地管理那些对用户可见的资源。比如在onStart()方法中对资源进行加载，而在onStop()方法中对资源进行释放，从而保证处于停止状态的活动不会占用过多内存。
//    前台生存期
//    活动在onResume()方法和onPause()方法之间所经历的就是前台生存期。在前台生存期内，活动总是处于运行状态的，此时的活动是可以和用户进行交互的，我们平时看到和接触最多的也就是这个状态下的活动。

    public static final String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

//    Activity中还提供了一个onSaveInstanceState()回调方法，这个方法可以保证在活动被回收之前一定会被调用，因此我们可以通过这个方法来解决活动被回收时临时数据得不到保存的问题。
//    onSaveInstanceState()方法会携带一个Bundle类型的参数，Bundle提供了一系列的方法用于保存数据
//    比如可以使用putString()方法保存字符串，使用putInt()方法保存整型数据，以此类推。每个保存方法需要传入两个参数，第一个参数是键，用于后面从Bundle中取值，第二个参数是真正要保存的内容。
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something you just typed";
        outState.putString("data_key", tempData);

//        Intent还可以结合Bundle一起用于传递数据，首先可以把需要传递的数据都保存在Bundle对象中，然后再将Bundle对象存放在Intent里。到了目标活动之后先从Intent中取出Bundle，再从Bundle中一一取出数据
    }
}