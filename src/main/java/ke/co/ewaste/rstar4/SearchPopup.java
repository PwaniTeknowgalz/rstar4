package ke.co.ewaste.rstar4;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

/**
 * Created by  on 4/19/2016.
 */
public class SearchPopup extends DialogFragment {
    TextView title;
    Button search,cancel;
   EditText searchterm;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialog= inflater.inflate(R.layout.search_popup, null);

        title =(TextView) dialog.findViewById(R.id.searchtitle);
        searchterm=(EditText) dialog.findViewById(R.id.searchterm);
        search=(Button) dialog.findViewById(R.id.buttonsearch);
        cancel=(Button) dialog.findViewById(R.id.buttoncancel);
        search.setCompoundDrawables(new IconicsDrawable(getActivity().getApplicationContext()).icon(Ionicons.Icon.ion_search).color(getResources().getColor(R.color.white)).sizeDp(14), null, null, null);
        cancel.setCompoundDrawables(new IconicsDrawable(getActivity().getApplicationContext()).icon(Ionicons.Icon.ion_android_cancel).color(getResources().getColor(R.color.white)).sizeDp(14), null, null, null);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPopup.this.getDialog().cancel();

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                y.putExtra("searchterm", searchterm.getText().toString());
                startActivity(y);
                SearchPopup.this.getDialog().cancel();

            }
        });



        builder.setView(dialog);

        return builder.create();
    }

}