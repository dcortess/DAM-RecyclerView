package cat.udl.tidic.amd.dam_recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cat.udl.tidic.amd.dam_recyclerview.models.Event;
import cat.udl.tidic.amd.dam_recyclerview.preferences.PreferencesProvider;
import cat.udl.tidic.amd.dam_recyclerview.viewmodel.EventViewModel;

public class EventsListActivity extends AppCompatActivity implements LifecycleOwner {

    public static final int INSERT_EVENT = 1;
    public static final int EDIT_EVENT = 2;
    public static final String TAG = "EventListActivity";
    private SharedPreferences mPreferences;


    private EventViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        this.mPreferences = PreferencesProvider.providePreferences();
        initViews();
    }

    private void initViews() {
        ImageButton addButton = findViewById(R.id.createEvent);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this,
                    AddEditEventActivity.class);
            startActivityForResult(intent, INSERT_EVENT);
        });

        RecyclerView recyclerView = findViewById(R.id.activityMainRcyMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final EventAdapter adapter = new EventAdapter(new EventDiffCallback());
        recyclerView.setAdapter(adapter);

        // Inicialització intent i entrada de dades
        adapter.setOnItemClickListener(event -> {
            Log.d(TAG, event.getTittle());
            Intent intent = new Intent(EventsListActivity.this,
                    AddEditEventActivity.class);
            intent.putExtra(AddEditEventActivity.EXTRA_ID, event.getId());
            intent.putExtra(AddEditEventActivity.EXTRA_TITLE, event.getTittle());
            intent.putExtra(AddEditEventActivity.EXTRA_DESCRIPTION, event.getDescription());
            intent.putExtra(AddEditEventActivity.EXTRA_START, event.getStart());
            intent.putExtra(AddEditEventActivity.EXTRA_END, event.getEnd());
            intent.putExtra(AddEditEventActivity.EXTRA_AVALUATION, event.getAvaluation());
            startActivityForResult(intent, EDIT_EVENT);
        });

        viewModel = new EventViewModel(this.getApplication());
        viewModel.setUserId("");
        viewModel.getEvents().observe(this, adapter::submitList);

        ImageButton searchButton = findViewById(R.id.searchButton);

        // Funció buscar (no acabada d'implementar)
        searchButton.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.activityMainAtcEventUserId);
            viewModel.setUserId(textView.getText().toString());
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull  RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Eliminar events
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.removeEvent(adapter.getEventAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Deleted event",
                        Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Si es vol crear un event i és possible
        if (requestCode == INSERT_EVENT && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditEventActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditEventActivity.EXTRA_DESCRIPTION);
            String start = data.getStringExtra(AddEditEventActivity.EXTRA_START);
            String end = data.getStringExtra(AddEditEventActivity.EXTRA_END);
            float avaluation = data.getIntExtra(AddEditEventActivity.EXTRA_AVALUATION,
                    1);

            String current_user = this.mPreferences.getString("current_user", "");
            Event event = new Event(Integer.parseInt(current_user),start,end,title,description,
                    avaluation);
            viewModel.insert(event);

            Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_EVENT && resultCode == RESULT_OK) {
            // Si es vol editar un event i és possible
            int id = data.getIntExtra(AddEditEventActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Event can't be updated",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtenció dades intent
            String title = data.getStringExtra(AddEditEventActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditEventActivity.EXTRA_DESCRIPTION);
            String start = data.getStringExtra(AddEditEventActivity.EXTRA_START);
            String end = data.getStringExtra(AddEditEventActivity.EXTRA_END);
            float avaluation = data.getFloatExtra(AddEditEventActivity.EXTRA_AVALUATION,
                    1);

            String current_user = this.mPreferences.getString("current_user", "");
            Event event = new Event(Integer.parseInt(current_user),start,end,title,description,
                    avaluation);

            event.setId(id);
            viewModel.update(event);

            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
        } else {
            // Si no és cap de les anteriors (no possible)
            Toast.makeText(this, "Event not saved", Toast.LENGTH_SHORT).show();
        }
    }
}




