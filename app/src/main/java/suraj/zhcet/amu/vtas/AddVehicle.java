package suraj.zhcet.amu.vtas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddVehicle extends AppCompatActivity {
    RadioGroup gtype;
    RadioButton vtype;
    Button btnadd;
    EditText vrno;
    VREgDataBaseAdapter vDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        final String userName = getIntent().getStringExtra("userName");
        // get Instance  of Database Adapter
        vDataBaseAdapter = new VREgDataBaseAdapter(this);
        vDataBaseAdapter = vDataBaseAdapter.open();

        gtype=(RadioGroup)findViewById(R.id.radioGroup);
        vrno=(EditText)findViewById(R.id.vrno);
        btnadd = (Button) findViewById(R.id.add);

        btnadd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String Vrno = vrno.getText().toString();
                int selectedId=gtype.getCheckedRadioButtonId();
                vtype=(RadioButton)findViewById(selectedId);
                String Vtype = vtype.getText().toString();
                //Toast.makeText(AddVehicle.this,vtype.getText(),Toast.LENGTH_SHORT).show();

                // check if any of the fields are vaccant
                if (Vrno.equals("") || vtype.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    // Save the Data in Database
                    vDataBaseAdapter.insertEntry(Vtype, Vrno, userName);
                    Toast.makeText(getApplicationContext(), "Vehicle Successfully Added ", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddVehicle.this,WelcomeScreen.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        vDataBaseAdapter.close();
    }
}

