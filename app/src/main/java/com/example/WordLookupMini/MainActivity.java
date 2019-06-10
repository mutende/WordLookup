package com.example.WordLookupMini;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MobileAdapter mobileAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button buttonTranslate;
    private Button exitButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.result);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buttonTranslate = findViewById(R.id.btn_translate);
        exitButton = findViewById(R.id.btn_exit);
        editText = findViewById(R.id.editText);

        mobileAdapter = new MobileAdapter();
        mobileAdapter.setDataSet(new String[]{"Enter a word to translate"});
        recyclerView.setAdapter(mobileAdapter);


        buttonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileAdapter.setDataSet(
                        translations(editText.getText().toString()));
                mobileAdapter.notifyDataSetChanged();

            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String[] translations(String word){
        ArrayList<String> swahiliWords = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(getAssets().open("data.csv")))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(word)){
                    swahiliWords.add(values[0]);
                }

            }

            String[] arr = new String[swahiliWords.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = swahiliWords.get(i);
            }

            if (arr.length == 0){
                arr = new String[]{"The Word you entered is not in the dictionary"};
            }

            return arr;

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{"Empty"};

    }
}
