package com.gl.shakedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ProductActivity extends AppCompatActivity {
    private ImageView ivProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//        Window window = getWindow();
//        // window在顶部显示
//        window.setGravity(Gravity.TOP);
//        // 将dialog设为宽度匹配父容器
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);

        ivProduct = findViewById(R.id.ivProduct);
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
