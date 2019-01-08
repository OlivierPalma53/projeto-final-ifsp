package com.olivierpalma.projetofinal.model;

/**
 * Created by olivierpalma on 09/12/2017.
 * classe PessoaController
 */

public class Pessoa {

    private String name;
    private String cpf;
    private String age;
    private String phone;
    private String email;

    public Pessoa(String name, String cpf, String age, String phone, String email) {
        this.name = name;
        this.cpf = cpf;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Chama o metodo para cadastrar pessoa no banco de dados
    /* public Boolean save(){
        DBHelper pessoaDAO = new DBHelper();
        Boolean result = pessoaDAO.saveDB(this);
        return result;
    }
    */


}
