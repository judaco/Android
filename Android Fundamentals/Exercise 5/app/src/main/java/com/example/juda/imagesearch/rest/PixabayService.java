package com.example.juda.imagesearch.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Juda on 02/12/2017.
 */

public interface PixabayService {

    /*
    Base search image URL
     */
    String BASE_URL = "https://pixabay.com/api/";

    /*
    Search image params
     */
    String IMAGE_TYPE = "image_type";
    String QUERY = "q";

    /*
    API Key
     */
    String apiKey = "7251606-c88610a8631e1f3622db24cc4";
    String keyQuery = "?key=" + apiKey;

    /*
    Image type params
     */
    String IMAGE_TYPE_DEFAULT = "all";
    String IMAGE_TYPE_PHOTO = "photo";
    String IMAGE_TYPE_ILLUSTRATION = "illustration";
    String IMAGE_TYPE_VECTOR = "vector";

    @GET(keyQuery)
    Call<ImageSearchResult>searchImage (@Query(QUERY) String qValue, @Query(IMAGE_TYPE) String imageType);

}
