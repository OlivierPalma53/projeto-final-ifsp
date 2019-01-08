package com.olivierpalma.projetofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.olivierpalma.projetofinal.model.DBHelper;
import com.olivierpalma.projetofinal.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class CadastrarActivity extends AppCompatActivity {

    //Criando propriedades para recuperar as views
    private EditText edtName;
    private EditText edtCpf;
    private EditText edtAge;
    private EditText edtPhone;
    private EditText edtEmail;

    private TextInputLayout txtLayoutName;
    private TextInputLayout txtLayoutCpf;
    private TextInputLayout txtLayoutAge;
    private TextInputLayout txtLayoutPhone;
    private TextInputLayout txtLayoutEmail;


    private Button btnSave;
    private Button btnList;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        //Recuperando as views pelo id
        edtName = (EditText) findViewById(R.id.edtName);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        txtLayoutName = (TextInputLayout) findViewById(R.id.txtLayoutName);
        txtLayoutCpf = (TextInputLayout) findViewById(R.id.txtLayoutCpf);
        txtLayoutAge = (TextInputLayout) findViewById(R.id.txtLayoutAge);
        txtLayoutPhone = (TextInputLayout) findViewById(R.id.txtLayoutPhone);
        txtLayoutEmail = (TextInputLayout) findViewById(R.id.txtLayoutEmail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnList = (Button) findViewById(R.id.btnList);
        btnBack = (Button) findViewById(R.id.btnBack);


        //Metodo de clique no botao salvar
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Limpando os erros dos EditTexts
                txtLayoutName.setErrorEnabled(false);
                txtLayoutCpf.setErrorEnabled(false);
                txtLayoutAge.setErrorEnabled(false);
                txtLayoutPhone.setErrorEnabled(false);
                txtLayoutEmail.setErrorEnabled(false);

                //Chamando a função para validar os dados de entrada
                if(isValid()){

                    //Instanciando o Objeto de conexão com o banco
                    DBHelper helper = new DBHelper(getApplicationContext());


                    //chamando o metodo para inserir os dados no banco
                    helper.insert(edtName.getText().toString(), edtCpf.getText().toString(), edtAge.getText().toString(), edtPhone.getText().toString(), edtEmail.getText().toString());

                    //limpando os campos de texto
                    edtName.setText("");
                    edtCpf.setText("");
                    edtAge.setText("");
                    edtPhone.setText("");
                    edtEmail.setText("");

                    Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();



                }

            }
        });

        //Metodo de clique no botão listar
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper helper = new DBHelper(CadastrarActivity.this);
                List<Pessoa> pessoas = new ArrayList<Pessoa>();
                pessoas = helper.listAll();

                if(!pessoas.isEmpty()){
                    for (int i = 0; i < pessoas.size(); i++){

                        String nome = pessoas.get(i).getName();
                        String cpf = pessoas.get(i).getCpf();
                        String idade = pessoas.get(i).getAge();
                        String telefone = pessoas.get(i).getPhone();
                        String email = pessoas.get(i).getEmail();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarActivity.this);
                        builder.setTitle("Cadastro");
                        builder.setMessage("Nome: " + nome + "\n CPF: " + cpf + "\n Idade: " + idade + "\n Telefone: " + telefone + "\n Email: " + email);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.create();
                        builder.show();
                    }
                }

            }
        });





        //Metodo de clique para o botao voltar
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });

    }

    public boolean isValid(){
        //Metodo para validar entrada do texto usuario

        if(edtName.getText().toString().isEmpty()){
            txtLayoutName.setErrorEnabled(true);
            txtLayoutName.setError("Digite um nome");
            return false;
        } else if (edtCpf.getText().toString().isEmpty() || edtCpf.getText().toString().length() < 11){
            txtLayoutCpf.setErrorEnabled(true);
            txtLayoutCpf.setError("Digite um CPF Válido");
            return false;
        } else if(edtAge.getText().toString().isEmpty()){
            txtLayoutAge.setErrorEnabled(true);
            txtLayoutAge.setError("Digite a idade");
            return false;
        } else if (edtPhone.getText().toString().isEmpty() || edtPhone.getText().toString().length() < 10){
            txtLayoutPhone.setErrorEnabled(true);
            txtLayoutPhone.setError("Digite um telefone Válido");
            return false;
        } else if(edtEmail.getText().toString().isEmpty() || !edtEmail.getText().toString().contains("@")){
            txtLayoutEmail.setErrorEnabled(true);
            txtLayoutEmail.setError("Digite um email Válido");
            return false;
        } else {
            return true;
        }

    }



}
