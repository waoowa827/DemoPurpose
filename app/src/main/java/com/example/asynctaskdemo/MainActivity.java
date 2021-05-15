package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import dto.My;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="MainActivity";
    MyBroadcastReceiver myBroadcastReceiver=new MyBroadcastReceiver();
    private Button button,service_btn,button1,save;
    private EditText time,count,empName;
    private TextView finalResult,text;
    ListView listView;
    HashMap<Integer,String> map=new HashMap<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Employee");;
   /* int emp_id;
    String empName="";
    static String ceoName="";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase.child("Name").setValue("Ashish");
        Query key=mDatabase.orderByKey();
        Log.v(TAG,"Firebase Instance key is:" +key +" setting value: ");

        System.out.println("Git practicing: ");
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(getIntent().ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(myBroadcastReceiver,intentFilter);

        button1=(Button)findViewById(R.id.button);


        Toast.makeText(this, "hello bro" + mDatabase, Toast.LENGTH_LONG).show();
        Log.v(TAG,"onCreate");
        listView = findViewById(R.id.listViewHeroes);
        getHeroes();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

        ButterKnife.inject(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginPage.class);
                startActivity(intent);
                finish();
                //sendNotification();
            }
        });
        parseUsingGson(readJsonFromAsset());
        /*StringBuilder stringBuilder=new StringBuilder();
        try {

            InputStream fileInputStream= getAssets().open("my.json");
            while (true){
                int ch=fileInputStream.read();
                if (ch == -1)
                break;
                else {
                    stringBuilder.append((char)ch);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        parseUsingJsonObjectGson(stringBuilder.toString());*/

        time = (EditText) findViewById(R.id.in_time);
        empName = (EditText) findViewById(R.id.emp_name);
        button = (Button) findViewById(R.id.btn_run);
        finalResult = (TextView) findViewById(R.id.tv_result);
        count = (EditText) findViewById(R.id.count);
        text = (TextView) findViewById(R.id.text);
        service_btn=(Button) findViewById(R.id.service_btn);
        save=(Button) findViewById(R.id.addEmp);
        save.setOnClickListener(this);


         service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( getApplicationContext(), ServiceClass.class );
                startService(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = time.getText().toString();
                runner.execute(sleepTime);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v("TAG","onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v("TAG","onStop");
    }
    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
        Log.v("TechM","send notification is working"+ notificationManager);
    }
    @OnClick(R.id.button2)
    public void cancelNotification() {

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(1);
    }
    private void getHeroes() {

        Call<List<Hero>> call = RetrofitClient.getInstance().getMyApi().getHeroes();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }

                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String readJsonFromAsset(){
        StringBuilder stringBuilder=new StringBuilder();
        try {

            InputStream fileInputStream= getAssets().open("my.json");
            while (true){
                int ch=fileInputStream.read();
                if (ch == -1)
                    break;
                else {
                    stringBuilder.append((char)ch);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addEmp:
                submit();
                break;
        }
    }

    private void submit() {
        Model_EmpName model_empName=new Model_EmpName(empName.getText().toString(),"1");
        if (empName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter employee Name!", Toast.LENGTH_SHORT).show();
        }else {
            //model_empName.setName(empName.getText().toString());
            //String employee=empName.getText().toString();
            String userId=mDatabase.push().getKey();
            mDatabase.child(userId).setValue(model_empName);
            Log.v(TAG,"user id is: " + userId);
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for "+time.getText().toString()+ " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);

        }
    }
    private void parseUsingJsonObject(String json){

        StringBuilder stringBuilder= new StringBuilder();
        try {
            JSONObject jsonObject=new JSONObject(json);
            String name=jsonObject.getString("name");
            System.out.println("name from json: " + name);
            Log.v("NAME" ,name);

            JSONObject jsonObject1=jsonObject.getJSONObject("AllVersion");
            String base=jsonObject1.getString("base");
            Log.v("base" ,base);

            JSONArray jsonObject2=jsonObject.getJSONArray("AndroidVersion");
            for (int i=0;i<jsonObject2.length();i++){

                JSONObject loopObj=jsonObject2.getJSONObject(i);
                String mobile=loopObj.getString("mobile");
                String cost=loopObj.getString("cost");
                Log.v("mobile" ,mobile + "cost is: " + cost);
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    private void parseUsingGson(String json){

        Gson gson=new Gson();
        My my=gson.fromJson(json,My.class);
        Log.v("TechM", String.valueOf(my));
    }
}