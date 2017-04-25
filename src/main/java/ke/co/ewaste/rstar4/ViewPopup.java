package ke.co.ewaste.rstar4;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import ke.co.ewaste.rstar4.data.Waste;
import ke.co.ewaste.rstar4.db.WasteDb;


/**
 * Created by  on 21/04/2017.
 */

public class ViewPopup  extends DialogFragment {

    Button cancel,call,text;
    TextView name, location,price,wastetype;
    ImageView photo;

    public static ViewPopup newInstance(int id) {
        ViewPopup frag = new ViewPopup();
        Bundle args = new Bundle();
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int id = getArguments().getInt("id");


        List<WasteDb> all3 = WasteDb.getAll();
        ArrayList<Waste> supplies = fetchAll(all3.get(0).getData());

        final Waste supply = findById(id,supplies);
        String url="/files/Waste/photo";
        String baseurl = getContext().getResources().getString(R.string.baseurl);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialog= inflater.inflate(R.layout.activity_popup, null);

        name=(TextView) dialog.findViewById(R.id.name);
        location=(TextView) dialog.findViewById(R.id.location);
        price=(TextView) dialog.findViewById(R.id.price);
        wastetype=(TextView) dialog.findViewById(R.id.wastetype);

    photo = (ImageView) dialog.findViewById(R.id.photo);
        Ion.with(getContext()).load(baseurl+url+"/"+supply.getPhoto()).intoImageView(photo);
        name.setText(supply.getName());
        location.setText(supply.getLocation());

        wastetype.setText(supply.getWastetype().toUpperCase());
        price.setText("From KSH. "+supply.getPrice());


        cancel=(Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPopup.this.getDialog().cancel();

            }
        });
        call=(Button) dialog.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + supply.getPhone()));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }

                Toast.makeText(getContext(), "Calling....", Toast.LENGTH_SHORT).show();
                ViewPopup.this.getDialog().cancel();

            }
        });
        text=(Button) dialog.findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+supply.getPhone()));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", "Hello ,I would like to inquire about the "+supply.getWastetype()+"  posted on Rstar4 app.");
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }


                Toast.makeText(getContext(), "Creating Text Message", Toast.LENGTH_SHORT).show();
                ViewPopup.this.getDialog().cancel();

            }
        });



        builder.setView(dialog);

        return builder.create();
    }
    public ArrayList<Waste> fetchAll(String dataset){
        JsonElement jelements = new JsonParser().parse(dataset);
        ArrayList<Waste> rowItems=new ArrayList<>();
        JsonArray j=jelements.getAsJsonArray();
        for (int i=0;i<j.size();i++) {
            Waste dta = new Gson().fromJson(j.get(i).getAsJsonObject(),Waste.class);
            rowItems.add(dta);
        }
        return rowItems;
    }

    public Waste findById(int id, ArrayList<Waste> list){
      Waste w = null;
        for (Waste dealer:list ){
            if (dealer.getId()==id) {
                w=dealer;
            }

        }
        return  w;
    }

}