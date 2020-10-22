package com.amoor.SmallMarket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amoor.SmallMarket.R;
import com.amoor.SmallMarket.data.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.Hold> {

    private Context context;
    private ArrayList<Product> all_products = new ArrayList<>();


    public ProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new Hold(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Hold hold, int i) {

        hold.ItemProductTvProductId.setText(all_products.get(i).getPro_id());
        hold.ItemProductTvProductName.setText(all_products.get(i).getPro_name());
        hold.ItemProductTvProductPrice.setText(all_products.get(i).getPro_price());

        setAction(hold, i);

    }


    @Override
    public int getItemCount() {
        return all_products.size();
    }

    public void setData(ArrayList<Product> all_products) {
        this.all_products = all_products;
    }

    public class Hold extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.Item_Product_Tv_Product_id)
        TextView ItemProductTvProductId;
        @BindView(R.id.Item_Product_Tv_Product_Name)
        TextView ItemProductTvProductName;
        @BindView(R.id.Item_Product_Tv_Product_Price)
        TextView ItemProductTvProductPrice;
        @BindView(R.id.Item_Product_Ll_Container)
        LinearLayout ItemProductLlContainer;

        public Hold(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

    private void setAction(Hold hold, final int i) {
//        hold.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(context, DetailesActivity.class);
//                intent.putExtra("product_details",all_products.get(i));
//                context.startActivity(intent);
//                ((Activity) context).finish();
////                Toast.makeText(context, all_products.get(i).getPro_name(), Toast.LENGTH_SHORT).show();
////                AddProductFragment addProductFragment = new AddProductFragment();
////                Product product = all_products.get(i);
////                HelperMethods.replace(addProductFragment, ((Main2Activity) context).getSupportFragmentManager(), R.id.Main2_Container);
//////                addProductFragment.setTilToUpdate(product.getPro_id(), product.getPro_name(), product.getPro_price());
//            }
//        });
    }

}

