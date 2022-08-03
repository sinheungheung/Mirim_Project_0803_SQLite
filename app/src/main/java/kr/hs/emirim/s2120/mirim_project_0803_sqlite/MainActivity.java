package kr.hs.emirim.s2120.mirim_project_0803_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelpwer dbHelpwer; // 필드선언
    EditText editName, editCount, editResultName, editResultCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.edit_name);
        editCount = findViewById(R.id.editCount);
        editResultName = findViewById(R.id.edit_result_name);
        editResultCount = findViewById(R.id.edit_result_count);
        Button btnInit = findViewById(R.id.btn_init);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnSelect = findViewById(R.id.btn_select);
        btnInit.setOnClickListener(btnListener);
        btnInsert.setOnClickListener(btnListener);
        btnSelect.setOnClickListener(btnListener);

       dbHelpwer = new DBHelpwer(this);
    }
    View.OnClickListener btnListener = new View.OnClickListener() {
        SQLiteDatabase db; // 참조변수 선언
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_init:
                    db = dbHelpwer.getWritableDatabase(); // 초기화
                    dbHelpwer.onUpgrade(db, 1, 2); // 초기화를 하려면 버전을 바꿔야함
                    db.close();
                    break;
                case R.id.btn_insert:
                    db = dbHelpwer.getWritableDatabase();
                    db.execSQL("insert into idolTbl values('"+editName.getText().toString()+"',"+editCount.getText().toString()+");");
                    db.close();
                    Toast.makeText(getApplicationContext(), "새로운 idol정보가 추가되었습니다.", Toast.LENGTH_SHORT).show(); // 텍스트 보여주기
                    editName.setText(""); // 입력한 후 입력창이 자동으로 비워지기
                    editCount.setText(""); // 입력한 후 입력창이 자동으로 비워지기
                    break;
                case R.id.btn_select:

                    break;
            }
        }
    };
    public class DBHelpwer extends SQLiteOpenHelper{
        // DB 생성
    public DBHelpwer(Context context){
        super(context, "idolDB", null, 1);

    }
        // TABLE 생성
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE idolTbl (name CHAR(30) PRIMARY KEY," + "cnt INTEGER);");
        }

        // TABLE 제거
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("drop table if exists idolTbl");
            onCreate(db);
        }
    }
}