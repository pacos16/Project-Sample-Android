package com.pacosignes.projecte.Api;
public class APIUtils {
    //Evitamos que se puedan crear objetos ya que queremos que la clase sea estática
    private APIUtils() {
    }

    public static final String BASE_URL = "http://192.168.1.35:8080";


    public static APIService getAPIService() {
        return APIRestClient.getInstance(BASE_URL).getRetrofit().create(APIService.class);
    }
}
