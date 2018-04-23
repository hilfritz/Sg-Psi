package com.hilfritz.mvp.ui.psi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.psi.PsiRestInterface;
import com.hilfritz.mvp.api.psi.pojo.Item;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.ui.dialog.SimpleDialog;
import com.hilfritz.mvp.ui.loading.FullscreenLoadingDialog;
import com.hilfritz.mvp.ui.psi.views.MapInfoWindow;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PsiMapActivityFragment extends Fragment implements PsiMapContract.View {
    private static final String TAG = "PsiMapActivityFragment";
    @Inject
    PsiMapContract.Presenter presenter;
    @Inject
    PsiRestInterface api;
    @Inject
    Gson gson;

    GoogleMap googleMap;

    PsiMapContract.Model model;

    public PsiMapActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INITIALIZE INJECTION
        ((MyApplication)getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psi_map, container, false);
        ButterKnife.bind(this, view);
        model = new PsiMapContract.Model() {
            @Override
            public Observable<PsiPojo> getAllPsi() {
                return api.getAllPsi();
            }
        };
        presenter.init(this, model, Schedulers.io(), AndroidSchedulers.mainThread());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((PsiMapActivity)getActivity()).getMapFragment().getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                presenter.populate();
            }
        });

    }

    @Override
    public void showLoading() {
        FullscreenLoadingDialog.showLoading(getFragmentManager(), getString(R.string.label_loading), R.drawable.ic_loading_svg, false);
    }

    @Override
    public void hideLoading() {
        FullscreenLoadingDialog.hideLoading();
    }

    @Override
    public void showDialogWithMessage(String str) {
        SimpleDialog simpleDialog = SimpleDialog.newInstance(str, 0, true, false);
        simpleDialog.show(getFragmentManager(), SimpleDialog.TAG);
    }

    @Override
    public void showMapWithData(PsiPojo psiPojo) {

        googleMap.clear();

        int size = psiPojo.getRegionMetadata().size();
        List<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();

        //Set Custom InfoWindow Adapter
        MapInfoWindow adapter = new MapInfoWindow(getActivity());
        googleMap.setInfoWindowAdapter(adapter);

        List<Item> items = psiPojo.getItems();

        for (int x=0; x<size;x++){
            Double latitude = psiPojo.getRegionMetadata().get(x).getLabelLocation().getLatitude();
            Double longitude = psiPojo.getRegionMetadata().get(x).getLabelLocation().getLongitude();
            String name = psiPojo.getRegionMetadata().get(x).getName();
            String snippet = psiPojo.getRegionMetadata().get(x).getMapSnippet();

            LatLng ltlng = new LatLng(
                    latitude,
                    longitude
            );

            final BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.location_place, name, R.color.text_color));
            MarkerOptions markerOptions = new MarkerOptions().position(ltlng);
            markerOptions.title(name);
            markerOptions.snippet(snippet);

            markerOptions.icon(bitmapDescriptor);

            if (latitude!=0 && longitude!=0){
                markerOptionsList.add(markerOptions);

            }
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markerOptionsList) {
            builder.include(marker.getPosition());

        }
        LatLngBounds bounds = builder.build();

        //int padding = 0; // offset from edges of the map in pixels
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.15); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        for (MarkerOptions mo :
                markerOptionsList) {
            googleMap.addMarker(mo);
        }
        googleMap.animateCamera(cu);

    }

    @Override
    public String getStringFromStringResId(int stringResID) {
        return getString(stringResID);
    }

    @OnClick(R.id.populateButton)
    public void onPopulateButtonClick(View view){
        presenter.populate();
    }

    public void log(String str){
        Log.d(TAG, str);
    }

    /**
     *
     * @param resId int drawable resource id
     * @param title String title
     * @param titleColor int color drawable resource id
     * @return
     */
    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId, String title, int titleColor) {
        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker_custom, null);

        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.image);
        markerImageView.setImageResource(resId);

        TextView titleTxtView = (TextView) customMarkerView.findViewById(R.id.textView);
        titleTxtView.setText(title);
        titleTxtView.setTextColor(getResources().getColor(titleColor));

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}
