package com.example.dell.hajjcoin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class logsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private  trans[] transactions ;

    private RecyclerView recyclerView;
    private logsFragment.TransAdapter transAdapter;
    private List<trans>  transList;
    private Toolbar toolbar;

    public logsFragment() {

    }


    public static logsFragment newInstance(String param1, String param2) {
        logsFragment fragment = new logsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logs, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        transList = new ArrayList<>();
        transAdapter    = new logsFragment.TransAdapter(getActivity(), transList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transAdapter);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Transactions History");


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getActivity(), TransActivity.class);
                i.putExtra("type",   transactions[position].type);
                i.putExtra("date",   transactions[position].date);
                i.putExtra("mid",    transactions[position].merchantid);
                i.putExtra("amount", transactions[position].amount);
                startActivity(i);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchLogs();

        return view;
    }

    private void fetchLogs(){

        String userID = "1";
        String url = HelperClass.url_logs + userID;

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

    class TransAdapter extends RecyclerView.Adapter<logsFragment.TransAdapter.MyViewHolder> {
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
            }
        }

        public TransAdapter(Context context, List<trans> transList) {
            this.context = context;
            this.transList= transList;
        }

        @Override
        public logsFragment.TransAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trans_row, parent, false);
            return new logsFragment.TransAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(logsFragment.TransAdapter.MyViewHolder holder, final int position){
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

        }

        @Override
        public int getItemCount() {
            return transList.size();
        }
    }
}
