package com.projects.jeancarlos.siguemeproject.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 23/09/2015.
 */
public class EliminateRouteDialog extends AlertDialog implements View.OnClickListener {


    private Button dialog_eliminate_btn_cancel, dialog_eliminate_btn_ok;
    private InterfaceEliminateDialog interfaceEliminateDialog;

    public EliminateRouteDialog(Context context) {
        super(context);
        init(context);
    }

    protected EliminateRouteDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected EliminateRouteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_eliminate_route,null);

        dialog_eliminate_btn_cancel = (Button)view.findViewById(R.id.dialog_eliminate_btn_cancel);
        dialog_eliminate_btn_ok = (Button)view.findViewById(R.id.dialog_eliminate_btn_ok);

        setView(view);
        setCancelable(true);

        dialog_eliminate_btn_cancel.setOnClickListener(this);
        dialog_eliminate_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dialog_eliminate_btn_cancel:
                interfaceEliminateDialog.closeDialogEliminate(0);
                break;

            case R.id.dialog_eliminate_btn_ok:
                interfaceEliminateDialog.closeDialogEliminate(1);
                break;
        }
    }

    /**
     * Interface
     */

    public interface InterfaceEliminateDialog{
        void closeDialogEliminate(int i);
    }

    public void setInterfaceEliminateDialog(InterfaceEliminateDialog interfaceEliminateDialog) {
        this.interfaceEliminateDialog = interfaceEliminateDialog;
    }

}
