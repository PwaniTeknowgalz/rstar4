package ke.co.ewaste.rstar4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import ke.co.ewaste.rstar4.data.Waste;
import ke.co.ewaste.rstar4.db.WasteDb;
import ke.co.ewaste.rstar4.utils.MainListHandler;
import ke.co.ewaste.rstar4.utils.MainListHolder;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;


public class MainActivity extends BaseActivity{
    String url;
    RecyclerView list ;
    RelativeLayout error;
    TextView errortxt,time;
    private Menu menu;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;



    RecyclerView.LayoutManager m=null;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return "Rstar4";
    }

    @Override
    protected int getToolbarResourceId() {
        return R.id.holder;
    }

    @Override
    protected void activityOutput() {

        url=getResources().getString(R.string.baseurl)+getResources().getString(R.string.api);
        list=(RecyclerView)findViewById(R.id.list);
        error=(RelativeLayout) findViewById(R.id.error);
        errortxt=(TextView) findViewById(R.id.errortext);
        m= new LinearLayoutManager(getApplicationContext());

        time=(TextView) findViewById(R.id.time);
        time.setCompoundDrawables(new IconicsDrawable(getApplicationContext()).icon(Ionicons.Icon.ion_clock).color(Color.BLACK).sizeDp(16), null, null, null);

        if(WasteDb.getAll().isEmpty()){
            refresh();
        }else{
            refresh();

        }

    }
    public void refresh(){
        final Loader load=new Loader();
        load.show(getSupportFragmentManager(), "Loader");
        fetchData(new MainListHandler() {
            @Override
            public void onResponse(String dowload, boolean status, String info) {
                load.dismiss();
                if (status) {

                    List<WasteDb> all = WasteDb.getAll();
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                    if (all.isEmpty()) {
                        WasteDb datas = new WasteDb();
                        datas.setData(dowload);
                        datas.setCreatedat(dt.format(currentDate()));
                        datas.save();
                    } else {
                        WasteDb db = all.get(0);
                        db.setData(dowload);
                        db.setCreatedat(dt.format(currentDate()));
                        db.save();
                    }
                    List<WasteDb> all3 = WasteDb.getAll();
                    ArrayList<Waste> supply = fetchAll(all3.get(0).getData());
                    Date x=null;
                    try {
                        x=dt.parse(all3.get(0).getCreatedat());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    time.setText("Last updated : "+getTimeAgo(x.getTime(),getApplicationContext()));
                    EasyRecyclerAdapter e = new EasyRecyclerAdapter(MainActivity.this, MainListHolder.class, supply);
                    list.setLayoutManager(m);
                    //list.setItemAnimator(new SlideInRightAnimator());
                    list.setAdapter(e);

                } else {
                    list.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    errortxt.setText("No data has been downloaded! Please Refresh...");
                }

            }
        });
    }
    public ArrayList<Waste> fetchAll(String dataset){
        JsonElement jelements = new JsonParser().parse(dataset);
        ArrayList<Waste> rowItems=new ArrayList<>();
        JsonArray j=jelements.getAsJsonArray();
        for (int i=0;i<j.size();i++) {
            Waste dta = new Gson().fromJson(j.get(i).getAsJsonObject(), Waste.class);
            rowItems.add(dta);
        }
        return rowItems;
    }
    public void fetchData(final MainListHandler listener) {

        Ion.with(getApplicationContext())
                .load(url + "fetchwaste.json")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            JsonObject json = result.getAsJsonObject();
                            if (!result.get("success").getAsBoolean()) {
                                listener.onResponse(null, result.get("success").getAsBoolean(), result.get("message").getAsString());
                            } else {

                                listener.onResponse(result.get("data").toString(), result.get("success").getAsBoolean(), result.get("message").toString());
                            }


                        } else {
                            listener.onResponse(null, false, "Error: "+e.getMessage());
                        }


                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(0).setIcon(new IconicsDrawable(this).icon(Ionicons.Icon.ion_refresh).color(Color.WHITE).sizeDp(24));
        menu.getItem(1).setIcon(new IconicsDrawable(this).icon(Ionicons.Icon.ion_android_add).color(Color.WHITE).sizeDp(24));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            refresh();
            return true;
        }else if (id == R.id.action_add) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@ewaste.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Request to post my ewaste");
            if (intent.resolveActivity(this.getPackageManager()) != null) {
                startActivity(intent);
            }

            Toast.makeText(this, "Creating Email Message", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();;
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

}
