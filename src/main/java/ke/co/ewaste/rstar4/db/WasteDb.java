package ke.co.ewaste.rstar4.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by  on 18/04/2017.
 */
@Table(name = "WasteDb")
public class WasteDb extends Model{

    @Column(name = "data")
    String data;

    @Column(name = "createdat")
    String createdat;

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public WasteDb() {
        super();
    }

    public static List<WasteDb> getAll() {
        return new Select()
                .from(WasteDb.class)
                .execute();
    }
}
