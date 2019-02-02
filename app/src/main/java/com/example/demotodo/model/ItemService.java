package com.example.demotodo.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemService implements Callback<List<ItemVO>> {
    public static final String BASE_URL = "http://192.168.1.119:8080";
    ICallBackInterface objectWithOnSuccess; //polymorphism
    private ItemsRepository itemsRepository;

    public ItemService(ICallBackInterface object) {
        objectWithOnSuccess = object;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        itemsRepository = retrofit.create(ItemsRepository.class); //sukuriam retrofito klienta
    }


    public void get() {
        Call<List<ItemVO>> call = itemsRepository.getAll();
        call.enqueue(this);
    }

    public void post(ItemVO itemVO) {
        Call<ItemVO> call = itemsRepository.post(itemVO);
        call.enqueue(new Callback<ItemVO>() {
            @Override
            public void onResponse(Call<ItemVO> call, Response<ItemVO> response) {
                get();
            }

            @Override
            public void onFailure(Call<ItemVO> call, Throwable t) {
            }
        });
    }

    public void put(ItemVO itemVO) {
        Call<ItemVO> call = itemsRepository.put(itemVO);
        call.enqueue(new Callback<ItemVO>() {
            @Override
            public void onResponse(Call<ItemVO> call, Response<ItemVO> response) {
                get();
            }

            @Override
            public void onFailure(Call<ItemVO> call, Throwable t) {
            }
        });
    }

    public void delete(ItemVO itemVO) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                get();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                get();
            }
        };

        Call<ResponseBody> call = itemsRepository.delete(itemVO.id);
        call.enqueue(callback);
    }


    @Override
    public void onResponse(Call<List<ItemVO>> call, Response<List<ItemVO>> response) {
        this.objectWithOnSuccess.onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<List<ItemVO>> call, Throwable t) {

    }
}
