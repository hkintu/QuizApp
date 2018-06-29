package com.example.henry.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView marksView;
    private TextView questionNumber;
    float submittedMarks;
    float radioMarks;
    int editText1Answer;
    int editText2Answer;


    //method to handle the onClick menu item clicked which include the reset and exit menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //initialise menu item id here
        int id = item.getItemId();

        if (id == R.id.reset) {
            resetGrade();
            return true;
        }
        if (id == R.id.exit) {

            // method to confirm if the user would like to exit the app
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you really wish to Exit Quiz?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //method for handling reset marks and refresh the app
    private void resetGrade() {

        submittedMarks = 0;
        radioMarks = 0;
        editText1Answer = 0;
        editText2Answer = 0;
        displayMarks(submittedMarks + radioMarks + editText1Answer + editText2Answer);

        finish();
        startActivity(getIntent());

    }

    // method for creating menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create objects to link with XML TextViews where questions are to be displayed

        questionNumber = (TextView) findViewById(R.id.question_number_one);
        questionNumber.setText("QUESTION 1\n" +
                "In an Ethernet network, under what two scenarios can devices transmit? (Choose two.)");

        questionNumber = (TextView) findViewById(R.id.question_number_two);
        questionNumber.setText("QUESTION 2\n" + "Which layer in the OSI reference model is responsible for determining the availability of the receiving program and checking to see if enough resources exist for that communication?");

        questionNumber = (TextView) findViewById(R.id.question_number_three);
        questionNumber.setText("QUESTION 3\n" +
                "How many bits are contained in each field of an IPv6 address?");

        questionNumber = (TextView) findViewById(R.id.question_number_four);
        questionNumber.setText("QUESTION 4\n" +
                "Name the device below");

    }

    //method for the final submission after all methods have finished their tasks
    public void submitMarks(View v) {

        // objects are created for the checkBoxes view

        CheckBox answerOne = (CheckBox) findViewById(R.id.answer_1);
        boolean answerOneIsAnswer = answerOne.isChecked();

        CheckBox answerTwo = (CheckBox) findViewById(R.id.answer_2);
        boolean answerTwoIsAnswer = answerTwo.isChecked();

        CheckBox answerThree = (CheckBox) findViewById(R.id.answer_3);
        boolean answerThreeIsAnswer = answerThree.isChecked();

        CheckBox answerFour = (CheckBox) findViewById(R.id.answer_4);
        boolean answerFourIsAnswer = answerFour.isChecked();

        // an object created for the radioButton view
        RadioButton answerRadio4 = (RadioButton) findViewById(R.id.answer_radio4);
        boolean selectedButton4 = answerRadio4.isChecked();

        // submissions for all methods in the application
        submittedMarks = calculateMarks(answerOneIsAnswer, answerTwoIsAnswer, answerThreeIsAnswer, answerFourIsAnswer);
        radioMarks = calculateForRadioButton(selectedButton4);
        editText1Answer = checkForText1();
        editText2Answer = checkForText2();

        // method that displays marks scored for all answered questions
        displayMarks(submittedMarks + radioMarks + editText1Answer + editText2Answer);

        //we create an object to link to the scroll view
        final View scrollView = (View) findViewById(R.id.scroll_view);

        //the method is used to scroll back to the top for the student to see
        //their grade
        String toastedMarks = String.valueOf(v);
        scrollView.scrollTo(0, 0);
        Toast.makeText(getApplicationContext(), "The Quiz Score is: "+getText(), Toast.LENGTH_LONG).show();

        //method for hiding keyboard when the submit button is clicked
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // method that checks whether the right checkBoxes are checked then award mark,
    // for each checkBox with correct answer 12.5% is awarded
    public float calculateMarks(boolean answerOneIsChecked, boolean answerTwoIsChecked, boolean ansThreeIsChecked, boolean ansFourIsChecked) {
        float finalMark = 0;
        if (ansThreeIsChecked) {
            finalMark += 12.5;
            if (ansFourIsChecked) {
                finalMark += 12.5;
            }
        }
        if (answerOneIsChecked && answerTwoIsChecked && ansThreeIsChecked && ansFourIsChecked) {
            Toast.makeText(getApplicationContext(), "Choose only two from Qn.1", Toast.LENGTH_SHORT).show();
            finalMark = 0;
        }
        return finalMark;
    }

    // method that that checks if the button with the correct answer is selected and then awards
    // marks 25%
    public float calculateForRadioButton(boolean radioChecked4) {
        float markForRadioButton = 0;

        if (radioChecked4) {
            markForRadioButton += 25;
        } else {
            markForRadioButton += 0;
        }
        return markForRadioButton;
    }

    // method for checking whether entered text for question three is correct then award marks 25%
    public int checkForText1() {
        EditText editText = (EditText) findViewById(R.id.question_three_answer_editText);

        String text1 = editText.getText().toString();
        String answerForQuestionThree = "16";
        int answer = 0;
        if (text1.equals(answerForQuestionThree)) {
            answer += 25;
        }
        return answer;
    }

    // method for checking whether entered text for question four is correct then award marks 25%
    public int checkForText2() {
        EditText editText = (EditText) findViewById(R.id.question_four_answer_editText);

        String text2 = editText.getText().toString();
        String answer1 = "Router";
        String answer2 = "router";
        int answer = 0;
        if (text2.equals(answer1) || text2.equals(answer2)) {
            answer += 25;
        }
        return answer;
    }

    // method for displaying marks scored after the submit button is pressed
    public void displayMarks(float v) {
        marksView = (TextView) findViewById(R.id.marks_textView);
        marksView.setText(String.valueOf(v) + "%");

    }
    public String getText() {
        marksView=(TextView)findViewById(R.id.marks_textView);
        String text = marksView.getText().toString();
        return text;
    }

    /**
     * Called to process touch screen events.  You can override this to
     * intercept all touch screen events before they are dispatched to the
     * window.  Be sure to call this implementation for touch screen events
     * that should be handled normally.
     *
     * @param ev The touch screen event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if (v instanceof EditText){
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())){
                   v.clearFocus();
                   InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
