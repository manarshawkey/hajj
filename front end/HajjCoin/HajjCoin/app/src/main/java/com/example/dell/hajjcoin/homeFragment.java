package com.example.dell.hajjcoin;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

class trans{
    String refno;
    String merchantid;
    String amount;
    String atmNo;
    String clientid;
    String type;
    String tr_time;
    String client2phone;
    String storename;
    String client1phone;
    String date;
}
public class homeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FloatingActionButton payFAB;
    private Activity activity;


    private  trans[] transactions;

    private RecyclerView recyclerView;
    private TransAdapter transAdapter;
    private List<trans>  transList;

    public homeFragment() {
    }


    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        transList = new ArrayList<>();
        transAdapter    = new TransAdapter(getActivity(), transList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transAdapter);

        payFAB = view.findViewById(R.id.fab);
        payFAB.setOnClickListener(this);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent i = new Intent(getActivity(), TransActivity.class);
                i.putExtra("amount", transactions[position].amount);
                i.putExtra("type",   transactions[position].type);
                i.putExtra("date",   transactions[position].date);
                i.putExtra("mid",   transactions[position].merchantid);
                startActivity(i);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchTrans();

        return view;
    }

    private void fetchTrans(){
        String userID = "1";

        String url = HelperClass.url_trans + userID;

        HttpGetRequest getRequest = new HttpGetRequest(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String res) {
                Gson gson = new Gson();
                transactions = gson.fromJson(res, trans[].class);

                for(int i=0; i< transactions.length;i++)
                    transList.add(transactions[i]);

                transAdapter.notifyDataSetChanged();

            }

            @Override
            public void UpdateProgress(int flag) {

            }
        });

        getRequest.execute(url);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:

                Intent i = new Intent(getActivity(), ScanActivity.class);
                startActivity(i);
                break;
        }

    }



    class TransAdapter extends RecyclerView.Adapter<TransAdapter.MyViewHolder> {
        private Context context;
        private List<trans> transList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView storeName,transDate,amount;
            public de.hdodenhof.circleimageview.CircleImageView img;

            public MyViewHolder(View view) {
                super(view);
                storeName      = view.findViewById(R.id.contact_name);
                transDate      = view.findViewById(R.id.contact_score);
                img            = view.findViewById(R.id.profile_pic);

               // amount         = view.findViewById(R.id.amount);
            }
        }

        public TransAdapter(Context context, List<trans> transList) {
            this.context  = context;
            this.transList= transList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trans_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position){
            final trans trans = transList.get(position);
            if (trans.type.equals("deposit")) {
                trans.amount = "+" + trans.amount;
                holder.img.setImageResource(R.drawable.ic_deposit);
                holder.storeName.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            else {
                trans.amount = "-" + trans.amount;
                holder.img.setImageResource(R.drawable.ic_withdraw);
                holder.storeName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            transactions[position].amount= trans.amount + " SAR";
            holder.storeName.setText(trans.amount );

            holder.transDate.setText(trans.date);
        }

        @Override
        public int getItemCount() {
            return transList.size();
        }
    }



}
