package com.prolan.antonioprado.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.prolan.antonioprado.R;
import com.prolan.antonioprado.adapter.ListAdapterDetails;
import com.prolan.antonioprado.app.AppController;
import com.prolan.antonioprado.model.Books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL ="http://de-coding-test.s3.amazonaws.com/books.json";
    private List<Books> booksList = new ArrayList<Books>();
    ListView listView;
    ProgressDialog progressDialog;
    private ListAdapterDetails adapterDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
    }

    public void fetchData(){
        listView = (ListView) findViewById(R.id.listView);
        adapterDetails = new ListAdapterDetails(this,booksList);
        listView.setAdapter(adapterDetails);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest bookRequest = new JsonArrayRequest(URL,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        Log.d("DEBUG", response.toString());
                        hidePDialog();

                        try {
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Books mBooks = new Books();
                                mBooks.setTitle(obj.getString("title"));
                                mBooks.setImageURL(obj.getString("imageURL"));
                                if(!obj.isNull("author"))
                                    mBooks.setAuthor(obj.getString("author"));
                                else
                                    mBooks.setAuthor(" ");
                                booksList.add(mBooks);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it shows updated data in ListView
                        adapterDetails.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUG", "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "ERRO: Unable to get data", Toast.LENGTH_LONG).show();
                hidePDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(bookRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
