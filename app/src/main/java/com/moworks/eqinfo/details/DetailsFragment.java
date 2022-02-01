package com.moworks.eqinfo.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.moworks.eqinfo.R;
import com.moworks.eqinfo.pojo.EqModel;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DetailsFragment extends Fragment {

    private static final String MAP_SEGMENT = "map";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_details, container, false);

        EqModel args = DetailsFragmentArgs.fromBundle(requireArguments()).getItem();

        bind(root , args);




        return root;
    }



    private void bind(View root ,  EqModel args  ){
        TextView location = root.findViewById(R.id.location);
        TextView date  = root.findViewById(R.id.date);
        TextView magnitude  = root.findViewById(R.id.magnitude);

        bindLocation(location ,args);
        magnitude.setText(getString(R.string.magnitude, args.getMag()));

        String dateFormat = new SimpleDateFormat("MMM dd, yyyy, HH:mm a" , Locale.getDefault()).format(args.getTime());
        date.setText(dateFormat);

        View mapIndicator = root.findViewById(R.id.indicator);

        mapIndicator.setOnClickListener(v->{
            locationMap(args);
        });

    }


    private void bindLocation( TextView location , EqModel args){
        if (args.getPlace().contains("null")){
            location.setText(R.string.no_place);

        }else  location.setText(args.getPlace());
    }


    private void locationMap(EqModel args){
        Uri  uri = Uri.parse(args.getMapUrl());
        Uri mapUri = Uri.withAppendedPath( uri , MAP_SEGMENT);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(mapUri);

        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null){
            startActivity(mapIntent);
        }

    }

}

