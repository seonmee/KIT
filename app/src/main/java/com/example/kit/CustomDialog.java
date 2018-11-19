package com.example.kit;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kit.Bean.ScrapNewsBean;
import com.example.kit.DB.DatabaseHelper;

import java.net.PasswordAuthentication;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private static final int LAYOUT = R.layout.memo_dialog;

    private Context mContext;

    private EditText mMemoTxt;

    private TextView cancelTx;
    private TextView storeTx;

    private String mMemo;
    private ScrapNewsBean mSb;
    private DatabaseHelper mDb;

    public CustomDialog(Context context) {
        super(context);
        mContext = context;

    }
    public CustomDialog(Context context, ScrapNewsBean sb, DatabaseHelper db){
        super(context);
        mContext = context;
        mSb = sb;
        mDb = db;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        mMemoTxt = (EditText)findViewById(R.id.note);

        cancelTx = (TextView)findViewById(R.id.memo_cancel);
        storeTx = (TextView)findViewById(R.id.memo_store);

        mMemo = mSb.getmMemo();

            mMemoTxt.setText(mMemo);

        cancelTx.setOnClickListener(this);
        storeTx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memo_cancel:
                cancel();
                break;
            case R.id.memo_store:
                mDb.setMemo(mMemoTxt.getText().toString(),mSb.getmTitle());
                /* db에 저장된 memo 내용을 다시 setText */
                ScrapNewsBean news = mDb.getNewsItem2(mSb.getmTitle());
                mMemoTxt.setText(news.getmMemo());
                Toast.makeText(mContext,mMemoTxt.getText().toString(),Toast.LENGTH_LONG).show();
                break;
             default:
                 break;

        }
    }

}
