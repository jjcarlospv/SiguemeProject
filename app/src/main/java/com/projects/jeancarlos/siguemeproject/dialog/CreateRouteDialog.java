package com.projects.jeancarlos.siguemeproject.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class CreateRouteDialog extends AlertDialog implements View.OnClickListener {

    private InterfaceDialogRoute interfaceDialogRoute;
    private Button dialog_create_btn_cancel, dialog_create_btn_ok;
    private TextView dialog_create_route_name, dialog_create_route_description;

    public CreateRouteDialog(Context context) {
        super(context);
        init(context);
    }

    protected CreateRouteDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected CreateRouteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_create_route,null);

        dialog_create_btn_cancel = (Button)view.findViewById(R.id.dialog_create_btn_cancel);
        dialog_create_btn_ok = (Button)view.findViewById(R.id.dialog_create_btn_ok);

        dialog_create_route_name = (TextView)view.findViewById(R.id.dialog_create_route_name);
        dialog_create_route_description = (TextView)view.findViewById(R.id.dialog_create_route_description);

        setView(view);
        setCancelable(true);

        dialog_create_btn_cancel.setOnClickListener(this);
        dialog_create_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dialog_create_btn_cancel:
                interfaceDialogRoute.closeDialogRoute(0,"","");
                break;

            case R.id.dialog_create_btn_ok:

                if(dialog_create_route_name.getText().toString().length()> 2)
                {
                    interfaceDialogRoute.closeDialogRoute(1,dialog_create_route_name.getText().toString(),dialog_create_route_description.getText().toString());}

                else{
                    Toast.makeText(getContext(), R.string.dialog_create_route_type_name, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public interface InterfaceDialogRoute{
        void closeDialogRoute(int i, String nameRoute, String description);

    }

    public void setInterfaceDialogRoute(InterfaceDialogRoute interfaceDialogRoute){
        this.interfaceDialogRoute = interfaceDialogRoute;
    }
}

