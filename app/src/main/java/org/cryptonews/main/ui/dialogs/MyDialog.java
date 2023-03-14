package org.cryptonews.main.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.cryptonews.main.databinding.DialogBinding;

public class MyDialog extends DialogFragment {

    ClickListener listener;

    public MyDialog(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void click();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(requireContext());
        DialogBinding binding = DialogBinding.inflate(getLayoutInflater(),null,false);
        binding.button2.setOnClickListener(view -> listener.click());
        adb = adb.setView(binding.getRoot());
        adb = adb.setCancelable(false);

        return adb.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        requireActivity().finish();
        super.onDismiss(dialog);
    }
}
