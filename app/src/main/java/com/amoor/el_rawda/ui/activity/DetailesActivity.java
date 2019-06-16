package com.amoor.el_rawda.ui.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amoor.el_rawda.R;
import com.amoor.el_rawda.data.model.Product;
import com.amoor.el_rawda.helper.HelperMethods;
import com.amoor.el_rawda.ui.fragment.detailsActivity.DisplayDetailsFragment;

public class DetailesActivity extends AppCompatActivity {

    private Product product_details = new Product();
    private DisplayDetailsFragment displayDetailsFragment = new DisplayDetailsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);

        HelperMethods.replace(displayDetailsFragment, getSupportFragmentManager(), R.id.Details_Activity_Fl_Container);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent.hasExtra("product_details"))
        {
            product_details = intent.getExtras().getParcelable("product_details");
            displayDetailsFragment.setProductOnFragment(product_details);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_product,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(DetailesActivity.this, Main2Activity.class));
                finish();
                break;
            case R.id.edit_product:
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(DetailesActivity.this,Main2Activity.class);
//                intent.putExtra("edit","edit");
//                intent.putExtra("pro_details",product_details);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailesActivity.this, Main2Activity.class));
        finish();
    }
}
