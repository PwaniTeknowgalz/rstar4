package ke.co.ewaste.rstar4.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;


import ke.co.ewaste.rstar4.R;
import ke.co.ewaste.rstar4.ViewPopup;
import ke.co.ewaste.rstar4.data.Waste;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by  on 03-08-2016.
 */
@LayoutId(R.layout.listitem)

public class MainListHolder extends ItemViewHolder<Waste> {
    String url="/files/Waste/photo/";
    String baseurl = getContext().getResources().getString(R.string.baseurl);
    @ViewId(R.id.name)
    TextView name;

    @ViewId(R.id.location)
    TextView location;

    @ViewId(R.id.price)
    TextView price;

    @ViewId(R.id.wastetype)
    TextView wastetype;

    @ViewId(R.id.photo)
    ImageView photo;
    @ViewId(R.id.main)
    LinearLayout main;

    public MainListHolder (View view) {
        super(view);
    }
    @Override
    public void onSetValues(final Waste s, final PositionInfo positionInfo) {
        name.setText(s.getName());
        price.setText("From KSH. "+s.getPrice());
        location.setText(s.getLocation());
        wastetype.setText(s.getWastetype());
        Ion.with(getContext()).load(baseurl+url+"/"+s.getPhoto()).intoImageView(photo);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPopup v = ViewPopup.newInstance(s.getId());
                v.show(((AppCompatActivity)getContext()).getSupportFragmentManager(),"ViewPopup");
            }
        });

    };





}
