package work.kocwillnever.bettertimer;

import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = "Data Test";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Content content = new Content(getApplicationContext());

        Content.User currentUser = content.getCurrentUser();
        Log.d(TAG, "=== getting user information ===");
        Log.d(TAG,
            "username is " + currentUser.getName() + "\n" +
            "motto is " + currentUser.getMotto() + "\n" +
            "coin number is " + currentUser.getCoinNum());

        Log.d(TAG, "=== getting plans information ===");
        List<Content.Plan> plans = content.getPlans();
        for (Content.Plan plan: plans) {
            Log.d(TAG,
                    "plan id is " + plan.getId() + "\n" +
                    "plan name is " + plan.getName() + "\n-----");
        }


        Log.d(TAG, "onCreate: === getting goods information ===");
        List<Content.Goods> allGoods = content.getAllGoods();
        for (Content.Goods goods: allGoods) {
            Log.d(TAG,
                    "good id is " + goods.getId() + "\n" +
                    "goods name is " + goods.getName() + "\n" +
                    "goods price is " + goods.getPrice() + "\n-----"
            );
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(goods.getImage());
            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root);
            layout.addView(imageView);
        }
    }
}
