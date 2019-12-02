package com.hilfritz.mvp.ui.psi.views;

import android.app.Activity;
import android.graphics.PorterDuff;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hilfritz.mvp.R;

/**
 * @author Hilfritz Camallere on 22/4/18
 */
public class MapInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public MapInfoWindow(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.map_info_window, null);

        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        titleTextView.setText(marker.getTitle());
        descriptionTextView.setText(marker.getSnippet());


        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        int porterDuffColor = ContextCompat.getColor(context, R.color.light_blue_600);
        imageView.getDrawable().setColorFilter(porterDuffColor, PorterDuff.Mode.SRC_ATOP);

        titleTextView.setTextColor(porterDuffColor);
        descriptionTextView.setTextColor(porterDuffColor);

        return view;
    }
}
