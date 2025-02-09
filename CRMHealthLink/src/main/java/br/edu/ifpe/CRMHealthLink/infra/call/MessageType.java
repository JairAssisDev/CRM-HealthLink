package br.edu.ifpe.CRMHealthLink.infra.call;

public enum MessageType {
    DO_OFFER("{\"type\": \"doOffer\", \"sendTo\": \"%s\"}"),DISCONNECT("{\"type\": \"disconnect\",\"msg\": \"%s\"}");
    public final String message;
    MessageType(String message){
        this.message = message;
    }

    public String getJSON(String... params){
        return this.message.formatted((Object[]) params);
    }
}
