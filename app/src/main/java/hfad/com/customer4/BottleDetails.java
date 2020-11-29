package hfad.com.customer4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class BottleDetails extends AppCompatActivity {
JSONObject postData;
TextView manuBox;
TextView nameBox;
TextView mfdBox;;
TextView laBox;
TextView loc1;
TextView date1;
    TextView loc2;
    TextView date2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottle_details);

        manuBox=(TextView)findViewById(R.id.manuBox);
        nameBox=(TextView)findViewById(R.id.nameBox);
        mfdBox=(TextView)findViewById(R.id.mfdBox);
        laBox=(TextView)findViewById(R.id.laBox);

        loc1=(TextView)findViewById(R.id.loc1);
        loc2=(TextView)findViewById(R.id.loc2);
        date1=(TextView)findViewById(R.id.date1);
        date2=(TextView)findViewById(R.id.date2);


        try{ postData = new JSONObject(getIntent().getStringExtra("JSON_OBJECT"));

            initiateApiCAll();}
        catch(JSONException e)
        {

            e.printStackTrace();

        }


    }
    public void click1(View view)
    {
        initiateApiCAll();;




    }

    public void initiateApiCAll()
    {
        JSONObject postData1 = new JSONObject();
        try {
            int batchId=postData.getInt("batchId");
            int bottleId=postData.getInt("bottleId");
            postData1.put("batchId", batchId);
            postData1.put("bottleId", bottleId);
            volleyPost(postData1); ///calls the volley method to make request. Email, password values are passed to volleyPost() method

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void volleyPost(JSONObject postData){
        ConnectionString obj=new ConnectionString();

        String postUrl = obj.connectioncode+"Customer/getBottle";
        RequestQueue requestQueue = Volley.newRequestQueue(this);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("MyActivity", "MyClass.getView() — get item number "+response);
                setTextViews(response);
               // handleResponse(response);// After the trespons e is recieved handleResponse() method will be called to read json response and take next steps.
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void setTextViews(JSONObject postData)
    {
        try{
            String productname=postData.getString("productname");
            nameBox.setText(productname);
            String creator=postData.getString("creator");
            manuBox.setText(creator);
            String createDate=postData.getString("createDate");
            mfdBox.setText(createDate);
            String lastloc=postData.getString("lastloc");
           laBox.setText(lastloc);
           JSONObject jobject=postData.getJSONObject("history");
            Log.i("2222222222", "tyyy() — get item number "+jobject);
            Iterator<String> keys = jobject.keys();
            int count=0;
            int [] a=new int[100];
            String[] dates=new String[2];
            String[] locs=new String[2];

            for(int i = 0; i<jobject.names().length(); i++){
                count=count+1;
                dates[i]=jobject.names().getString(i);
                locs[i]=jobject.getString(dates[i]);
                Log.v("TAG", "key = " + jobject.names().getString(i) + " value = " + jobject.get(jobject.names().getString(i)));
            }
            if(count==2)
            {
                date2.setText(dates[0]);
                loc2.setText(locs[0]);
                date1.setText(dates[1]);
                loc1.setText(locs[1]);


            }
            else
            {
                date1.setText(dates[0]);
                loc1.setText(locs[0]);


            }


        }
        catch(JSONException e)
        {

        }

    }
}