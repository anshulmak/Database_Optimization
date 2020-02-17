package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.Database.AppDatabase;
import com.example.test2.Entity.Word;
import com.example.test2.Utils.DatabaseInitializer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView time_tv,title_tv;
    private ProgressBar progressBar;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_tv = findViewById(R.id.time_tv);
        title_tv = findViewById(R.id.title_tv);
        progressBar = findViewById(R.id.progress_bar);

        DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));

        Button start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] htmls = new String[26];
                startTime = System.nanoTime();

                String html = "http://www.mso.anu.edu.au/~ralph/OPTED/v003/wb1913_";

                for(int i=0 ; i<26;i++){
                    htmls[i] = html.concat(String.valueOf((char)(i+97))).concat(".html");
                }

                new GetJSONTask().execute(htmls);
            }
        });

        }

    private static void addWord(@NonNull final AppDatabase db, Word word) {
        db.wordDao().insertAll(word);
    }

    private class GetJSONTask extends AsyncTask<String, Void, Document[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            title_tv.setText("Downloading");
            progressBar.setVisibility(View.VISIBLE);
            startTime = System.nanoTime();
        }

        @Override
        protected Document[] doInBackground(String... url) {

            Document[] docs = new Document[26];

            for(int i=0 ; i<26 ; i++){
                Document doc = null;
                try {
                    doc = Jsoup.connect(url[i]).get();
                } catch (IOException e) {
                    Log.d("error",e.toString());
                }
                docs[i] = doc;

            }
            return docs;
        }

        @Override
        protected void onPostExecute(Document[] docs) {

            for(int i = 0 ; i <26 ; i++){
                Document doc = docs[i];
                Elements elements = doc.select("p");

                for (Element element : elements){
                    int s1 = element.text().indexOf('(');
                    int s2 = element.text().indexOf(')');
                    if (s1<0){
                        continue;
                    }
                    if ((s2+1)>element.text().length() || s2<0 || s2<s1){
                        continue;
                    }
                    String word = element.text().substring(0,s1);
                    String figure_of_speech = element.text().substring(s1+1,s2);
                    String meaning = element.text().substring(s2+1);

                    Word word1 = new Word();
                    word1.setWord_name(word);
                    word1.setFigure_of_speech(figure_of_speech);
                    word1.setMeaning(meaning);
                    addWord(AppDatabase.getAppDatabase(getApplicationContext()), word1);
                }

                //Toast.makeText(getApplicationContext(),AppDatabase.getAppDatabase(getApplicationContext()).wordDao().countWords(),Toast.LENGTH_SHORT).show();
                Log.d("title",doc.title());
                Log.d("size", String.valueOf(AppDatabase.getAppDatabase(getApplicationContext()).wordDao().countWords()));

            }

            long endTime = System.nanoTime();
            long durationInMilis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime)/1000;
            long l = AppDatabase.getAppDatabase(getApplicationContext()).wordDao().countWords();
            title_tv.setText(l+" words");
            time_tv.setText(String.valueOf(durationInMilis) + " seconds");
            Toast.makeText(getApplicationContext(),"Words stored in Room database",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
