package ke.co.muniform.muniform.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



/**
 * Created by  on 03-08-2016.
 */
public class Utils {

    public Utils(){}



   /*
    public static void addtocart(ProductsSupermarkets products, int infoid,int qty){
        String x=null;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            x=dt.format(new Date());

        ShoppingList sl = new ShoppingList();
        sl.setPrice(products.getInfo().get(infoid).getPrice());
        sl.setSupermarketname(products.getInfo().get(infoid).getSupermarket());
        sl.setProductname(products.getName());
        sl.setQuantity(qty);
       String data = new Gson().toJson(sl);
        List<ShoppingListDb> db = searchList();
        if(db.isEmpty()){
            JsonElement jelements = new JsonParser().parse(data);
            JsonArray j =new JsonArray();
            j.add(jelements);
            String end=j.toString();
            ShoppingListDb db2= new ShoppingListDb();
            db2.setShoppinglist(end);
            db2.setStatus(true);
            db2.setCreatedat(x);
            db2.save();
        }else {
            ShoppingListDb db2= db.get(0);
            ArrayList<ShoppingList> shopping = fetchAll(db2.getShoppinglist());
            shopping.add(sl);
            String mdata = new Gson().toJson(shopping);
            db2.setShoppinglist(mdata);
            db2.save();

        }


    }
    public static ArrayList<ShoppingList> fetchAll(String dataset){
        JsonElement jelements = new JsonParser().parse(dataset);
        ArrayList<ShoppingList> rowItems=new ArrayList<>();
        JsonArray j=jelements.getAsJsonArray();
        for (int i=0;i<j.size();i++) {
            ShoppingList dta = new Gson().fromJson(j.get(i).getAsJsonObject(),ShoppingList.class);
            rowItems.add(dta);
        }
        return rowItems;
    }*/

}
