package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.usermgmt.response.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.FirebaseDatabaseHelper.list;
import static com.example.user.ncpaidemo.SelectBaseActivity.baseIntent;

public class BaseInfoAdapter {


    private Context mContext;
    private BaseAdapter mBaseAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<BaseInfo> baseInfo, List<String> keys){


        mContext = context;
        mBaseAdapter = new BaseAdapter(baseInfo, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBaseAdapter);
        int sum=0;

        for(int i =0; i<list.size(); i++){
            for(int j=0; j<list.get(i).size(); j++) {
                System.out.println("i는"+i+"j는"+j+list.get(i).get(j).getsCategory()+"\n");
            }
                sum +=list.get(i).size();
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sum);


    }

    class BaseInfoItemView extends RecyclerView.ViewHolder {
        private TextView lCategory;
        private TextView sCategory;
        private ImageView selected;

        private String key;

        public BaseInfoItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.content_select_base_list, parent, false));

            lCategory = (TextView) itemView.findViewById(R.id.base_lCategory);
            sCategory = (TextView) itemView.findViewById(R.id.base_sCategory);
            selected = itemView.findViewById(R.id.base_select);
        }

        public void bind(BaseInfo baseInfo, String key) {
            lCategory.setText(baseInfo.getlCategory());
            sCategory.setText(baseInfo.getsCategory());
            this.key = key;
        }
    }

    class BaseAdapter extends RecyclerView.Adapter<BaseInfoItemView>{
        private List<BaseInfo> mBaseList;
        private List<String> mKeys;

        public BaseAdapter (List<BaseInfo> mBaseList, List<String> mKeys){
            this.mBaseList = mBaseList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BaseInfoItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseInfoItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseInfoItemView holder, int position) {
            holder.bind(mBaseList.get(position),mKeys.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageView selected = holder.itemView.findViewById(R.id.base_select);
                    selected.setVisibility(View.VISIBLE);

                    System.out.println(position);

                    baseIntent = new Intent(v.getContext(),OcrInActivity.class);
                    baseIntent.putExtra("lCategory", mBaseList.get(position).getlCategory());
                    baseIntent.putExtra("sCategory", mBaseList.get(position).getsCategory());
                    baseIntent.putExtra("nDay", mBaseList.get(position).getnDay());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBaseList.size();
        }
    }
}