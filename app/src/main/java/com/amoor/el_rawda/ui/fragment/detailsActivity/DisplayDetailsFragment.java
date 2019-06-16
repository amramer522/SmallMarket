package com.amoor.el_rawda.ui.fragment.detailsActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoor.el_rawda.R;
import com.amoor.el_rawda.data.model.Product;
import com.amoor.el_rawda.helper.DBConnection;
import com.amoor.el_rawda.ui.activity.DetailesActivity;
import com.amoor.el_rawda.ui.activity.Main2Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayDetailsFragment extends Fragment {

    @BindView(R.id.Display_Details_Fragment_Tv_ProductName)
    TextView DisplayDetailsFragmentTvProductName;
    @BindView(R.id.Display_Details_Fragment_Tv_ProductImg)
    ImageView DisplayDetailsFragmentTvProductImg;
    @BindView(R.id.Display_Details_Fragment_Tv_ProductPrice)
    TextView DisplayDetailsFragmentTvProductPrice;
    Unbinder unbinder;
    @BindView(R.id.Display_Details_Fragment_Tv_ProductId)
    TextView DisplayDetailsFragmentTvProductId;
    private Product product_details = new Product();
    private DBConnection db;

    public DisplayDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_details, container, false);
        unbinder = ButterKnife.bind(this, v);
        db = new DBConnection(getContext());

        DisplayDetailsFragmentTvProductId.setText("Product Id : " + product_details.getPro_id());
        DisplayDetailsFragmentTvProductName.setText("Product Name : " + product_details.getPro_name());
        DisplayDetailsFragmentTvProductPrice.setText("Product Price : " + product_details.getPro_price());


        return v;
    }

    public void setProductOnFragment(Product product_details) {
        this.product_details = product_details;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Display_Details_Fragment_Btn_DeleteProduct)
    public void onViewClicked()
    {
        db.deleteProduct(product_details.getPro_id());
        startActivity(new Intent(getContext(), Main2Activity.class));
        getActivity().finish();
    }
}
