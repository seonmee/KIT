package com.example.kit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Adapter.ScrapNewsAdapter;
import com.example.kit.Bean.ScrapNewsBean;
import com.example.kit.DB.DatabaseHelper;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private static final int LAYOUT = R.layout.memo_dialog;

    private Context mContext;

    private EditText mMemoTxt;

    private TextView cancelTx;
    private TextView storeTx;

    private String mMemo;
    private ScrapNewsBean mSb;
    private DatabaseHelper mDb;

    public CustomDialog(Context context, ScrapNewsBean sb){
        super(context);
        mContext = context;
        mSb = sb;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        mDb = new DatabaseHelper(mContext);

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
                mSb.setmMemo(mMemoTxt.getText().toString());
                /* bookmark가 unclick 되야함*/

                cancel();

                break;
            default:
                break;

        }
    }

}
