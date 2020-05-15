package com.example.mvpdemo2.feature.movies;

import com.example.mvpdemo2.api.APIService;
import com.example.mvpdemo2.api.RetrofitConfiguration;
import com.example.mvpdemo2.models.GetMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenter implements MoviesContract.Presenter {

    MoviesContract.View view;

    public MoviesPresenter(MoviesContract.View view) {
        this.view = view;
    }

    @Override
    public void getMovies(int page) {
        view.showLoadingIndicator();
        APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMoviesResponse> call = service.getMovies(page);
        call.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                view.hideLoadingIndicator();
                if (response.code() == 200) {
                    view.setDataToRecyclerView(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.showError(t.toString());
            }
        });
    }
}
