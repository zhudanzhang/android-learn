package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

//使用实现接口的方式来进行注册监听器
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.edit_text);
        imageView  = (ImageView) findViewById(R.id.image_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//                通过findViewById()方法得到EditText的实例，然后在按钮的点击事件里调用EditText的getText()方法获取到输入的内容，再调用toString()方法转换成字符串
                String inputText = editText.getText().toString();
                Toast.makeText(MainActivity2.this, inputText,
                        Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.img2);

                //                显示隐藏
//                使用的是setVisibility()方法，可以传入View.VISIBLE、View.INVISIBLE和View.GONE这3种值
//                if (progressBar.getVisibility() == View.GONE) {
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    progressBar.setVisibility(View.GONE);
//                }

//                进度条
                int progress = progressBar.getProgress();
                progress = progress + 10;
                progressBar.setProgress(progress);


//          弹框 弹出一个对话框
            AlertDialog.Builder dialog = new AlertDialog.Builder (MainActivity2.this);
            dialog.setTitle("This is Dialog");
            dialog.setMessage("Something important.");
            dialog.setCancelable(false);
//            调用setPositiveButton()方法为对话框设置确定按钮的点击事件，调用setNegativeButton()方法设置取消按钮的点击事件，最后调用show()方法将对话框显示出来
            dialog.setPositiveButton("OK", new DialogInterface.
                    OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.
                    OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
//            dialog.show();

//            ProgressDialog会在对话框中显示一个进度条，一般用于表示当前操作比较耗时，让用户耐心地等待
                ProgressDialog progressDialog = new ProgressDialog
                        (MainActivity2.this);
                progressDialog.setTitle("This is ProgressDialog");
                progressDialog.setMessage("Loading...");
//                注意，如果在setCancelable()中传入了false，表示ProgressDialog是不能通过Back键取消掉的，这时你就一定要在代码中做好控制，当数据加载完成后必须要调用ProgressDialog的dismiss()方法来关闭对话框，否则ProgressDialog将会一直存在
                progressDialog.setCancelable(true);
                progressDialog.show();
            break;
            default:
                break;
        }
    }
}