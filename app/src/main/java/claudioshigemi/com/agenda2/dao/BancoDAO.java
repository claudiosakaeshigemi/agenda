package claudioshigemi.com.agenda2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by claudio on 18/01/2017.
 */

public class BancoDAO  extends SQLiteOpenHelper{

    private  static final String NOMEBANCO = "agenda";
    private  static final int VERSAOBANCO = 1;
    private  static final String TABLECONTATO = "CREATE TABLE contato (id INTEGER PRIMARY KEY,nome TEXT, email TEXT, telefone TEXT); ";


    public BancoDAO(Context context) {
        super(context, NOMEBANCO, null, VERSAOBANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLECONTATO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contato;");

    }
}













































