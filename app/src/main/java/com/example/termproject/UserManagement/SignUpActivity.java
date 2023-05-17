package com.example.termproject.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.termproject.R;

/*
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 */

public class SignUpActivity extends AppCompatActivity {

    /*
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;

     */
    private static final String TAG = "SignUpActivity";
    private Long mLastClickTime = 10000L;
    Toolbar toolbar;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
        //make random authentication number
        String check;
        Random random = new Random();
        int num = random.nextInt(9999) + 1;
        if(num<1000){
            int plus = random.nextInt(9)+1;
            plus *=1000;
            num+=plus;
        }
        check = String.valueOf(num);

        findViewById(R.id.websitemoveTextView).setOnClickListener(mOnClickListener);
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(check);
            }
        });

        findViewById(R.id.authNumButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                    switch (view.getId()) {
                        case R.id.authNumButton:
                            buttonSendEmail(view, check);
                            break;
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();

            }
        });

 */
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.websitemoveTextView:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mail.knu.ac.kr/"));
                    startActivity(intent);
                    break;
            }
        }
    };

    private void buttonSendEmail(View view, String check){
        String stringReceiverEmail;
        EditText emailEditText;
        emailEditText = findViewById(R.id.emailEditText);
        stringReceiverEmail = emailEditText.getText().toString() +"@knu.ac.kr";
        try {
            String stringSenderEmail = "clubdictionary@gmail.com";
            String stringPasswordSenderEmail = "tlsghcjfldmddyd!";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: 신비한 동아리 사전 경북대 이메일 인증");
            mimeMessage.setText("신비한 동아리 사전 경북대 메일 인증을 위한 번호입니다.\n감사합니다!!\n\n"+check);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            toast("인증번호가 전송되었습니다.");
        } catch (AddressException e) {
            e.printStackTrace();
            toast("주소에 문제가 있습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            toast("문제가 생겼습니다.");
        }
    }
    private void signUp(String check){
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString().trim();
        email+="@knu.ac.kr";
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
        String authNum = ((EditText) findViewById(R.id.authNumEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.passwordcheckEditText)).getText().toString();


        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {

            if (password.equals(passwordCheck)) {
                if(authNum.equals(check)){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                //MemberInfo memberInfo = new MemberInfo(bedNum);
                                if (user != null) {
//                                mDb.collection("users").document(user.getUid()).set(memberInfo)
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                toast("회원정보 입력에 실패했습니다.");
//                                            }
//                                        });
                                    toast("회원가입에 성공했습니다!!");
                                    Intent intent = new Intent(SignUpActivity.this, MemberInitActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    //성공시 UI 로직
                                } else {
                                    // If sign in fails, display a message to the user.

                                    //실패시 UI 로직
                                    if (task.getException() != null) {
                                        toast("회원가입에 실패했습니다.");
                                    }

                                }
                            }
                        }
                    });
                }
                else{
                    toast("인증번호가 일치하지 않습니다.");
                }
            } else {
                toast("비밀번호가 일치하지 않습니다.");
            }
        } else {
            toast("정보를 모두 입력해 주세요.");
        }

    }

     */

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}