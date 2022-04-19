package com.cristianomoraes.libri_retorfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cristianomoraes.libri_retorfit.model.Item;
import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedLivro extends AppCompatActivity {

    /** DECLAREÇÃO DOS ATRIBUTOS **/
    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        /** CONECTAO O APLICATIVO COM A API **/
        routerInterface = APIUtil.getUsuarioInterface();

        /** EXECUTA A CHAMADA PARA A ROTA DE LISTAGEM DE LIVROS **/
        Call<List<Livro>> call = routerInterface.getLivros();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                if (response.isSuccessful()){
                    List<Item> itens = new ArrayList<>();

                    /** RECEBER OS DADOS DA API **/
                    List<Livro> list = new ArrayList<Livro>();
                    list = response.body();

                    for (int i = 0; i < list.size(); i++){
                        itens.add(new Item(0, list.get(i)));
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(new LivroAdapter(itens));
                }

            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {

            }
        });

    }//FIM DO MÉTODO ONCREAT

    /** CLASSE DE ADAPTER DA RECYCLERVIEW **/
    private class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Item> itens;

        public LivroAdapter(List<Item>itens){
            this.itens = itens;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LivroAdapter.LivroViewHolder(
                    LayoutInflater.from(
                            parent.getContext()).inflate(
                            R.layout.item_container_livro,
                            parent,
                            false
            ));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            /** DADOS DE LIVRO **/
            if (getItemViewType(position) == 0){

                Livro livro = (Livro) itens.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);
            }

            /** DADOS DE HQ **/
//            if (getItemViewType(position) == 1){}
           /** DADOS DE MANGA **/
//            if (getItemViewType(position) == 2){}

        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        public int getItemViewType(int position){
            return itens.get(position).getType();
        }

        /** CLASSE DE VIEWHOLDER DA RECYCLERVIEW **/
        class LivroViewHolder extends RecyclerView.ViewHolder{

            /** ATRIBUTOS DA CLASSE LIVROHOLDER **/
            private TextView txtTitulo, txtDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                txtTitulo = itemView.findViewById(R.id.txtTitulo);
                txtDescricao = itemView.findViewById(R.id.txtDescricao);

                /** AÇÃO DE CLIQUE PARA EDITAR LIVRO E EXECUTAR LIVRO **/

            }//FIM DO CONSTRUTOR DA CLASSE LIVROVIEWHOLDER

            /** MÉTODO QUE CARREGA OS VALORES NOS ELEMENTOS DE TEXTVIEW
                -txtTitulo
                - txtDescricao
             * **/

            public void setLivroData(Livro livro){

                txtTitulo.setText(livro.getTitulo());
                txtDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();

            }

        }//FIM DA CLASSE LIVROVIEWHOLDER

    }

}//FIM DA CLASSE FEEDLIVRO