package gerrix.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import gerrix.retrofit.models.Item;
import gerrix.retrofit.models.SOAnswersResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SOService mService;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getSOService();
        loadAnswers();
    }

    public void loadAnswers() {

        /*
         enqueue() asynchronously sends the request and notifies your app
         with a callback when a response comes back.
         Since this request is asynchronous,
         Retrofit handles it on a background thread so that
         the main UI thread isn't blocked or interfered with.
         */

        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    List items = response.body().getItems();
                    for (int i = 0; i < items.size(); i++) {
                        Item item = (Item) items.get(i);
                        Log.w(TAG, "onResponse: " + item.getOwner().getDisplayName() );
                    }
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {

            }
        });
    }
}
