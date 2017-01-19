package claudioshigemi.com.agenda2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import claudioshigemi.com.agenda2.model.Contato;

/**
 * Created by claudio on 18/01/2017.
 */

public class ContatoDAO {

    Context context;
    BancoDAO dao;
    private  static final String BANCONOME = "Contato";

    public ContatoDAO(Context context) {
        this.context = context;
    }

    public  long  insere(Contato contato) {
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosDoContato(contato);
        long inserir = db.insert(BANCONOME, null, dados);
        db.close();
        Log.i(BANCONOME, inserir + " ");

        return inserir;

    }
    public List<Contato> buscaContatos() {
        String sql = "SELECT * FROM " + BANCONOME;
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        List<Contato> contatos = new ArrayList<Contato>();

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));

            contatos.add(contato);
        }
        cursor.close();
        return  contatos;
    }

    public void alterar(Contato contato){
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosDoContato(contato);
        String[] params = {String.valueOf(contato.getId() )};
        db.update("Contato", dados, "id=? ", params);
    }


    public  void  deletar(Contato contato){
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = new String[]{ String.valueOf(contato.getId())};
        db.delete(BANCONOME, whereClause, whereArgs);
        db.close();
    }


    private ContentValues pegaDadosDoContato(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome" , contato.getNome() );
        dados.put("email" , contato.getEmail() );
        dados.put("telefone", contato.getTelefone() );

        return dados;
    }



}














































