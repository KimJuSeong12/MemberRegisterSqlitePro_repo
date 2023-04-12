package com.example.memberregistersqlitepro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memberregistersqlitepro.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    var member: Member? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSearchId.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
            val id = binding.edtUpdateId.text.toString()
            if (id.isEmpty()) {
                Toast.makeText(this, "수정할 id 입력바람", Toast.LENGTH_SHORT).show()
            } else {
                member = dbHelper.selectID(id)
                if (member != null) {
                    binding.edtPhone2.setText(member?.phone)
                    binding.edtName2.setText(member?.name)
                    binding.edtLevel2.setText(member?.level)
                } else {
                    Toast.makeText(this, "수정할 데이터정보를 가져오지 못함", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
            val phone = binding.edtPhone2.text.toString()
            val name = binding.edtName2.text.toString()
            val level = binding.edtLevel2.text.toString()

            if (member != null && phone.isNotBlank() && name.isNotBlank() && level.isNotBlank()) {
                member!!.name = name
                member!!.phone = phone
                member!!.level = level

                if (dbHelper.updateTBL(member)) {
                    Toast.makeText(this, "수정할 데이터 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "수정할 데이터 실패", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "수정할 데이터를 입력바람", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMain.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
