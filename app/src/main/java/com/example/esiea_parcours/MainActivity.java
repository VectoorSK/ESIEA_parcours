package com.example.esiea_parcours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esiea_parcours.model.Annee;
import com.example.esiea_parcours.model.Bloc;
import com.example.esiea_parcours.model.Matiere;
import com.example.esiea_parcours.network.GetDataService;
import com.example.esiea_parcours.network.RetrofitClientInstance;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Annee> datalist;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadCursus();
    }

    private void loadCursus() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Annee>> call = service.getAllYears();
        call.enqueue(new Callback<List<Annee>>() {
            @Override
            public void onResponse(Call<List<Annee>> call, Response<List<Annee>> response) {
                progressDialog.dismiss();
                datalist = response.body();
                Toast.makeText(MainActivity.this, "Loading success!", Toast.LENGTH_SHORT).show();
                showCursus();
            }

            @Override
            public void onFailure(Call<List<Annee>> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCursus() {
        for (Annee annee : datalist) {
            int idTitle = getResources().getIdentifier("title_" + annee.getAnnee() + "a", "id", getPackageName());
            TextView title = (TextView) findViewById(idTitle);
            title.setText("Année " + annee.getAnnee());
            int i = 0;
            for (Bloc bloc : annee.getBlocs()) {
                i++;
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.bloc_layout, null);
                TextView layout = (TextView) view.findViewById(R.id.bloc_titre);
                layout.setText(bloc.getNom());

                int containerID = getResources().getIdentifier("card_" + annee.getAnnee() + "a", "id", getPackageName());
                ViewGroup container = (ViewGroup) findViewById(containerID);
                container.addView(view, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


                for (Matiere matiere : bloc.getMatieres()) {
                    LayoutInflater layoutInflater2 = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                    View view2 = layoutInflater2.inflate(R.layout.matiere_layout, null);
                    TextView name = (TextView) view2.findViewById(R.id.buttonM);
                    name.setText(matiere.getNom());
                    ((ViewGroup) container.getChildAt(i)).addView(view2, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //ViewGroup container2 = (ViewGroup) findViewById(R.id.bloc_layout);
                    //container2.addView(view2, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                }
            }
        }
    }
}
