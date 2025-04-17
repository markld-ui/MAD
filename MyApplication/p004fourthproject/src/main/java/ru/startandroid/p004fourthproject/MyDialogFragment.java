package ru.startandroid.p004fourthproject;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class MyDialogFragment extends DialogFragment
{
    private static final String AGE_MESSAGE = "message";

    public static MyDialogFragment newInstance(String message)
    {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(AGE_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        String message = getArguments() != null ? getArguments().getString(AGE_MESSAGE) :
                "Ошибка!";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Результат конвертации данных")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create();
    }
}