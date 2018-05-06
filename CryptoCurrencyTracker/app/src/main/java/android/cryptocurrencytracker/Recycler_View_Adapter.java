package android.cryptocurrencytracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Recycler_View_Adapter  extends RecyclerView.Adapter<Recycler_View_Adapter.Recycler_View_View_Holder>{

    private Context mCtx;
    private static ClickListener clickListener;
    private List<Recycler_View_Class> recycler_view_classList;

    public Recycler_View_Adapter(Context mCtx, List<Recycler_View_Class> recycler_view_classList) {
        this.mCtx = mCtx;
        this.recycler_view_classList = recycler_view_classList;
    }

    @NonNull
    @Override
    public Recycler_View_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_layout, null);
        Recycler_View_View_Holder holder=new Recycler_View_View_Holder(view);
        return holder;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_View_View_Holder holder, int position) {
        Recycler_View_Class recycler_view_class=recycler_view_classList.get(position);

        holder.text_crypto_name.setText(recycler_view_class.getCryto_name());
        holder.text_symbol.setText(recycler_view_class.getCrypto_symbol());
        holder.text_price.setText(String.valueOf(recycler_view_class.getPrice()));
        holder.text_change1d.setText(String.valueOf(recycler_view_class.getChange1d()));
        /*holder.text_change1w.setText(String.valueOf(recycler_view_class.getChange1w()));
        holder.text_change1m.setText(String.valueOf(recycler_view_class.getChange1m()));
        holder.text_change1y.setText(String.valueOf(recycler_view_class.getChange1y()));*/

        holder.crypto_symbol.setImageDrawable(mCtx.getResources().getDrawable(recycler_view_class.getImage()));
    }

    @Override
    public int getItemCount() {
        return recycler_view_classList.size();
    }

    class Recycler_View_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView crypto_symbol;
        TextView text_crypto_name,text_symbol,text_price, text_change1d, text_change1w, text_change1m ,text_change1y;

        public Recycler_View_View_Holder(View itemView) {
            super(itemView);

            crypto_symbol = itemView.findViewById(R.id.cryptoSymbol);
            text_crypto_name = itemView.findViewById(R.id.txt_crypto_name);
            text_symbol = itemView.findViewById(R.id.txt_symbol);
            text_price = itemView.findViewById(R.id.txt_price);
            text_change1d = itemView.findViewById(R.id.txt_change1d);
            /*text_change1w = itemView.findViewById(R.id.txt_change1w);
            text_change1m = itemView.findViewById(R.id.txt_change1m);
            text_change1y = itemView.findViewById(R.id.txt_change1y);*/

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Recycler_View_Adapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
