package rashakacom.rashaka.utils.helpers.views.passw;

/**
 * Created by User on 20.07.2017.
 */

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import rashakacom.rashaka.utils.Support;

import static rashakacom.rashaka.utils.Support.passwordOk;

public class PasswordWatcherAdapter implements TextWatcher {

    public interface TextWatcherListener {

        void onTextChanged(EditText view, String text);

    }

    private final EditText view;
    private final TextWatcherListener listener;

    public PasswordWatcherAdapter(EditText editText, TextWatcherListener listener) {
        this.view = editText;
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(!passwordOk(s.toString())){
            Support.setRedOutline(view);
            view.setTextColor(Color.RED);
        } else {
            Support.setWhiteOutline(view);
            view.setTextColor(Color.WHITE);
        }

        listener.onTextChanged(view, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // pass
    }

    @Override
    public void afterTextChanged(Editable s) {
        // pass
    }

}