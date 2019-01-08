package com.olivierpalma.projetofinal.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DBHelper {

    //Definindo os atributos

    private static final String DATABASE_NAME = "pessoafisica"; //Nome do banco de dados
    private static final int DATABASE_VERSION = 1; //Versão do banco
    private static final String TABLE_NAME = "recebedados"; //Nome da tabela do banco de dados

    private Context context;
    private SQLiteDatabase db;

    //atributos necessários para a criação ao suporte para o banco

    private SQLiteStatement insertStnt;

    //instrução SQL para que possa ser reaproveitada
    private static final String INSERT = "insert into " + TABLE_NAME + " (nome, cpf,idade, telefone, email) VALUES (?,?,?,?,?)";

    //Criando o construtor DBHelper
    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStnt = this.db.compileStatement(INSERT);
    }

    //metodo de inserir
    public long insert(String nome, String cpf, String idade, String telefone, String email) {
        this.insertStnt.bindString(1, nome);
        this.insertStnt.bindString(2, cpf);
        this.insertStnt.bindString(3, idade);
        this.insertStnt.bindString(4, telefone);
        this.insertStnt.bindString(5, email);

        return this.insertStnt.executeInsert();
    }

    //Metodo para apagar todos os registros
    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    //Criando uma Metodo para recuperar as informações do banco


    public List<Pessoa> listAll() {

        List<Pessoa> list = new ArrayList<Pessoa>();

        try {
            Cursor cursor = this.db.query(TABLE_NAME, new String[]{"nome", "cpf", "idade", "telefone", "email"},
                    null, null, null, null, null, null);
            int contaRegistros = cursor.getCount();
            if (contaRegistros != 0) {
                int idNome = cursor.getColumnIndex("nome");
                int idCpf = cursor.getColumnIndex("cpf");
                int idIdade = cursor.getColumnIndex("idade");
                int idTelefone = cursor.getColumnIndex("telefone");
                int idEmail = cursor.getColumnIndex("email");

                cursor.moveToFirst();
                do {
                    Pessoa pessoa = new Pessoa(cursor.getString(idNome), cursor.getString(idCpf), cursor.getString(idIdade), cursor.getString(idTelefone), cursor.getString(idEmail));
                    list.add(pessoa);

                } while (cursor.moveToNext());

                if (cursor == null || !cursor.isClosed()) {
                    cursor.close();
                    return list;
                }
            }
            return list;
        } catch (Exception err) {
            return null;
        }

    }

    //Criando Inner class para Herdar de SQLiteOpenHelper
    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //Impelmentando metodos abstratos
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, idade TEXT, telefone TEXT, email TEXT);";
            db.execSQL(sql);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}