package com.example.memberregistersqlitepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memberregistersqlitepro.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            //데이타베이스 생성을해서 SqlDatabase connection 가져온다.
            val dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)

            val id = binding.edtID.text.toString()
            val name = binding.edtName.text.toString()
            val password = binding.edtPassword.text.toString()
            val password2 = binding.edtPassword2.text.toString()
            val phone = binding.edtPhone.text.toString()
            val email = binding.edtEmail.text.toString()
            val address = binding.edtAddress.text.toString()
            val level = binding.edtLevel.text.toString()

            //패턴검색 및 데이타를 입력하지 않으면 경고메세지
            if (id.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty() || phone.isEmpty()
                || email.isEmpty() || address.isEmpty() || level.isEmpty()
            ) {
                Toast.makeText(this, "모든정보 입력바람", Toast.LENGTH_SHORT).show()
            } else if (!password.equals(password2)) {   //패스워드가  점검
                Toast.makeText(this, "패스워드 패스워드 재입력 맞지 않음", Toast.LENGTH_SHORT).show()
            } else if (dbHelper.selectCheckID(id)) {
                Toast.makeText(this, "아이디 중복입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val member = Member(id, name, password, phone, email, address, level)
                if (dbHelper.insertTBL(member)) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
        } //end of btnSave event
        binding.btnReturn.setOnClickListener {
            finish()
        }
    }
}
