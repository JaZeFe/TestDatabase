package com.example.jzf.testdatabase;

import android.content.ContentValues;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private MyDatabaseHelper dbHelper;
    private Button Create_button,Add_button,Update_button,Delete_button,Query_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this ,"BookStore.db" ,null ,1);

        Create_button = (Button) findViewById(R.id.Create_button);
        Add_button = (Button) findViewById(R.id.Add_button);
        Update_button = (Button) findViewById(R.id.Update_button);
        Delete_button = (Button) findViewById(R.id.Delete_button);
        Query_button = (Button) findViewById(R.id.Query_button);

        Create_button.setOnClickListener(new ButtonListener());
        Add_button.setOnClickListener(new ButtonListener());
        Update_button.setOnClickListener(new ButtonListener());
        Delete_button.setOnClickListener(new ButtonListener());
        Query_button.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener{

        public void onClick(View v){
            switch (v.getId()){
                case R.id.Create_button: {
                    dbHelper.getWritableDatabase();
                    break;
                }

                case R.id.Add_button:{
                    DatabaseManager databaseManager = new DatabaseManager(MainActivity.this);
                    ContentValues values = new ContentValues();
                    values.put("name","The Da Vinci Code");
                    values.put("author","Dan Brown");
                    values.put("pages",454);
                    values.put("price", 16.96);
                    values.put("id", 1233321);
                    String data = databaseManager.queryData("Book", new String[]{"author"}, "id = ?",new String[] {"1233321"});
                    if(data == null){
                        databaseManager.insertData("Book", values);
                        Toast.makeText(MainActivity.this,"Add Succeeded",Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this,"The ID has existed!",Toast.LENGTH_SHORT).show();
                    break;
                }

                case R.id.Update_button:{
                    DatabaseManager databaseManager = new DatabaseManager(MainActivity.this);
                    ContentValues values = new ContentValues();
                    values.put("price",10.99);
                    databaseManager.updateData("Book", values, "id = ?", new String[]{"1233321"});
                    Toast.makeText(MainActivity.this,"Update Succeeded",Toast.LENGTH_SHORT).show();
                    break;
                }

                case R.id.Delete_button:{
                    DatabaseManager databaseManager = new DatabaseManager(MainActivity.this);
                    databaseManager.deleteData("Book", "pages > ?", new String[]{"400"});
                    Toast.makeText(MainActivity.this,"Delete Succeeded",Toast.LENGTH_SHORT).show();
                    break;
                }

                case R.id.Query_button:{
                    DatabaseManager databaseManager = new DatabaseManager(MainActivity.this);
                    String data = databaseManager.queryData("Book", new String[]{"author"}, "id = ?",new String[] {"1233321"});
                    if(data != null)
                        Toast.makeText(MainActivity.this,"Author is "+ data,Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Hasn't this ID",Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
