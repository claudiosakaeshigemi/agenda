package claudioshigemi.com.agenda2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import claudioshigemi.com.agenda2.R;
import claudioshigemi.com.agenda2.model.Contato;

/**
 * Created by claudio on 18/01/2017.
 */

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewholder> {

    private List<Contato> contatoList;

    public ContatosAdapter(List<Contato> contatoList) {
        this.contatoList = contatoList;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contato_list_row, parent, false);
        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

        Contato contato = contatoList.get(position);
        holder.nome.setText(contato.getNome());
        holder.email.setText(contato.getEmail());
        holder.telefone.setText(contato.getTelefone());

    }

    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView nome, email, telefone;

        public MyViewholder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.list_nome);
            email = (TextView) itemView.findViewById(R.id.list_email);
            telefone = (TextView) itemView.findViewById(R.id.list_telefone);
        }
    }
}


































