package com.moworks.eqinfo.report;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.moworks.eqinfo.R;
import com.moworks.eqinfo.pojo.EqModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class ReportAdapter extends ListAdapter<EqModel , ReportAdapter.ReportViewModel > {

    private static final  String LOCATION_SEPARATOR = "of";
    private static final String offset_location = "Near To";

    private static DiffCallback diffCallback = new DiffCallback();

    private  OnItemClickListener onItemClickListener;

    public ReportAdapter(OnItemClickListener onItemClickListener) {
        super(diffCallback);
        this.onItemClickListener = onItemClickListener ;
    }

    @NonNull
    @Override
    public ReportViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return ReportViewModel.create(parent) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewModel holder, int position) {
        EqModel item = getItem(position);
        ReportViewModel.bind(holder , item ,onItemClickListener  );
    }




    public interface OnItemClickListener{

        void onItemClick(EqModel item);

    }





    public static class ReportViewModel extends RecyclerView.ViewHolder {
        TextView magUnits ;
        TextView offsetLocation ;
        TextView primaryLocation ;
        TextView date ;
        TextView  time ;

        private ReportViewModel(View  itemView) {
            super(itemView);
            magUnits = itemView.findViewById(R.id.mag_units);
            offsetLocation = itemView.findViewById(R.id.offset_location);
            primaryLocation = itemView.findViewById(R.id.primary_location);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }

        public static ReportViewModel create(ViewGroup parent){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_item , parent , false);
            return  new ReportViewModel(itemView);
        }

        public static void bind(ReportViewModel holder, EqModel item ,  OnItemClickListener onItemClickListener){
            setMagBackground(holder.magUnits , holder.itemView.getContext() , item);
            holder.magUnits.setText(String.valueOf(item.getMag()));
            displayLocation(holder , item);
            displayDateAndTime(holder , item);

            holder.itemView.findViewById(R.id.container).setOnClickListener(
                    v -> onItemClickListener.onItemClick(item)
            );

        }

        private static void setMagBackground(@NonNull TextView view, Context context , @NotNull EqModel item ){
            GradientDrawable drawable = (GradientDrawable) view.getBackground();

            int magUnit = (int) item.getMag();
            switch(magUnit){
                case 1:
                    drawable.setColor(ContextCompat.getColor( context,R.color.gray_400));
                    break;
                case 2:
                    drawable.setColor(ContextCompat.getColor( context,R.color.deep_range_400));
                    break;
                case 3:
                    drawable.setColor(ContextCompat.getColor( context,R.color.orange_400));
                    break;
                case 4:
                    drawable.setColor(ContextCompat.getColor( context,R.color.deep_range_600));
                    break;
                case 5:
                case 6:
                    drawable.setColor(ContextCompat.getColor( context,R.color.red_600));
                    break;
                default:
                    drawable.setColor(ContextCompat.getColor( context,R.color.red_700));
            }


        }


        private static void displayLocation(ReportViewModel holder  , EqModel item ) {

            String place = item.getPlace();
            if(place.contains(LOCATION_SEPARATOR)){

                String[] places = place.split(LOCATION_SEPARATOR);

                String offsetLocation  = places[0] +" OF" ;

                holder.offsetLocation.setText(offsetLocation);
                holder.primaryLocation.setText(places[1]);

            } else {
                holder.offsetLocation.setText(offset_location);
                if (place.contains("null")){
                    holder.offsetLocation.setText(R.string.no_place);
                    holder.primaryLocation.setText(R.string.no_place_primary);
                    return;
                }
                holder.primaryLocation.setText(place);
            }
        }

        private static void displayDateAndTime(ReportViewModel holder  , EqModel item ){

            Date date = new Date(item.getTime());

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy" , Locale.getDefault());
            holder.date.setText(dateFormatter.format(date));

            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a" , Locale.getDefault());
            holder.time.setText( timeFormatter.format(date));
        }


    }



    private static class DiffCallback extends DiffUtil.ItemCallback<EqModel> {

        @Override
        public boolean areItemsTheSame(@NonNull EqModel oldItem, @NonNull EqModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EqModel oldItem, @NonNull EqModel newItem) {
            return  oldItem.equals(newItem);
        }
    }


}
