package kr.hs.emirim.s2120.mirim_project_0803_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
    Button btnSelect;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("아이돌 정보 입력하기");
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.edit_name);
        editCount = findViewById(R.id.editCount);
        editResultName = findViewById(R.id.edit_result_name);
        editResultCount = findViewById(R.id.edit_result_count);
        Button btnInit = findViewById(R.id.btn_init);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnSelect = findViewById(R.id.btn_select);
        Button btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnInit.setOnClickListener(btnListener);
        btnInsert.setOnClickListener(btnListener);
        btnUpdate.setOnClickListener(btnListener);
        btnDelete.setOnClickListener(btnListener);
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
                    btnSelect.callOnClick();
                    break;
                case R.id.btn_update:
                    db = dbHelpwer.getWritableDatabase();
                    db.execSQL("update idolTbl set cnt = "+editCount.getText().toString() + " where name='" + editName.getText().toString() + "';");
                    btnSelect.callOnClick();
                    editName.setText("");
                    editCount.setText("");
                    db.close();
                    break;
                case R.id.btn_delete:
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("삭제");
                    dlg.setIcon(R.drawable.idol);
                    dlg.setMessage("정말로 삭제하시겠습니까?");
                    dlg.setNegativeButton("취소", null);
                    dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db = dbHelpwer.getWritableDatabase();
                            db.execSQL("DELETE FROM idolTBL where name ='" + editName.getText().toString() + "';");
                            btnSelect.callOnClick();
                            db.close();
                            editName.setText("");
                            editCount.setText("");
                        }
                    });
                    dlg.show();
                    break;
                case R.id.btn_select:
                    // 조회
                    db = dbHelpwer.getReadableDatabase();
                    Cursor c =  db.rawQuery("select * from idolTbl", null);

                    String strName = "아이돌명\r\n__________\r\n";
                    String strCnt = "인원수\r\n__________\r\n";

                    while(c.moveToNext()){
                        strName += c.getString(0) + "\r\n";
                        strCnt += c.getString(1) + "\r\n";
                    }
                    editResultName.setText(strName);
                    editResultCount.setText(strCnt);

                    c.close();
                    db.close();
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