package com.example.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText trans;
    TextView transTv, expTv;
    Button search;
    LinearLayout transLi, expLi;
    String exp, tran, pho;
    String from = "auto", to = "auto", q;
    TextRes tr;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickAndListener();
    }

    private void init() {
        trans = findViewById(R.id.trans);
        transTv = findViewById(R.id.trans_tv);
        expTv = findViewById(R.id.exp_tv);
        search = findViewById(R.id.search);
        transLi = findViewById(R.id.trans_l);
        expLi = findViewById(R.id.exp_l);
        spinner = findViewById(R.id.lan_spinner);
    }

    private void clickAndListener() {
        search.setOnClickListener(v -> textResult());

        trans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    transLi.setVisibility(View.GONE);
                    expLi.setVisibility(View.GONE);
                }
                search.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spinnerListener();
    }

    private void textResult() {
        q = trans.getText().toString().trim();
        TextTranslator t = new TextTranslator(q);
        Call<TextRes> call = t.getCall(from, to);
        call.enqueue(new Callback<TextRes>() {
            @Override
            public void onResponse(@NonNull Call<TextRes> call, @NonNull Response<TextRes> response) {
                tr = response.body();
                if (tr != null) {
                    int code = tr.getErrorCode();
                    if (0 == code) {
                        exp = tr.getExplains();
                        tran = tr.getTrans();
                        pho = tr.getUkPho();
                        if (pho != null) {
                            transTv.setText(tran + pho);
                        } else {
                            transTv.setText(tran);
                        }
                        transLi.setVisibility(View.VISIBLE);
                        search.setVisibility(View.GONE);
                        if (!tran.equals(exp) && exp != null) {
                            expTv.setText(exp);
                            expLi.setVisibility(View.VISIBLE);
                        }
                    } else if (113 == code) {
                        toaPrint("请输入翻译内容");
                    } else toaPrint("请求错误，错误码为:" + tr.getErrorCode());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TextRes> call, @NonNull Throwable t) {
                Log.i("TAG", "onFailure");
                toaPrint("请检查网络设置！");
            }
        });
    }

    private void toaPrint(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void spinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1://英文 → 中文
                        from = "en";
                        to = "zh-CHS";
                        break;
                    case 2://中文 → 英文
                        from = "zh-CHS";
                        to = "en";
                        break;
                    case 3://中文 → 日语
                        from = "zh-CHS";
                        to = "ja";
                        break;
                    case 4://中文 → 韩语
                        from = "zh-CHS";
                        to = "ko";
                        break;
                    case 5://中文 → 法语
                        from = "zh-CHS";
                        to = "fr";
                        break;
                    case 6://中文 → 俄语
                        from = "zh-CHS";
                        to = "ru";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}