package suraj.zhcet.amu.vtas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {
    TextView textViewusername;
    Button btnMyVehicle,btnMyLocation,addnewvehicle,button2;
    //String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        final String userName = getIntent().getStringExtra("userName");
        textViewusername=(TextView)findViewById(R.id.textViewusername);
        textViewusername.setText(userName);

        btnMyLocation=(Button)findViewById(R.id.btnMyLocation);
        btnMyVehicle=(Button)findViewById(R.id.btnMyVehicle);
        addnewvehicle=(Button)findViewById(R.id.addnewvehicle);
        button2=(Button)findViewById(R.id.button2);

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeScreen.this,MyLocation.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
        btnMyVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeScreen.this,MyVehicle.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
        addnewvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeScreen.this,AddVehicle.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
       button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeScreen.this,VehicleMap.class);
                String latlng="2757.1471,07805.5764";
                String []words=latlng.split(",");
                intent.putExtra("lat",words[0]);
                intent.putExtra("lng",words[1]);
               // intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

    }
}
