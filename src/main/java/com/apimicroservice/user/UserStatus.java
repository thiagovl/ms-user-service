package com.apimicroservice.user;

public enum UserStatus {

	ACTIVE(0),
    SUSPENDED(1),
    BLOCKED(2);

    private Integer code;

    private UserStatus(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    // Verifica se o código existe
    public static UserStatus valueOf(Integer code){
        for (UserStatus value: UserStatus.values()){
            if (value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Código de status invalido!!!");
    }
}