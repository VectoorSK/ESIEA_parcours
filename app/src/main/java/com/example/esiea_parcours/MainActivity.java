package com.example.esiea_parcours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
                showCursus(datalist.get(0));
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

    private void showCursus (final Annee annee) {
        for (int j = 1; j <= 5 ; j++) {
            int btnID = getResources().getIdentifier("btn_" + j + "a", "id", getPackageName());
            CardView btn = (CardView) findViewById(btnID);
            btn.setCardBackgroundColor(0xFF0085C6);
            ((TextView)((ViewGroup) btn).getChildAt(0)).setTextColor(0xFFFFFFFF);
        }
        int btnID = getResources().getIdentifier("btn_" + annee.getAnnee() + "a", "id", getPackageName());
        CardView btn = (CardView) findViewById(btnID);
        btn.setCardBackgroundColor(0xFFFFFFFF);
        ((TextView)((ViewGroup) btn).getChildAt(0)).setTextColor(0xFF0085C6);

        int idTitle = getResources().getIdentifier("title_1a", "id", getPackageName());
        TextView title = (TextView) findViewById(idTitle);
        if (annee.getAnnee() == 1) {
            title.setText("1ère Année");
        } else {
            title.setText(annee.getAnnee() + "ème Année");
        }

        int containerID = getResources().getIdentifier("card_1a", "id", getPackageName());
        ViewGroup container = (ViewGroup) findViewById(containerID);

        for (int j = container.getChildCount()-1; j > 0; j--) {
           container.removeViewAt(j);
        }
        int i = 0;
        for (final Bloc bloc : annee.getBlocs()) {
            i++;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.bloc_layout, null);
            TextView layout = (TextView) view.findViewById(R.id.bloc_titre);
            layout.setText(bloc.getNom());

            container.addView(view, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (final Matiere matiere : bloc.getMatieres()) {
                LayoutInflater layoutInflater2 = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View view2 = layoutInflater2.inflate(R.layout.matiere_layout, null);
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewIntent = new Intent(MainActivity.this, MatiereDetailActivity.class);
                        viewIntent.putExtra("annee", annee.getAnnee());
                        viewIntent.putExtra("bloc", bloc.getNom());
                        viewIntent.putExtra("nom", matiere.getNom());
                        viewIntent.putExtra("semestre", matiere.getSemestre());
                        startActivity(viewIntent);
                    }
                });

                if (matiere.getSemestre() == 1) {
                    view2.setBackgroundColor(0xffffffff);
                } else {
                    view2.setBackgroundColor(0xffdddddd);
                }

                TextView name = (TextView) view2.findViewById(R.id.titleM);
                name.setText("• " + matiere.getNom());
                TextView coeff = (TextView) view2.findViewById(R.id.coeffM);
                if (matiere.getCoeff() == Math.floor(matiere.getCoeff())) {
                    coeff.setText(String.format("%.0f", matiere.getCoeff()));
                } else {
                    coeff.setText(Double.toString(matiere.getCoeff()));
                }
                ((ViewGroup)((ViewGroup)((ViewGroup) container.getChildAt(i)).getChildAt(0)).getChildAt(0)).addView(view2, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    public void loadYear1(View view) {
        showCursus(datalist.get(0));
    }
    public void loadYear2(View view) {
        showCursus(datalist.get(1));
    }
    public void loadYear3(View view) {
        showCursus(datalist.get(2));
    }
    public void loadYear4(View view) {
        showCursus(datalist.get(3));
    }
    public void loadYear5(View view) {
        showCursus(datalist.get(4));
    }
}
