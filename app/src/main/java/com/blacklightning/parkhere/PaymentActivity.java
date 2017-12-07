package com.blacklightning.parkhere;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import com.recurly.android.RecurlyApi;



public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    RecurlyApi recurlyApi = RecurlyApi.getInstance(getApplicationContext(),"ewr1-5DRj9vkWW6QMYauP2cw3sL");
    Button payPal,venmo,craigList,recurly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payPal = (Button)findViewById(R.id.button);
        venmo = (Button)findViewById(R.id.button_1);
        craigList = (Button)findViewById(R.id.button_2);
        recurly = (Button)findViewById(R.id.button_3);

        payPal.setOnClickListener(this);
        venmo.setOnClickListener(this);
        craigList.setOnClickListener(this);
        recurly.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        if(v == payPal) {
            intent.setData(Uri.parse("https://www.paypal.com/signin?country.x=US&locale.x=en_US"));
            startActivity(intent);
        }
        else if(v == venmo){
            intent.setData(Uri.parse("https://venmo.com/"));
            startActivity(intent);}
        else if(v == craigList){
            intent.setData(Uri.parse("https://sfbay.craigslist.org/sby/"));
            startActivity(intent);}
        else
            intent.setData(Uri.parse("https://recurly.com/"));
            startActivity(intent);
    }
}