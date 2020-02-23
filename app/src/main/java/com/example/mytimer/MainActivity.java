package com.example.mytimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMin, etSec;
    TextView tvMin, tvSec;
    int min, sec;
    TimerTask t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMin=findViewById(R.id.etMin);
        etSec=findViewById(R.id.etSec);
        tvMin=findViewById(R.id.tvMin);
        tvSec=findViewById(R.id.tvSec);
    }

    public void btnClicked(View view) {
        if(view.getId()==R.id.btnStart){
            startBtn();
        }
        else if(view.getId()==R.id.btnPR){
            PRBtn();
        }
        else if(view.getId()==R.id.btnReset){
            resetBtn();
        }
    }

    private void startBtn(){

        if(t!=null){
            Toast.makeText(MainActivity.this, "Please Reset", Toast.LENGTH_SHORT).show();
            return;
        }

        String m=etMin.getText().toString();
        String s=etSec.getText().toString();

        if(m.equals("") && s.equals("")){
            Toast.makeText(MainActivity.this, "Enter Time!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(m.equals("")){
            min=0;
        }
        else{
            min=Integer.parseInt(m);
        }
        if(s.equals("")){
            sec=0;
        }
        else{
            sec=Integer.parseInt(s);
        }

        t=new TimerTask();
        t.execute( min*60 + sec );
    }

    private void PRBtn(){

        if(t!=null){
            t.cancel(true);
            t=null;

            if(!tvMin.getText().toString().equals("Time")){

                min=Integer.parseInt( tvMin.getText().toString() );
                sec=Integer.parseInt( tvSec.getText().toString() );
            }
        }
        else{

            if(  !(min==0 && sec==0)  ){
                t=new TimerTask();
                t.execute( min*60 +sec );
            }
        }
    }

    private void resetBtn(){
        etMin.setHint("Min");
        etSec.setHint("Sec");

        tvMin.setText("");
        tvSec.setText("");

        min=0;
        sec=0;

        if(t!=null){
            t.cancel(true);
            t=null;
        }
    }

    void wait1Sec(){
        long startTime=System.currentTimeMillis();
        while(System.currentTimeMillis() < startTime+1000);
    }

    class TimerTask extends AsyncTask<Integer, String, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {

            int totalSec=integers[0];

            for(int i=totalSec; i>=0; i--){

                if(isCancelled()){
                    break;
                }
                wait1Sec();

                if(i!=0){
                    publishProgress( String.valueOf(i/60), String.valueOf(i%60) );
                }
                else{
                    publishProgress( "Time", "Out" );
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            tvMin.setText(values[0]);
            tvSec.setText(values[1]);
        }
    }
}
