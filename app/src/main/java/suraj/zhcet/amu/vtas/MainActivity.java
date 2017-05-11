package suraj.zhcet.amu.vtas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn;
    EditText editTextUserName, editTextPassword;
    TextView textviewsignup;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        editTextUserName=(EditText)findViewById(R.id.username);
        editTextPassword=(EditText)findViewById(R.id.passwd);
        textviewsignup = (TextView) findViewById(R.id.textviewsignup);
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);


        textviewsignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    //dialog.dismiss();

                    Intent intent=new Intent(MainActivity.this,WelcomeScreen.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}