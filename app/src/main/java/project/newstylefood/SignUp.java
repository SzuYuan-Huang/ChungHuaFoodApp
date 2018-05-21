package project.newstylefood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import project.newstylefood.Model.User;

public class SignUp extends AppCompatActivity
{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference user;
    private Button btnSignUp;
    private EditText edtName,edtPhone,edtEmail,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference("users");

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(edtName.getText().toString())||TextUtils.isEmpty(edtPhone.getText().toString())||
                        TextUtils.isEmpty(edtEmail.getText().toString())|| TextUtils.isEmpty(edtPassword.getText().toString()))
                {
                    Toast.makeText(SignUp.this,"填寫地區不可為空白",Toast.LENGTH_LONG).show();
                }
                else
                {
                    final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this, "請稍等...", "處理中...", true);

                    firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    progressDialog.dismiss();

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignUp.this,"註冊成功",Toast.LENGTH_LONG).show();
                                        User myUser = new User(edtName.getText().toString(),edtPhone.getText().toString()
                                        ,edtEmail.getText().toString(),edtPassword.getText().toString());
                                        user.child(firebaseAuth.getCurrentUser().getUid()).setValue(myUser);
                                        Intent intent = new Intent(SignUp.this,Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUp.this,"註冊失敗",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
