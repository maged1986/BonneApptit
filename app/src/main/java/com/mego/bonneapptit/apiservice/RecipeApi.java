package com.mego.bonneapptit.apiservice;

import com.mego.bonneapptit.models.responses.RecipeResponse;
import com.mego.bonneapptit.models.responses.RecipeListResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.Single;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.mego.bonneapptit.utils.Constants.BASE_URL;

public interface RecipeApi {
    // this is api interface

    @GET("/api/search")
    io.reactivex.rxjava3.core.Observable<RecipeListResponse> searchRecipes(
            @Query("q") String query,
            @Query("page") int page
    );
    @GET("/api/search")
    io.reactivex.rxjava3.core.Observable<RecipeListResponse> getAllRecipes(

    );

    // GET RECIPE REQUEST
    @GET("/api/get")
    Observable<RecipeResponse> getRecipe(
            @Query("rId") String recipe_id
    );
}
