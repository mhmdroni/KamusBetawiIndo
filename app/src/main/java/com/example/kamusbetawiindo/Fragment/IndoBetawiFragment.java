package com.example.kamusbetawiindo.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kamusbetawiindo.Adapter.KamusBetawiAdapter;
import com.example.kamusbetawiindo.Algoritma.BoyerMoore;
import com.example.kamusbetawiindo.Algoritma.Horspool;
import com.example.kamusbetawiindo.DB.DBHelper;
import com.example.kamusbetawiindo.Model.Kamus;
import com.example.kamusbetawiindo.R;
import com.example.kamusbetawiindo.Utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class IndoBetawiFragment extends Fragment {
    DBHelper dbHelper;
    EditText etHasil, etAddText, etWaktu, etAlgoritma;
    int failure[];
    RecyclerView recyclerViewKamus;
    KamusBetawiAdapter adapter;

    LinearLayout layHasilTranslate;
    LinearLayout layHint;

    List<Kamus> kamusList;
    List<Kamus> kamusListTemp = new ArrayList<>();

    int pilih_algo = 1;

    String pattern = "";
    String hasil = "";

    public IndoBetawiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        etAddText = (EditText) v.findViewById(R.id.et_add_text);
        etHasil = (EditText) v.findViewById(R.id.et_hasil_translate);
        etAlgoritma = (EditText) v.findViewById(R.id.et_algoritma);
        etWaktu = (EditText) v.findViewById(R.id.et_waktu);
        Button btnTranslate = (Button) v.findViewById(R.id.btn_translate);
        final Button btnOcr = (Button) v.findViewById(R.id.btn_ocr);
        recyclerViewKamus = (RecyclerView) v.findViewById(R.id.rvKamus);

        layHasilTranslate = (LinearLayout) v.findViewById(R.id.lay_hasil_translate);
        layHint = (LinearLayout) v.findViewById(R.id.lay_hint);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewKamus.setLayoutManager(llm);
        kamusList = dbHelper.getAllIndo();
        kamusListTemp = new ArrayList<>();

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pilih_algo == 1) {
                    prosesBoyerMoore();
                } else if (pilih_algo == 2) {
                    prosesHorspool();
                }

            }
        });

        btnOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //private void askOCrMethod
            }
        });

        final String[] algoritma = getActivity().getResources().getStringArray(R.array.algoritma);
        etAlgoritma.setText(algoritma[0]);

        etAlgoritma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommonUtil.dialogArray(getActivity(), algoritma, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etAlgoritma.setText(algoritma[i]);

                        if (i == 0) {
                            pilih_algo = 1;
                        } else if (i == 1) {
                            pilih_algo = 2;
                        }
                    }
                });
            }
        });
        return v;

    }

    private void prosesHorspool() {
        pattern = etAddText.getText().toString().toLowerCase();
//        hasil = getIndonesia(pattern);
        kamusListTemp.clear();

        Horspool hp = new Horspool();

        for (int i = 0; i < kamusList.size(); i++) {
            int starttime = (int) System.nanoTime();
            int searchIndex = hp.horspool(kamusList.get(i).getBetawi(), pattern);
            int endtime = (int) System.nanoTime() - starttime;

            float second = endtime / 1000F;
            etWaktu.setText(second + " Milliseconds");

            Log.d("HOOSRPOl", ": INDEX " + searchIndex);

            if (searchIndex != -1) {
                kamusListTemp.add(kamusList.get(i));
            }
        }

        prosesView();
    }

    private void prosesBoyerMoore() {
        pattern = etAddText.getText().toString().toLowerCase();

        kamusListTemp.clear();

        BoyerMoore bm = new BoyerMoore();

        for (int i = 0; i < kamusList.size(); i++){
            int starttime = (int) System.nanoTime();
            int searchIndex = bm.findPattern(kamusList.get(i).getBetawi(), pattern);
            int endtime = (int) System.nanoTime() - starttime;

            float second = endtime / 1000F;
            etWaktu.setText(second + " Milliseconds");

            Log.d("BOOYER MOORE", ": Ada" + searchIndex);

            if (searchIndex != -1) {
                kamusListTemp.add(kamusList.get(i));
            }
        }
        prosesView();
    }

    private void prosesView() {
        boolean matchString = false;
        adapter = new KamusBetawiAdapter(kamusListTemp, getActivity());
        Log.d("KAMUS HASIL", "prosesView: " + kamusListTemp.size());
        if (kamusListTemp.size() > 0) {
            for (int i = 0; i < kamusListTemp.size(); i++) {
                if (pattern.equals(kamusListTemp.get(i).getIndo())) {
                    etHasil.setText(kamusListTemp.get(i).getBetawi().toUpperCase());
                    etHasil.setTextColor(Color.BLUE);
                    matchString = true;
                    Log.d("True", kamusListTemp.get(i).getIndo());
                } else if (pattern.contains(kamusListTemp.get(i).getIndo())) {
                    Log.d("Mungkin Maksud Anda", kamusListTemp.get(i).getBetawi());
                    matchString = false;
                }
            }
            if (matchString == true) {
                layHasilTranslate.setVisibility(View.VISIBLE);
                layHint.setVisibility(View.GONE);
            } else if (matchString == false) {
                recyclerViewKamus.setAdapter(adapter);
                layHint.setVisibility(View.VISIBLE);
                layHasilTranslate.setVisibility(View.GONE);
            }
        } else if (kamusListTemp.size() == 0) {
            layHasilTranslate.setVisibility(View.VISIBLE);
            etHasil.setText("Data Tidak Di Temukan".toUpperCase());
            etHasil.setTextColor(Color.RED);
            layHint.setVisibility(View.GONE);
        }

        if (hasil == null) {
            etHasil.setTextColor(Color.RED);
            etHasil.setText("Data tidak Ditemukan");
        } else {
            etHasil.setTextColor(Color.BLUE);
            etHasil.setText(getBetawi(pattern));
        }
    }

    private String getBetawi(String addText) {
        Kamus kamus = dbHelper.getIndo(addText);
        return kamus.getBetawi();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etAddText.setText(result.get(0));
                }
                break;
            }

        }

    }

    public int kmp(String text, String pat) {
        /** pre construct failure array for a pattern **/
        failure = new int[pat.length()];
        fail(pat);
        /** find match **/
        int pos = posMatch(text, pat);
        if (pos == -1)
            Log.d("Hasil", "\nNo match found");
        else
            Log.d("Hasil", "\nMatch found at index " + pos);

        return pos;
    }

    private void fail(String pat) {
        int n = pat.length();
        failure[0] = -1;
        for (int j = 1; j < n; j++) {
            int i = failure[j - 1];
            while ((pat.charAt(j) != pat.charAt(i + 1)) && i >= 0)
                i = failure[i];
            if (pat.charAt(j) == pat.charAt(i + 1))
                failure[j] = i + 1;
            else
                failure[j] = -1;
        }
    }

    /**
     * Function to find match for a pattern
     **/
    private int posMatch(String text, String pat) {
        int i = 0, j = 0;
        int lens = text.length();
        int lenp = pat.length();
        while (i < lens && j < lenp) {
            if (text.charAt(i) == pat.charAt(j)) {
                i++;
                j++;
            } else if (j == 0)
                i++;
            else
                j = failure[j - 1] + 1;
        }
        return ((j == lenp) ? (i - lenp) : -1);
    }
}
