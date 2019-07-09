package com.kesbokar.kesbokar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterInboxBusiness extends BaseAdapter {
    private final ArrayList<InboxBusinessList> inboxBusinessLists;
    LayoutInflater layoutInflater;
    TextView txtSno,txtTitle,txtAbn,txtPhone,txtStatus;
    Button adpBtnEdt,adptBtnDel;
    Context context;
    public AdapterInboxBusiness(Context context, ArrayList<InboxBusinessList> inboxBusinessLists){
        this.context = context;
        this.inboxBusinessLists = inboxBusinessLists;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return inboxBusinessLists.size();
    }

    @Override
    public Object getItem(int i) {
        return inboxBusinessLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.adapter_inbox_business,null);
        txtSno = view.findViewById(R.id.adapTxtSno);
        txtTitle = view.findViewById(R.id.adapTxtTitle);
        txtAbn = view.findViewById(R.id.adapTxtABN);
        txtPhone = view.findViewById(R.id.adapTxtPhone);
        txtStatus = view.findViewById(R.id.adapTxtStatus);
        adpBtnEdt=view.findViewById(R.id.adapBtnEdt);
        adpBtnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,InboxReplyBusiness.class);
                context.startActivity(intent);
            }
        });
        adptBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue= Volley.newRequestQueue(context);
                String url;

                url="http://serv.kesbokar.com.au/jil.0.1/v1/quotes-product/delete";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error",error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String >();
                        params.put("api_token","FSMNrrMCrXp2zbym9cun7phBi3n2gs924aYCMDEkFoz17XovFHhIcZZfCCdK");
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
        txtSno.setText(inboxBusinessLists.get(i).getTxtSno());
        txtTitle.setText(inboxBusinessLists.get(i).getTxtName());
        txtPhone.setText(inboxBusinessLists.get(i).getTxtMessage());
        txtAbn.setText(inboxBusinessLists.get(i).getTxtBusiness());
        txtStatus.setText(inboxBusinessLists.get(i).getTxtDate());
        return view;
    }
}
