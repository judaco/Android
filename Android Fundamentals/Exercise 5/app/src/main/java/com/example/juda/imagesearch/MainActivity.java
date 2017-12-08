package com.example.juda.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juda.imagesearch.rest.Hit;
import com.example.juda.imagesearch.rest.HitsAdapter;
import com.example.juda.imagesearch.rest.ImageSearchResult;
import com.example.juda.imagesearch.rest.PixabayService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    /*
    My views
     */
    private EditText mSearchEditText;
    private TextView mDescription;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    /*
    My Retrofit and Service source
     */
    private Retrofit retrofit;
    private PixabayService pixabayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = findViewById(R.id.searchET);
        mDescription = findViewById(R.id.description);
        mRecyclerView = findViewById(R.id.hits_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /*
        Initialization of Retrofit, and using of GsonConverterFactory in order to generate an impl.
        of our Interface which uses Gson for its deserialization.
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(PixabayService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pixabayService = retrofit.create(PixabayService.class);
    }

    public void searchImageBtn(View view) {

        mDescription.setText(R.string.start_searching);

        /*
        Let's create the call
         */
        Call<ImageSearchResult> imageSearchResultCall = pixabayService
                .searchImage(mSearchEditText.getText().toString(), PixabayService.IMAGE_TYPE_DEFAULT);

        /*
        Let's make the call
         */
        imageSearchResultCall.enqueue(new Callback<ImageSearchResult>() {
            @Override
            public void onResponse(Call<ImageSearchResult> call, Response<ImageSearchResult> response) {

                //Check if the call succeed or not, if it do, then update the UI
                if (response.code() == 200) {
                    mDescription.setText(String.format(getString(R.string.hits_found)
                    , response.body().getTotalHits(), mSearchEditText.getText().toString()));
                    ArrayList<Hit> hits = (ArrayList<Hit>)response.body().getHits();
                    HitsAdapter adapter = new HitsAdapter(hits);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    mDescription.setText(String.format(getString(R.string.error), response.code()));
                }
            }

            @Override
            public void onFailure(Call<ImageSearchResult> call, Throwable t) {

                //The HTTP call has failed
                mDescription.setText(R.string.wrong);
            }
        });
    }
}
