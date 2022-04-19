package com.cristianomoraes.libri_retorfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroLivro extends AppCompatActivity {

    /** ATRIBUTOS **/
    RouterInterface routerInterface;
    EditText txtTitulo;
    EditText txtDescricao;
    EditText txtFoto;
    Button btnInserirLivro;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        /** ASSOCIAR OS COMPONENTES GRÁFICOS AOS ATRIBUTOS **/
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnInserirLivro = findViewById(R.id.btnCadastrarLivro);

        /** CONFIGURAÇÃO DO routerInterface **/
        routerInterface = APIUtil.getUsuarioInterface();

        /** TRATAMENTO DE AÇÃO DE CLICK OU TOQUE DE TELA
         * DO BOTÃO INSERIR LIVRO
         * **/
        btnInserirLivro.setOnClickListener(view -> {

        /** CRIA O OBJETO DE LIVRO E RECEBE OS DADOS **/
        Livro livro = new Livro();

        livro.setTitulo(txtTitulo.getText().toString());
        livro.setDescricao(txtDescricao.getText().toString());
        livro.setTblUsuarioCodUsuario(1);

        /** CHAMADA DO MÉTODO DA ROTA DE INSERÇÃO DE LIVRO **/
        addLivro(livro);

        });

    }//FIM DO MÉTODO onCreate

    /**
     * IMPLEMENTAÇÃO DO MÉTODO addLivro DA INTERFACE RouterInface
     **/
    public void addLivro(Livro livro) {

    /** LIGA O MÉTODO addLivro DA CLASSE CadastroLivro
     * COM SUA REPRESENTAÇÃO NA INTERFACE routerface
     * **/
    Call<Livro>call = routerInterface.addLivro(livro);

    /** EXECUÇÃO CHAMADA DA ROTA **/
    call.enqueue(new Callback<Livro>() {
        @Override
        public void onResponse(Call<Livro> call, Response<Livro> response) {
            Toast.makeText(CadastroLivro.this, "Livro inserido com sucesso", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Livro> call, Throwable t) {
            Log.d("ERRO-API", t.getMessage());
        }
    });

    }

}