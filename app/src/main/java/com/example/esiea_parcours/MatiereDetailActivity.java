package com.example.esiea_parcours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esiea_parcours.model.Annee;
import com.example.esiea_parcours.model.Bloc;
import com.example.esiea_parcours.model.Matiere;
import com.example.esiea_parcours.model.Objectif;
import com.example.esiea_parcours.network.GetDataService;
import com.example.esiea_parcours.network.RetrofitClientInstance;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
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
        int semestre = getIntent().getIntExtra("semestre", 1);
        loadMatiere(annee, bloc, nom, semestre);
    }

    private void loadMatiere(final int annee, final String bloc, final String nom, final int semestre) {
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
                                    if (m.getNom().matches(nom) && m.getSemestre() == semestre) {
                                        matiere = m;
                                        showRepartitionChart();
                                        showMatiere();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Annee>> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                progressDialog.dismiss();
                Toast.makeText(MatiereDetailActivity.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRepartitionChart() {
        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        int id = 0;
        String[] labels = {"Cours / TD : ", "Cours : ", "TD : ", "TP : ", "Projet : ", "Atelier : ", "Soutenance : "};
        Matiere test = matiere;
        for (double heure : test.getRepartition()) {
            if (heure != 0) {
                double percent = heure * 100 / matiere.getNb_heures();
                int colorID = getResources().getIdentifier("bluePalette" + id, "color", getPackageName());
                SliceValue slice = new SliceValue((float)percent, getResources().getColor(colorID));
                if (heure == Math.floor(heure)) {
                    pieData.add(slice.setLabel(labels[id] + String.format("%.0f", heure) + "h"));
                } else {
                    pieData.add(slice.setLabel(labels[id] + heure + "h"));
                }
            }
            id++;
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelBackgroundEnabled(false);
        pieChartData.setValueLabelsTextColor(0xff000000);
        pieChartData.setHasCenterCircle(true).setCenterText1("Répartition").setCenterText1FontSize(20);
        pieChartView.setPieChartData(pieChartData);
    }

    private void showMatiere() {
        // load matière nom
        TextView nameView = (TextView) findViewById(R.id.mat_nom);
        nameView.setText(matiere.getNom());

        // load matière coeff
        TextView coeffView = (TextView) findViewById(R.id.mat_coeff);
        if (matiere.getCoeff() == Math.floor(matiere.getCoeff())) {
            coeffView.setText(" " + String.format("%.0f", matiere.getCoeff()));
        } else {
            coeffView.setText(" " + Double.toString(matiere.getCoeff()));
        }

        // load matière nb heures
        TextView heureView = (TextView) findViewById(R.id.mat_heure);
        if (matiere.getNb_heures() == Math.floor(matiere.getNb_heures())) {
            heureView.setText(" " + String.format("%.0f", matiere.getNb_heures()));
        } else {
            heureView.setText(" " + Double.toString(matiere.getNb_heures()));
        }

        // load matière résumé
        if (matiere.getResume().matches("")) {
            Button resumeBtn = (Button) findViewById(R.id.mat_resume_caption);
            resumeBtn.setVisibility(View.GONE);
        } else {
            TextView resumeView = (TextView) findViewById(R.id.mat_resume);
            resumeView.setText(matiere.getResume());
        }

        // load matière contenu
        if (matiere.getContenu().matches("")) {
            Button contenuBtn = (Button) findViewById(R.id.mat_contenu_caption);
            contenuBtn.setVisibility(View.GONE);
        } else {
            TextView contenuView = (TextView) findViewById(R.id.mat_contenu);
            contenuView.setText(matiere.getContenu());
        }

        // load matière objectifs
        if (matiere.getObjectifs_situations().size() == 0) {
            Button objectifBtn = (Button) findViewById(R.id.mat_objectif_caption);
            objectifBtn.setVisibility(View.GONE);
        } else {
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
        }

        // load matière supports
        if (matiere.getSupports().length == 0) {
            Button supportBtn = (Button) findViewById(R.id.mat_support_caption);
            supportBtn.setVisibility(View.GONE);
        } else {
            ViewGroup icon_container = (ViewGroup) findViewById(R.id.mat_support);
            String[] icons = {"doc_papier", "doc_numerique", "powerpoint", "audio", "video", "ordinateur", "web", "moodle", "mathematica", "quartus", "logiciel_r", "ms_project"};
            String[] iconsNoms = {"document papier", "document numérique", "powerpoint", "document audio", "document vidéo", "ordinateur", "support web", "moodle", "mathematica", "quartus", "R (logiciel)", "MS project"};

            for (String support : matiere.getSupports()) {
                int index = Arrays.asList(icons).indexOf(support);
                LayoutInflater supportInflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View supportLayout = supportInflater.inflate(R.layout.support_layout, null);

                ImageView supportIcon = (ImageView) supportLayout.findViewById(R.id.support_icon);
                int iconID = getResources().getIdentifier("ic_" + support, "drawable", getPackageName());
                supportIcon.setImageResource(iconID);

                TextView supCaption = (TextView) supportLayout.findViewById(R.id.support_text);
                supCaption.setText(iconsNoms[index]);

                icon_container.addView(supportLayout);
            }
        }
    }

    // display / close résumé
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

    // display / close contenu
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

    // display / close objectifs
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

    // display / close support
    public void openCloseSupport (View view) {
        TextView captionView = (TextView) findViewById(R.id.mat_support_caption);
        LinearLayout supportView = (LinearLayout) findViewById(R.id.mat_support);
        if (supportView.getVisibility() == View.GONE) {
            supportView.setVisibility(View.VISIBLE);
            Drawable arrow_up = getResources().getDrawable(R.drawable.ic_arrow_up_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_up, null);
        } else {
            supportView.setVisibility(View.GONE);
            Drawable arrow_down = getResources().getDrawable(R.drawable.ic_arrow_down_black_24dp);
            captionView.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow_down, null);
        }
    }
}
