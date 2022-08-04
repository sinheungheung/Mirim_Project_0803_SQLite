# SQLite
##### SQLiteOpenHelper 클래스
* 생성자
* onCreate()
* onUpgrade()
* getReadableDatabase()
* getWritableDatabase()
##### * 주요 용도 *
* DB 생성
* 테이블 생성
* 테이블 삭제 후 다시 생성
* 읽기 전용 DB 열기, SQLiteDatabase 반환
* 읽고 쓰기용 DB 열기, SQLiteDatabase 반환

##### SQLiteDatabase 클래스
* execSQL()
* close()
* query(), rawQuery()
##### * 주요 용도 *
* SQL문(Insert, Update, Delete) 실행
* DB닫기
* Select 실행 후 커서 반환

##### Cursor 인터페이스
* moveTcFirst()
* moveTcLast()
* moveTcNext()
##### * 주요 용도 *
* 커서의 첫 행으로 이동
* 커서의 마지막 행으로 이동
* 현재 커서의 다음 행으로 이동
