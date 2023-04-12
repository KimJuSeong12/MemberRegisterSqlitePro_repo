package com.example.memberregistersqlitepro

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context?, val name: String?, val version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "create table memberTBL(" +
                        "id text primary key," +
                        "name text not null," +
                        "password text not null," +
                        "phone text not null," +
                        "email text," +
                        "address text," +
                        "level text)"
            )
        }
//        db?.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists member")
        onCreate(db)
//        db?.close()
    }

    fun insertTBL(member: Member): Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            db.execSQL("insert into memberTBL values( '${member.id}','${member.name}','${member.password}', '${member.phone}', '${member.email}', '${member.address}', '${member.level}')")
            Log.d("DBHelper", "insert ${member.id} ${member.name} 성공")
            flag = true
        } catch (e: SQLException) {
            flag = false
        }
//        db.close()
        return flag
    }

    //memberTBL id가 존재하는지 유무
    fun selectCheckID(id: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var flag = false
        try {
            cursor = db.rawQuery("select id from memberTBL where id = '${id}'", null)
            if (cursor.count >= 1) {
                Log.d("DBHelper", "selectCheckID ${id} 존재함")
                flag = true
            } else {
                Log.d("DBHelper", "selectCheckID ${id} 존재하지 않음")
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectCheckID ${id} 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return flag
    }

    //memberTBL 로그인기능
    fun selectLogin(id: String, password: String): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var flag = false

        try {
            cursor = db.rawQuery(
                "select id from memberTBL where id = '${id}' and password = '${password}'",
                null
            )
            if (cursor.count >= 1) {
                Log.d("DBHelper", "selectLogin 로그인성공")
                flag = true
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectLogin ${id} 로그인 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return flag
    }

    //memberTBL id record를 가져오는것
    fun selectID(id: String): Member? {
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var member: Member? = null
        val mutableList: MutableList<Member>? = mutableListOf<Member>()
        try {
            cursor = db.rawQuery("select * from memberTBL where id = '${id}'", null)
            if (cursor.moveToFirst()) {
                Log.d("DBHelper", "selectID ${id} 데이터정보 로드성공")
                member = Member(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectID ${id} 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return member
    }

    //memberTBL 모든 데이터 가져오기
    fun selectAll(): MutableList<Member>? {
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        val mutableList: MutableList<Member>? = mutableListOf<Member>()
        try {
            cursor = db.rawQuery("select * from memberTBL", null)
            if (cursor.count >= 1) {
                Log.d("DBHelper", "selectAll 성공 ")
                while (cursor.moveToNext()) {
                    val member = Member(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                    mutableList?.add(member)
                }
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectAll 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return mutableList
    }

    //memberTBL 삭제기능
    fun deleteTBL(id: String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        var flag = false
        try {
            if (selectCheckID(id)) {
                db.execSQL("delete from memberTBL where id = '${id}'")
                Log.e("DBHelper", "delete ${id} 성공")
                flag = true
            } else {
                Log.e("DBHelper", "delete ${id} 존재하지 않음")
            }
        } catch (e: SQLException) {
            Log.e("DBHelper", "delete ${id} 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return flag
    }

    //memberTBl 수정기능
    fun updateTBL(member: Member?): Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            if (member != null) {
                db.execSQL("update memberTBL set phone = '${member?.phone}', name = '${member?.name}', level = '${member?.level}' where id = '${member?.id}'")
                Log.d("DBHelper", "update ${member?.id}  성공")
                flag = true
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "update 실패 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return flag
    }

}