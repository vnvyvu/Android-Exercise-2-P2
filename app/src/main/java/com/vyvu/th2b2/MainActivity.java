package com.vyvu.th2b2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.vyvu.th2b2.dao.QuyenGopDAO;
import com.vyvu.th2b2.model.QuyenGop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private QuyenGopDAO quyenGopDAO;
    private SearchView sv;
    private TextView tong;
    private MyAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        sv=findViewById(R.id.searchview);
        tong=findViewById(R.id.tongtien);
        quyenGopDAO =new QuyenGopDAO(this, "quyengop");
        rv.setAdapter(adapter=new MyAdapter(this, quyenGopDAO.getAll()));
        rv.setLayoutManager(new LinearLayoutManager(this));
        tong.setText(adapter.getTong()+"");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                rv.setAdapter(adapter=new MyAdapter(MainActivity.this, quyenGopDAO.getAll()));
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addOption:
                Intent i=new Intent(this, QuyenGopAct.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        rv.setAdapter(adapter=new MyAdapter(this, quyenGopDAO.getAll()));
        tong.setText(adapter.getTong()+"");
    }

    public class MyAdapter extends RecyclerView.Adapter implements Filterable{
        private Context c;
        private double tong;
        private ArrayList<QuyenGop> quyenGops;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public MyAdapter(Context c, ArrayList<QuyenGop> quyenGops) {
            this.c = c;
            this.quyenGops = quyenGops;
            tong=0;
            quyenGops.forEach((v)->tong+=v.getPrice());
        }

        public double getTong() {
            return tong;
        }

        public ArrayList<QuyenGop> getQuyenGops() {
            return quyenGops;
        }

        public void setQuyenGops(ArrayList<QuyenGop> quyenGops) {
            this.quyenGops = quyenGops;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(c).inflate(R.layout.r_form, parent, false)) {
              @Override
                public String toString() {
                    return super.toString();
                }
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            QuyenGop p= quyenGops.get(position);
            String[] ts=getResources().getStringArray(R.array.cities);
            ((TextView)holder.itemView.findViewById(R.id.name)).setText(p.getName()+"--"+p.getId());
            ((TextView) holder.itemView.findViewById(R.id.city)).setText(ts[p.getCity()]);
            ((TextView)holder.itemView.findViewById(R.id.date)).setText(p.getDate());
            ((TextView)holder.itemView.findViewById(R.id.price)).setText(p.getPrice()+"");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(c, QuyenGopAct.class);
                    i.putExtra("quyengop", p);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return quyenGops.size();
        }

        @Override
        public Filter getFilter() {
            Filter f=new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();

                    if(charSequence == null | charSequence.length() == 0){
                        filterResults.count = quyenGops.size();
                        filterResults.values = quyenGops;

                    }else{
                        String searchChr = charSequence.toString().toLowerCase();

                        List<QuyenGop> resultData = new ArrayList<>();

                        for(QuyenGop userModel: quyenGops){
                            if(userModel.getName().contains(searchChr)){
                                resultData.add(userModel);
                            }
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;

                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    quyenGops=(ArrayList<QuyenGop>) results.values;
                    notifyDataSetChanged();
                }
            };
            return f;
        }
    }
}