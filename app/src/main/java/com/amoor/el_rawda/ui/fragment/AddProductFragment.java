package com.amoor.el_rawda.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amoor.el_rawda.R;
import com.amoor.el_rawda.data.model.Product;
import com.amoor.el_rawda.helper.DBConnection;
import com.amoor.el_rawda.helper.HelperMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.amoor.el_rawda.helper.HelperMethods.getTextFromTil;
import static com.amoor.el_rawda.helper.HelperMethods.setTexttoTil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {


    @BindView(R.id.Add_Product_Fragment_Til_Product_Name)
    TextInputLayout AddProductFragmentTilProductName;
    @BindView(R.id.Add_Product_Fragment_Til_Product_Price)
    TextInputLayout AddProductFragmentTilProductPrice;
    @BindView(R.id.Add_Product_Fragment_Til_Product_id)
    TextInputLayout AddProductFragmentTilProductId;
    Unbinder unbinder;
    private DBConnection db;
    private HomeFragment homeFragment = new HomeFragment();
    private View v;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_product, container, false);
        unbinder = ButterKnife.bind(this, v);

        db = new DBConnection(getContext());

//        AddProductFragmentTilProductName = v.findViewById(R.id.Add_Product_Fragment_Til_Product_Name);
//        AddProductFragmentTilProductPrice = v.findViewById(R.id.Add_Product_Fragment_Til_Product_Price);
//        AddProductFragmentTilProductId = v.findViewById(R.id.Add_Product_Fragment_Til_Product_id);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Add_Product_Fragment_Btn_Save)
    public void onViewClicked() {
        String pro_name = getTextFromTil(AddProductFragmentTilProductName);
        String pro_price = getTextFromTil(AddProductFragmentTilProductPrice);
        String pro_id = getTextFromTil(AddProductFragmentTilProductId);
        if (!TextUtils.isEmpty(pro_name)
                && !TextUtils.isEmpty(pro_price)
                && !TextUtils.isEmpty(pro_id)) {
            db.insertProductRow(getContext(), pro_id, pro_name, pro_price);

            AddProductFragmentTilProductName.getEditText().setText("");
            AddProductFragmentTilProductPrice.getEditText().setText("");
            AddProductFragmentTilProductId.getEditText().setText("0");

//            HelperMethods.replace(homeFragment, getActivity().getSupportFragmentManager(), R.id.Main2_Container, "HomeFragment");
        }
        else {
            Toast.makeText(getContext(), "Write all information", Toast.LENGTH_SHORT).show();
        }
    }

    public void setProductIDFromCamera(String productIDFromCamera)
    {
        AddProductFragmentTilProductId.getEditText().setText(productIDFromCamera);
    }

    public void setTilToUpdate(String pro_id, String pro_name, String pro_price)
    {
//        Toast.makeText(getContext()," hey from add product "+pro_id+"  "+pro_name+"  "+pro_price, Toast.LENGTH_SHORT).show();
//        if (AddProductFragmentTilProductName!=null&&AddProductFragmentTilProductId!=null&&AddProductFragmentTilProductPrice!=null)
//        {
//        AddProductFragmentTilProductId.getEditText().setText("11");
//            setTexttoTil(AddProductFragmentTilProductId,pro_id);
//            setTexttoTil(AddProductFragmentTilProductName,pro_name);
//            setTexttoTil(AddProductFragmentTilProductPrice,pro_price);
//            Toast.makeText(getContext()," hey from add product "+pro_id+"  "+pro_name+"  "+pro_price, Toast.LENGTH_SHORT).show();

//        }else
//        {
//            Toast.makeText(getContext(), "they are null pointer exception", Toast.LENGTH_SHORT).show();
//        }

    }

    public void EditProduct(Product pro_details)
    {
//        AddProductFragmentTilProductName.getEditText().setText(pro_details.getPro_name());
//        AddProductFragmentTilProductPrice.getEditText().setText(pro_details.getPro_price());
//        AddProductFragmentTilProductId.getEditText().setText(pro_details.getPro_id());
    }
}
