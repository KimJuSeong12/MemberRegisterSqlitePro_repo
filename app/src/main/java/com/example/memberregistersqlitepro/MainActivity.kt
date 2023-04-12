package com.example.memberregistersqlitepro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.memberregistersqlitepro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val DB_NAME = "memberDB.db"
        val VERSION = 1
    }

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
        binding.btnSub.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSub -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLogin -> {
                //데이타베이스 생성을해서 SqlDatabase connection 가져온다.
                val id = binding.edtID.text.toString()
                val password = binding.edtPWD.text.toString()
                //패턴검색 , 아이디, 패스워드 길이 체크
                if (id.isNullOrBlank() || id.isEmpty() || password.isNullOrBlank() || password.isEmpty()) {
                    Toast.makeText(this, "ID, PASSWORD 입력바람", Toast.LENGTH_SHORT).show()
                } else {
                    if (dbHelper.selectLogin(id, password)) {
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
