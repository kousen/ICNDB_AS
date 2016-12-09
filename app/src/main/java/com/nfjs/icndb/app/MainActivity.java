package com.nfjs.icndb.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends Activity {
    private TextView jokeView;

    private Retrofit retrofit;

    private ShareActionProvider shareActionProvider;

    private AsyncTask<String, Void, String> task;

    public interface ICNDB {
        @GET("/jokes/random")
        Call<IcndbJoke> getJoke(@Query("firstName") String firstName,
                                @Query("lastName") String lastName,
                                @Query("limitTo") String limitTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeView = (TextView) findViewById(R.id.text_view);
        final Button jokeButton = (Button) findViewById(R.id.icndb_button);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new JokeTask().execute(
                        prefs.getString("first", "Hans"),
                        prefs.getString("last", "Dockter"));
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (savedInstanceState != null) {
            jokeView.setText(savedInstanceState.getString("display"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("display", jokeView.getText().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (task != null) task.cancel(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        switch (item.getItemId()) {
            case R.id.action_joke:
                task = new JokeTask().execute(
                        prefs.getString("first", "Hans"),
                        prefs.getString("last", "Dockter"));
                return true;
            case R.id.joke_no_async:
                ICNDB icndb = retrofit.create(ICNDB.class);
                Call<IcndbJoke> icndbJoke = icndb.getJoke(
                        prefs.getString("first", "Tim"),
                        prefs.getString("last", "O'Reilly"), "[nerdy]");

                icndbJoke.enqueue(new Callback<IcndbJoke>() {
                    @Override
                    public void onResponse(Call<IcndbJoke> call, Response<IcndbJoke> response) {
                        if (response.isSuccessful()) {
                            jokeView.setText(response.body().getJoke());
                        } else {
                            jokeView.setText(R.string.not_funny);
                        }
                    }

                    @Override
                    public void onFailure(Call<IcndbJoke> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });

                return true;
            case R.id.preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class JokeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            ICNDB icndb = retrofit.create(ICNDB.class);
            Call<IcndbJoke> icndbJoke = icndb.getJoke(params[0], params[1], "[nerdy]");
            String joke = "";
            try {
                joke = icndbJoke.execute().body().getJoke();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return joke;
        }

        @Override
        protected void onPostExecute(String result) {
            jokeView.setText(result);
            setIntent(result);
        }
    }
}
