package appewtc.masterung.ishihara7mar15;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private TextView txtQuestion;
    private ImageView imvIshihara;
    private RadioGroup ragChoice;
    private RadioButton radioButton1, radioButton2,
            radioButton3, radioButton4;
    private Button btnAnswer;
    private int intRadioButton, intIndex, intScore,
            intIshihara[], intChoice[], intUserChoose[], intAnswerTrue[];
    private MyModel objMyModel;
    private String strChoice[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Setup Array
        setupArray();

        //Button Controller
        buttonController();

        //Radio Controller
        radioController();

        //Interface MyModel
        interfaceMyModel();

    }   // onCreate

    private void setupArray() {

        //assign address Image
        intIshihara = new int[]{R.drawable.ishihara_01,
                R.drawable.ishihara_02, R.drawable.ishihara_03,
                R.drawable.ishihara_04, R.drawable.ishihara_05,
                R.drawable.ishihara_06, R.drawable.ishihara_07,
                R.drawable.ishihara_08, R.drawable.ishihara_09,
                R.drawable.ishihara_10};

        //assign Choice
        intChoice = new int[]{R.array.times1, R.array.times2, R.array.times3,
                R.array.times4, R.array.times5, R.array.times6,
                R.array.times7, R.array.times8,
                R.array.times9, R.array.times10};

        //assign Answer
        intAnswerTrue = new int[]{1, 2, 3, 1, 2, 3, 1, 2, 4, 4};

    }   // setupArray



    private void interfaceMyModel() {
        objMyModel = new MyModel();
        objMyModel.setOnMyModelChangeListener(new MyModel.OnMyModelChangeListener() {
            @Override
            public void onMyModelChangeListener(MyModel myModel) {

                changeViewByMyModel(myModel.getIntButton());

            }   // event
        });
    }

    private void radioController() {
        ragChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //Sound Effect
                MediaPlayer soundRadio = MediaPlayer.create(getBaseContext(), R.raw.effect_btn_shut);
                soundRadio.start();

                //setup intRadioButton
                switch (checkedId) {
                    case R.id.radioButton:
                        intRadioButton = 1;
                        break;
                    case R.id.radioButton2:
                        intRadioButton = 2;
                        break;
                    case R.id.radioButton3:
                        intRadioButton = 3;
                        break;
                    case R.id.radioButton4:
                        intRadioButton = 4;
                        break;
                    default:
                        intRadioButton = 0;
                        break;
                }   // switch


            }   //event
        });
    }

    private void buttonController() {
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sound Effect
                MediaPlayer soundButton = MediaPlayer.create(getBaseContext(), R.raw.effect_btn_long);
                soundButton.start();

                //Check Answer
                checkAnswer();

            }   // event
        });
    }

    private void checkAnswer() {

        if (intRadioButton == 0) {

            Toast.makeText(MainActivity.this, "กรุณา ตอบคำถาม ด้วยคะ", Toast.LENGTH_LONG).show();

        } else {

            //Clear Check
            ragChoice.clearCheck();

            //Check Score
            checkScore();

            //Check Times นี่คือการเซ็คข้อ
            checkTimes();

        }   // if

    }   // checkAnswer

    private void checkScore() {

        intUserChoose = new int[10];
        intUserChoose[intIndex] = intRadioButton;

        if (intUserChoose[intIndex] == intAnswerTrue[intIndex]) {
            intScore++;
        }

    }

    private void checkTimes() {

        if (intIndex == 9) {

            //Intent to ShowScoreActivity
            Intent objIntent = new Intent(MainActivity.this, ShowScoreActivity.class);
            objIntent.putExtra("Score", intScore);
            startActivity(objIntent);
            finish();

            //Show Log
            Log.d("Test", "คะแนนของคุณคือ ==> " + Integer.toString(intScore));

        } else {

            //Call View my buttonController
            txtQuestion.setText(Integer.toString(intIndex + 2) + ". What is this ?");
            intIndex += 1;

            //Call MyModel
            objMyModel.setIntButton(intIndex);


        }   // if

    }   // checkTime

    private void changeViewByMyModel(int intMyModel) {

        //Change Image
        imvIshihara.setImageResource(intIshihara[intMyModel]);

        //Change Choice
        strChoice = getResources().getStringArray(intChoice[intMyModel]);
        radioButton1.setText(strChoice[0]);
        radioButton2.setText(strChoice[1]);
        radioButton3.setText(strChoice[2]);
        radioButton4.setText(strChoice[3]);

    }   // changeView

    private void bindWidget() {
        txtQuestion = (TextView) findViewById(R.id.textView2);
        imvIshihara = (ImageView) findViewById(R.id.imageView);
        ragChoice = (RadioGroup) findViewById(R.id.ragChoice);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        btnAnswer = (Button) findViewById(R.id.button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemAboutMe:
                Intent objIntent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(objIntent);
                break;
            case R.id.itemHow:
                Intent objIntent1 = new Intent(MainActivity.this, HowToUserActivity.class);
                startActivity(objIntent1);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
