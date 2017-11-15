package com.blacklightning.parkhere;

import android.drm.DrmStore;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.text.TextUtils;


import com.google.firebase.database.Transaction;


public class PayPalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

    }
    PayPalActivity(ContactsContract.Data data,DrmStore.Action actions) {
                /*
                 * Set up the payment here
                 */
    }

   private void onAuthorize(ContactsContract.Data data, DrmStore.Action actions) {
                /*
                 * Execute the payment here
                 */
    }

   private void onCancel(ContactsContract.Data data,Transaction actions) {
                /*
                 * Buyer cancelled the payment
                 */
                Toast.makeText(this, "You cancelled the payment!",Toast.LENGTH_SHORT).show();

    }

    private void onError(String err) {
                /*
                 * An error occurred during the transaction
                 */
               Toast.makeText(this, "An error occurred during the transaction",Toast.LENGTH_SHORT).show();
    }
    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
