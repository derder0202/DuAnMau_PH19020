package com.example.duanmau_ph19020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.ThuThuDAO
import com.example.duanmau_ph19020.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    private lateinit var thuThuDAO:ThuThuDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginActivityLayout.background.alpha = 130
        title = "LOGIN"
        thuThuDAO = ThuThuDAO(this)

        val preferences = getSharedPreferences("USER ACCOUNT", MODE_PRIVATE)
        binding.loginUsername.setText(preferences.getString("username",""))
        binding.loginPassword.setText(preferences.getString("password",""))
        binding.loginCheckbox.isChecked = preferences.getBoolean("check",false)

        binding.loginBtn.setOnClickListener {
            checkLogin()
        }
        binding.loginResetBtn.setOnClickListener {
//            binding.loginUsername.text = null
//            binding.loginPassword.text = null
            binding.loginCheckbox.isChecked = false
            TempFunc.resetField(binding.loginUsername,binding.loginPassword)

        }
    }
    fun rememberUser(user:String,password:String,check:Boolean){
        val preferences = getSharedPreferences("USER ACCOUNT", MODE_PRIVATE)
        val edit = preferences.edit()
        if(!check){
            edit.clear()
        } else{
            edit.putString("username",user)
            edit.putString("password",password)
            edit.putBoolean("check",check)
        }
        edit.apply()
    }
    fun checkLogin(){
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()
        val checkbox = binding.loginCheckbox
        if (username.isEmpty()||password.isEmpty()){
            Toast.makeText(this@LoginActivity,"Tên đăng nhập và mật khẩu không được bỏ trống",Toast.LENGTH_SHORT).show()
        } else{
            if(thuThuDAO.checkLogin(username,password)){
                Toast.makeText(this@LoginActivity,"Login thanh cong",Toast.LENGTH_SHORT).show()
                rememberUser(username,password,checkbox.isChecked)
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("user",username)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this@LoginActivity,"Tên đăng nhập và mật khẩu không đúng",Toast.LENGTH_SHORT).show()
            }
        }
    }
}