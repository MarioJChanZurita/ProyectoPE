package com.example.medicinereminder.MostrarAlarma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;

import java.util.List;

public class MostrarAdaptador extends RecyclerView.Adapter<MostrarAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, notas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.nombreMedicina);
            notas = (TextView)itemView.findViewById(R.id.notasMedicina);
        }
    }

    public List<MostrarModelo> alarmaLista;

    public MostrarAdaptador(List<MostrarModelo> alarmaLista) {this.alarmaLista = alarmaLista;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarma,parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(alarmaLista.get(position).getNombre());
        holder.notas.setText(alarmaLista.get(position).getNotas());
    }

    @Override
    public int getItemCount() {
        return alarmaLista.size();
    }
}
