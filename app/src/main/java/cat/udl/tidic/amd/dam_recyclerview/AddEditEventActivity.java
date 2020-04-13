package cat.udl.tidic.amd.dam_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddEditEventActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_ID";
    public static final String EXTRA_DESCRIPTION =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_DESCRIPTION";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_TITLE";
    public static final String EXTRA_AVALUATION=
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_AVALUATION";
    public static final String EXTRA_START =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_START";
    public static final String EXTRA_END =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_END";

    private final static String TAG = "AddEditForm";

    private EditText editTextTitle;
    private EditText editTextEnd;
    private EditText editTextStart;
    private EditText editTextDescription;
    private RatingBar ratingBarAvaluation;

    // Página principal
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        editTextTitle = findViewById(R.id.edit_text_event_title);
        editTextDescription = findViewById(R.id.edit_text_event_description);
        editTextStart = findViewById(R.id.edit_text_event_start);
        editTextEnd = findViewById(R.id.edit_text_event_end);
        ratingBarAvaluation = findViewById(R.id.ratingBar_event);
        Button savaButton = findViewById(R.id.button_event_save);


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Event");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextStart.setText(intent.getStringExtra(EXTRA_START));
            editTextEnd.setText(intent.getStringExtra(EXTRA_END));
            ratingBarAvaluation.setRating(intent.getFloatExtra(EXTRA_AVALUATION,1));

        } else {
            setTitle("Add Event");
        }

        savaButton.setOnClickListener(v -> saveEvent());

    }


    // Funció saveEvent, guarda els canvis fets als events i actualitza a la página principal.
    private void saveEvent() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String start = editTextStart.getText().toString();
        String end = editTextEnd.getText().toString();
        float avaluation = ratingBarAvaluation.getRating();

        Log.d(TAG, "" +avaluation);

        // Si el títol o la descipció son buides salta un error (tipus toast)
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this,
                    "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciació intents
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_START, start);
        data.putExtra(EXTRA_END, end);
        data.putExtra(EXTRA_AVALUATION, avaluation);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


}
