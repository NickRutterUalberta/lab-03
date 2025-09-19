package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.listycitylab3.City;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
    }
    private AddCityDialogListener listener;

    private City initialCity;
    public AddCityFragment(){

    }
    public AddCityFragment(City city){
        this.initialCity = city;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implementAddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        String input;
        String positive;
        if(initialCity != null){
            editCityName.setText(initialCity.getName());
            editProvinceName.setText(initialCity.getProvince());
            input = "Edit City";
            positive = "Save";
        }else{
            input = "Add a city";
            positive = "Add";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle(input)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positive, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (initialCity == null) {
                        listener.addCity(new City(cityName, provinceName));
                    } else{
                        initialCity.setName(cityName);
                        initialCity.setProvince(provinceName);
                        listener.addCity(null);
                    }
                })
                .create();
    }
}