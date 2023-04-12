package com.example.memberregistersqlitepro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.memberregistersqlitepro.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    val binding by lazy { ActivityDeleteBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnDelete2.setOnClickListener {
            //데이타베이스 생성을해서 SqlDatabase connection 가져온다.
            val db = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
            val id = binding.edtID2.text.toString()

            //패턴검색 , 아이디, 패스워드 길이 체크
            if (id.isEmpty()) {
                Toast.makeText(this, "ID 정보 입력바람", Toast.LENGTH_SHORT).show()
            } else {
                if (db.deleteTBL(id)) {
                    Toast.makeText(this, "ID 정보 삭제성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "ID 정보 삭제실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnMain2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
