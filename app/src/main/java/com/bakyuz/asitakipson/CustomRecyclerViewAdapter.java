package com.bakyuz.asitakipson;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.MyViewHolder> {

    private List<Asi> asiList;
    private String[] mDataset;

    private OnNoteListener mOnNoteListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView  asiAdi,hastaneAdı,asiTarih;

        OnNoteListener onNoteListener;

        public MyViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);

            this.onNoteListener = onNoteListener;

            asiAdi = v.findViewById(R.id.asiAdi_label);
            hastaneAdı = v.findViewById(R.id.hastahane_label);
            asiTarih = v.findViewById(R.id.tarih_label);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomRecyclerViewAdapter(List<Asi> asiList, OnNoteListener onNoteListener) {
        this.asiList = asiList;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_layout, parent, false);

        return new MyViewHolder(itemView,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Asi asi = asiList.get(position);
        holder. asiAdi.setText(asi.getAsiAdi());
        holder.hastaneAdı.setText(asi.getHastahaneAdi());
        holder.asiTarih.setText(asi.getAsiTarih());
    }

    @Override
    public int getItemCount() {
        return asiList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}