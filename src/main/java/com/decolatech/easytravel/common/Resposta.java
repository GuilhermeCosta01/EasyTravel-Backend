package com.decolatech.easytravel.common;

public class Resposta {

    private int codigo;
    private String mensagem;

    public Resposta(int codigo, String mensagem) {
        this.setCodigo(codigo);
        this.setMensagem(mensagem);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
