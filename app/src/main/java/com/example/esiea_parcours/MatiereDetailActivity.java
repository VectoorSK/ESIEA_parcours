package com.example.esiea_parcours;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esiea_parcours.model.Annee;
import com.example.esiea_parcours.model.Bloc;
import com.example.esiea_parcours.model.Matiere;
import com.example.esiea_parcours.model.Objectif;
import com.example.esiea_parcours.network.GetDataService;
import com.example.esiea_parcours.network.RetrofitClientInstance;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatiereDetailActivity extends AppCompatActivity {

    Matiere matiere;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere_detail);

        int annee = getIntent().getIntExtra("annee", 0);
        String bloc = getIntent().getStringExtra("bloc");
        String nom = getIntent().getStringExtra("nom");
        loadMatiere(annee, bloc, nom);
    }

    private void loadMatiere(final int annee, final String bloc, final String nom) {
        progressDialog = new ProgressDialog(MatiereDetailActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Annee>> call = service.getAllYears();
        call.enqueue(new Callback<List<Annee>>() {
            @Override
            public void onResponse(Call<List<Annee>> call, Response<List<Annee>> response) {
                progressDialog.dismiss();

                for (Annee a : response.body()) {
                    if (a.getAnnee() == annee) {
                        for (Bloc b : a.getBlocs()) {
                            if (b.getNom().matches(bloc)) {
                                for (Matiere m : b.getMatieres()) {
                                    if (m.getNom().matches(nom)) {
                                        matiere = m;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }

                showMatiere();
            }

            @Override
            public void onFailure(Call<List<Annee>> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                progressDialog.dismiss();
                //Toast.makeText(MainActivity.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMatiere() {
        // load matière title
        TextView nameView = (TextView) findViewById(R.id.mat_nom);
        nameView.setText(matiere.getNom());
        // load matière coeff
        TextView coeffView = (TextView) findViewById(R.id.mat_coeff);
        if (matiere.getCoeff() == Math.floor(matiere.getCoeff())) {
            coeffView.setText(String.format("%.0f", matiere.getCoeff()));
        } else {
            coeffView.setText(Double.toString(matiere.getCoeff()));
        }
        // load matière nb heures
        TextView heureView = (TextView) findViewById(R.id.mat_heure);
        String nbHeure = String.format("%.0f", matiere.getNb_heures());
        heureView.setText(nbHeure);
        // load matière résumé
        TextView resumeView = (TextView) findViewById(R.id.mat_resume);
        resumeView.setText(matiere.getResume());
        // load matière content
        TextView contenuView = (TextView) findViewById(R.id.mat_contenu);
        contenuView.setText(matiere.getContenu());

        // load matière objectives
        ViewGroup container = (ViewGroup) findViewById(R.id.mat_objectif);
        for (Objectif objectif : matiere.getObjectifs_situations()) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View objLayout = layoutInflater.inflate(R.layout.objectifs_layout, null);
            TextView objView = (TextView) objLayout.findViewById(R.id.obj_obj);
            objView.setText(objectif.getObjectif());
            TextView sitView = (TextView) objLayout.findViewById(R.id.obj_sit);
            sitView.setText(objectif.getSituation());

            container.addView(objLayout, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // load matière repartition
        TextView repartView = (TextView) findViewById(R.id.mat_repartition);
        repartView.setText("Cours / TD : " + String.format("%.0f", matiere.getNbh_cours_td()) + "\n"
                        + "Cours : " + String.format("%.0f", matiere.getNbh_cours()) + "\n"
                        + "TD : " + String.format("%.0f", matiere.getNbh_td()) + "\n"
                        + "TP : " + String.format("%.0f", matiere.getNbh_tp()) + "\n"
                        + "Projet : " + String.format("%.0f", matiere.getNbh_projet()) + "\n"
                        + "Atelier : " + String.format("%.0f", matiere.getNbh_atelier()));
    }

    public void openCloseResume (View view) {
        TextView captionView = (TextView) findViewById(R.id.mat_resume_caption);
        TextView resumeView = (TextView) findViewById(R.id.mat_resume);
        if (resumeView.getVisibility() == View.GONE) {
            resumeView.setVisibility(View.VISIBLE);
            Drawable arrow_up = getResources().getDrawable(R.drawable.ic_arrow_up_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_up, null);
        } else {
            resumeView.setVisibility(View.GONE);
            Drawable arrow_down = getResources().getDrawable(R.drawable.ic_arrow_down_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_down, null);
        }
    }

    public void openCloseContenu (View view) {
        TextView captionView = (TextView) findViewById(R.id.mat_contenu_caption);
        TextView contenuView = (TextView) findViewById(R.id.mat_contenu);
        if (contenuView.getVisibility() == View.GONE) {
            contenuView.setVisibility(View.VISIBLE);
            Drawable arrow_up = getResources().getDrawable(R.drawable.ic_arrow_up_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_up, null);
        } else {
            contenuView.setVisibility(View.GONE);
            Drawable arrow_down = getResources().getDrawable(R.drawable.ic_arrow_down_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_down, null);
        }
    }

    public void openCloseObjectifs (View view) {
        TextView captionView = (TextView) findViewById(R.id.mat_objectif_caption);
        LinearLayout objectifView = (LinearLayout) findViewById(R.id.mat_objectif);
        if (objectifView.getVisibility() == View.GONE) {
            objectifView.setVisibility(View.VISIBLE);
            Drawable arrow_up = getResources().getDrawable(R.drawable.ic_arrow_up_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_up, null);
        } else {
            objectifView.setVisibility(View.GONE);
            Drawable arrow_down = getResources().getDrawable(R.drawable.ic_arrow_down_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_down, null);
        }
    }

    public void openCloseRepartition (View view) {
        TextView captionView = (TextView) findViewById(R.id.mat_repartition_caption);
        TextView repartView = (TextView) findViewById(R.id.mat_repartition);
        if (repartView.getVisibility() == View.GONE) {
            repartView.setVisibility(View.VISIBLE);
            Drawable arrow_up = getResources().getDrawable(R.drawable.ic_arrow_up_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_up, null);
        } else {
            repartView.setVisibility(View.GONE);
            Drawable arrow_down = getResources().getDrawable(R.drawable.ic_arrow_down_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_down, null);
        }
    }
}
