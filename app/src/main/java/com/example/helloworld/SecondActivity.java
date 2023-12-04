package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static void actionStart(Context context, String data1) {
//        所有SecondActivity中需要的数据都是通过actionStart()方法的参数传递过来的，然后把它们存储到Intent中，最后调用startActivity()方法启动SecondActivity
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("extra data", data1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

//        获取数据
//        可以通过getIntent()方法获取到用于启动SecondActivity的Intent
        Intent intent = getIntent();
//       调用getStringExtra()方法，传入相应的键值，就可以得到传递的数据
//        如果传递的是整型数据，则使用getIntExtra()方法；如果传递的是布尔型数据，则使用getBooleanExtra()方法，以此类推
        String data = intent.getStringExtra("extra data");
        Log.d("SecondActivity", data);



        // 1. 销毁一个活动
//        只要按一下Back键就可以销毁当前的活动
//        提供了一个finish()方法，在活动中调用一下这个方法就可以销毁当前活动
        Button button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data return", "Hello MainActivity");
//                setResult()方法。这个方法非常重要，是专门用于向上一个活动返回数据的。
//                setResult()方法接收两个参数，第一个参数用于向上一个活动返回处理结果，一般只使用RESULT_OK或RESULT_CANCELED这两个值，第二个参数则把带有数据的Intent传递回去
                setResult(RESULT_OK, intent);
                finish();
            }
        });


//        隐式Intent
//        使用隐式Intent，我们不仅可以启动自己程序内的活动，还可以启动其他程序的活动，这使得Android多个应用程序之间的功能共享成为了可能。
//        比如说你的应用程序中需要展示一个网页，这时你没有必要自己去实现一个浏览器（事实上也不太可能），而是只需要调用系统的浏览器来打开这个网页就行了
        Button button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent.ACTION_VIEW  是一个Android系统内置的动作，其常量值为android.intent.action.VIEW
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                通过Uri.parse()方法，将一个网址字符串解析成一个Uri对象，再调用Intent的setData()方法将这个Uri对象传递进去
//                setData() 接收一个Uri对象，主要用于指定当前Intent正在操作的数据
//                可以在<intent-filter>标签中再配置一个<data>标签，用于更精确地指定当前活动能够响应什么类型的数据
                intent.setData(Uri.parse("http://www.baidu.com"));
//                只有<data>标签中指定的内容和Intent中携带的Data完全一致时，当前活动才能够响应该Intent。不过一般在<data>标签中都不会指定过多的内容，如上面浏览器示例中，其实只需要指定android:scheme为http，就可以响应所有的http协议的Intent了
                startActivity(intent);
            }
        });

//        除了http协议外，我们还可以指定很多其他协议，比如geo表示显示地理位置、tel表示拨打电话
        Button button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                指定了Intent的action是Intent.ACTION_DIAL，这又是一个Android系统的内置动作
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
    }



    //如果用户在SecondActivity中并不是通过点击按钮，而是通过按下Back键，如果用户在SecondActivity中并不是通过点击按钮，而是通过按下Back键回到
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
    }
}