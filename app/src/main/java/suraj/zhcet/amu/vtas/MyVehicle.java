package suraj.zhcet.amu.vtas;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;

public class MyVehicle extends AppCompatActivity implements OnItemClickListener{
    private static MyVehicle inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    String string[];
/*    String[] latlong =  "2757.1471,07805.5764".split(",");
    double latitude = Double.parseDouble(latlong[0]);
    double lat=latitude/100;
    double l=latitude-lat*100;
    double m=l/60;
    double n=(l-m*60)/60;
    double latt=lat+m+n;


    double longitude = Double.parseDouble(latlong[1]);
    double lon=longitude/100;
    double lo=longitude-lon*100;
    double mo=lo/60;
    double no=(lo-mo*60)/60;
    double lonn=lon+mo+no;
*/
    public static MyVehicle instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle2);
        smsListView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
        refreshSmsInbox();
       // myVehicle();
    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        String ph="+917078636658";
        final Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        final int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
       do {
           if(smsInboxCursor.getString(indexAddress).equals(ph)){
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
               string=smsInboxCursor.getString(indexBody).split("\\s");
            arrayAdapter.add(str);}
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
       /* try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }
            */
            Intent intent=new Intent(MyVehicle.this,VehicleMap.class);
            intent.putExtra("lat",string[8]);
            intent.putExtra("lng",string[12]);

            startActivity(intent);
        }
}
