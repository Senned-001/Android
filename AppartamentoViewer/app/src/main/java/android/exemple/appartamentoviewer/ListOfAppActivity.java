package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ListOfAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_app);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfAppView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppartamentAdapter adapter = new AppartamentAdapter(this, MainActivity.appartamentList);
        recyclerView.setAdapter(adapter);
    }
}