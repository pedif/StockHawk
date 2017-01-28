package com.udacity.stockhawk.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link HistoryDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryDialogFragment extends DialogFragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String mQuoteId;

    public HistoryDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HistoryDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryDialogFragment newInstance(String param1) {
        HistoryDialogFragment fragment = new HistoryDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuoteId = getArguments().getString(ARG_PARAM1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(Contract.Quote.URI, new String[]{Contract.Quote.COLUMN_HISTORY,
                Contract.Quote.COLUMN_SYMBOL},
                Contract.Quote.COLUMN_SYMBOL + "=?", new String[]{mQuoteId}, null);
        cursor.moveToFirst();


        String history = cursor.getString(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_history_dialog, null);


        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new HistoryAdapter(parseHistory(history)));


        builder.setView(rootView);
        String title = String.format(getResources().getString(R.string.history_dialog_title), cursor.getString(1));
        builder.setTitle(title);
        cursor.close();
        return builder.create();

    }

    /**
     * Generates a two dimension array based on a given history string
     *
     * @param history
     * @return
     */
    private String[][] parseHistory(String history) {

        String[] historyItems = history.split("\n");
        String[][] result = new String[historyItems.length][2];
        for (int i = 0; i < historyItems.length; i++) {
            String h = historyItems[i];
            int commaIndex = h.lastIndexOf(",");
            result[i][0] = h.substring(0, commaIndex);
            result[i][1] = h.substring(commaIndex + 1);
        }
        return result;
    }
}
