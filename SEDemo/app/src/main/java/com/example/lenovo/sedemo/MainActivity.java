package com.example.lenovo.sedemo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public  class MainActivity extends Activity implements Button.OnClickListener{
       /**定义程序要用到的类对象*/
       private File file;
       private  String path;
       private  String info;
       private String theKey_formInput;
       private TextView show_Result;
       private EditText input_SearchKey_Edit;
       private Button toSearch_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*通过findViewById()获取XML中的UI对象*/
        show_Result = (TextView) findViewById(R.id.TextView_Result );
        input_SearchKey_Edit = (EditText) findViewById(R.id.input_KEY_EditText );
        toSearch_Button = (Button) findViewById(R.id. Button_Search );
        /*为搜索按钮添加点击事件监听器*/
        toSearch_Button.setOnClickListener(this);
        /*初始化一个Field 对象，指定路径为/sdcard*/
        file = new File("/sdcard");
        /*从xml中获取字符串*/
        info = getString(R.string. info );

    }
    /*按钮点击事件处理*/
    @Override
    public void onClick(View v) {
        /*清空*/
        path="";
        show_Result.setText("");
        /*取得输入框中的要查询的Key*/
        theKey_formInput = input_SearchKey_Edit.getText().toString();
        /*浏览文件*/
        BrowserFile(file);

    }

    public  void BrowserFile(File file){
        if (theKey_formInput.equals("")){
            /*如果输入框没有输入点击搜索按钮，提示输入*/
            Toast. makeText (this, getString(R.string. pleaseInput ), Toast. LENGTH_SHORT ).show();

        }else {

            /*开始搜索文件*/
            ToSearchFiles(file);
            /*搜索完毕后，如果搜到结果为空，提示没有找到*/
            if(show_Result.getText().equals("")){
                Toast. makeText (this, getString(R.string.
                        notFond ),
                        Toast. LENGTH_SHORT ).show();
            }
        }

    }
    /*开始搜索文件方法*/
    public  void ToSearchFiles(File file){
        /*定义一个File文件数组，用来存放/sdcard目录下的文件或文件夹*/
        File[] the_Files = file.listFiles();
        /*通过遍历所有文件和文件夹*/
         for (File tempF : the_Files) {
             if (tempF.isDirectory()) {
                 ToSearchFiles(tempF);
                 /*如果是文件夹的话继续遍历搜索*/
             } else {
                 try {
                     /*是文件，进行比较，如果文件名称中包含输入搜索Key，则返回大于-1的值*/
                     if (tempF.getName().indexOf(theKey_formInput) > -1) {
                         /*获取符合条件文件的路径，进行累加*/
                         path += "\n" + tempF.getPath();
                         /*显示结果的TextView显示信息和搜索到的路径*/
                         show_Result.setText(info + path);
                     }
                 } catch (Exception e) {
                     // TODO: handle exception
                     /*如果路径找不到，提示出错*/
                     Toast.makeText(this, getString(R.string.pathError), Toast.LENGTH_SHORT).show();
                 }
             }
         }
    }
}
