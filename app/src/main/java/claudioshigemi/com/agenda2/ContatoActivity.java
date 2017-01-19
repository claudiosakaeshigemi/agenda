package claudioshigemi.com.agenda2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Text;

import claudioshigemi.com.agenda2.dao.BancoDAO;
import claudioshigemi.com.agenda2.dao.ContatoDAO;
import claudioshigemi.com.agenda2.model.Contato;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by claudio on 19/01/2017.
 */

public class ContatoActivity extends AppCompatActivity {
    private EditText contato_edtNome;
    private EditText contato_edtEmail;
    private EditText contato_edtTelefone;
    private Button contato_btnSalvar;
    private LinearLayout activity_contato;
    private ProgressBar contato_progressBar;
    private String nome, email, telefone;
    private Contato contato;
    private ContatoDAO dao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        findView();

        Intent intent = getIntent();
        contato = (Contato) intent.getSerializableExtra("contato");

        if (contato != null) {
            contato_edtNome.setText(contato.getNome());
            contato_edtEmail.setText(contato.getEmail());
            contato_edtTelefone.setText(contato.getEmail());
        } else {
            contato = new Contato();
        }


        contato_btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarValores();

                habilitarProgress(View.VISIBLE, false);

                if (TextUtils.isEmpty(nome)) {
                    Snackbar.make(activity_contato, R.string.digite_seu_nome, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(activity_contato, R.string.digite_seu_email, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(telefone)) {
                    Snackbar.make(activity_contato, R.string.digite_seu_telefone, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                contato.setNome(nome);
                contato.setEmail(email);
                contato.setTelefone(telefone);

                dao = new ContatoDAO(getApplicationContext());
                if (contato.getId() > 0) {
                    editar();
                } else {
                    inserir();
                }

            }
        });
    }

    private void editar() {
        dao.alterar(contato);
        Toast.makeText(getApplicationContext(),R.string.salvo_com_sucesso, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    private void inserir() {
        if (dao.insere(contato) > 0) {
            Toast.makeText(getApplicationContext(), R.string.salvo_com_sucesso, Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            Snackbar.make(activity_contato, R.string.nao_foi_possivel_salvar, Snackbar.LENGTH_LONG).show();
            habilitarProgress(View.GONE, true);
        }
    }

    private void habilitarProgress(int visible, boolean b) {

        contato_progressBar.setVisibility(visible);
        contato_btnSalvar.setClickable(b);
    }

    private void pegarValores() {
        nome = contato_edtNome.getText().toString();
        email = contato_edtEmail.getText().toString();
        telefone = contato_edtTelefone.getText().toString();
    }

    private void findView() {
        contato_edtNome = (EditText) findViewById(R.id.contato_edtNome);
        contato_edtTelefone = (EditText) findViewById(R.id.contato_edtTelefone);
        contato_edtEmail = (EditText) findViewById(R.id.contato_edtEmail);
        contato_btnSalvar = (Button) findViewById(R.id.contato_btnSalvar);
        activity_contato = (LinearLayout) findViewById(R.id.activity_contato);
        contato_progressBar = (ProgressBar) findViewById(R.id.contato_progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (contato.getId() > 0){


            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_contato,menu);
            return  true;
        }
        return  false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId() ){
            case R.id.contato_ligar:
                Intent chamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + contato.getTelefone() )) ;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED){
                    startActivity(chamada);
                }
                return true;

            case R.id.contato_apagar:
//                Toast.makeText(getApplicationContext(),R.string.apagar, Toast.LENGTH_SHORT ).show();

                new AlertDialog.Builder(this)
                        .setMessage(R.string.deseja_apagar)
                        .setCancelable(false)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dao = new ContatoDAO(getApplicationContext());
                                dao.deletar(contato);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        })
                        .setNegativeButton(R.string.nao, null).show();

                return  true;

            case  R.id.contato_email:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{contato.getEmail() } );
                email.setType("mensage/rfc822");
                startActivity(Intent.createChooser(email, R.string.enviar_email + ""));
                return  true;

            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}





















