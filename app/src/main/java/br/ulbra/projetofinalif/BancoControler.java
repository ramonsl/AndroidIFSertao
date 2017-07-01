package br.ulbra.projetofinalif;

/**
 * Created by ramon on 30/06/17.
 *
 * Classe para manipular registros no BD
 *
 *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class BancoControler {
    private SQLiteDatabase db;
    private DbHelper banco;

    public BancoControler(Context context){
        banco = new DbHelper(context);
    }

    public String inserePessoa(Pessoa p){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(DbHelper.NOME, p.getNome());
        valores.put(DbHelper.CPF, p.getCpf());
        valores.put(DbHelper.IDADE, p.getIdade());
        valores.put(DbHelper.EMAIL,p.getEmail());
        valores.put(DbHelper.FONE,p.getFone());



        resultado = db.insert(DbHelper.TABELA, null, valores);
        db.close();
        if(resultado == -1)
            return "Erro ao cadastrar pessoa, talvez o cpf já tenha sido cadastrado";
        else
            return "Pessoa cadastrada com sucesso";
    }


    public Cursor listaPessoa(){
        Cursor cursor;
        String[] campos = {DbHelper.ID, DbHelper.NOME, DbHelper.CPF, DbHelper.IDADE, DbHelper.EMAIL, DbHelper.FONE};
        db = banco.getReadableDatabase();
        cursor = db.query(DbHelper.TABELA, campos, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor consultaPorID(int id){
        Cursor cursor;
        String[] campos = {DbHelper.ID, DbHelper.NOME, DbHelper.CPF, DbHelper.IDADE, DbHelper.EMAIL, DbHelper.FONE};
        String where = DbHelper.ID + "=" +id;
        db = banco.getReadableDatabase();
        cursor = db.query(DbHelper.TABELA,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    //uso para buscar uma pessoa informando o CPF
    public Cursor consultaPorCPF(String cpf){
        Cursor cursor;
        String[] campos = {DbHelper.ID, DbHelper.NOME, DbHelper.CPF, DbHelper.IDADE, DbHelper.EMAIL, DbHelper.FONE};
        String where = DbHelper.CPF + "=" + cpf;
        db = banco.getReadableDatabase();
        cursor = db.query(DbHelper.TABELA,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    /// infelizmentente nao deu tempo de utilizar o ALTERAR
    public void alterarPessoa(int id, String nome, String cpf, String idade, String email, String fone){
        ContentValues valores;
        String where;


        db = banco.getWritableDatabase();
        where = DbHelper.ID + "=" +id;

        valores = new ContentValues();

        valores.put(DbHelper.NOME, nome);
        valores.put(DbHelper.CPF, cpf);
        valores.put(DbHelper.IDADE, idade);
        valores.put(DbHelper.EMAIL,email);
        valores.put(DbHelper.FONE,fone);

        db.update(DbHelper.TABELA,valores,where,null);
        db.close();
    }


    ///metodo que remove a pessoa

    public void deletarPessoa(int id){
        String where = DbHelper.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(DbHelper.TABELA,where,null);
        db.close();
    }

}


