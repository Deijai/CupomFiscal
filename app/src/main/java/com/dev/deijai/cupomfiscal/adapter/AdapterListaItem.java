package com.dev.deijai.cupomfiscal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.deijai.cupomfiscal.R;
import com.dev.deijai.cupomfiscal.model.CupomFiscal;

import java.util.List;

public class AdapterListaItem extends RecyclerView.Adapter<AdapterListaItem.MyViewHolder> {

    private List<CupomFiscal> cupomFiscalList;

    public AdapterListaItem(List<CupomFiscal> cupomFiscalList) {
        this.cupomFiscalList = cupomFiscalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterListaItem = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_lista_item,
                parent,
                false
        );
        return new MyViewHolder(adapterListaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CupomFiscal cupomFiscal = cupomFiscalList.get(position);
        //Log.i("Res cupom", cupomFiscal.toString());

        holder.codFilial.setText("FILIAL: "+cupomFiscal.getCodfilial());
        holder.numSeq.setText("QT ITEM: "+cupomFiscal.getNumseq()+" "+cupomFiscal.getUnidade().toUpperCase());
        holder.numCupom.setText("N° CUPOM: "+cupomFiscal.getNumnota());
        holder.numCaixa.setText("N° CAIXA: ."+cupomFiscal.getNumcaixa());
        holder.descricaoItem.setText("DESCRIÇÃO: "+cupomFiscal.getDescricaopaf());
        holder.codauxiliar.setText("CODAUX: "+cupomFiscal.getCodauxiliar());


    }

    @Override
    public int getItemCount() {
        if(cupomFiscalList.size() > 0 ){
            return cupomFiscalList.size();
        } else {
            return 0;
        }

    }

    //Classe interna View Hoder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //aqui poderia ser varios tipos de dados (Ex: ImageView etc...)
        TextView codFilial;
        TextView numSeq;
        TextView numCupom;
        TextView numCaixa;
        TextView descricaoItem;
        TextView codauxiliar;
        TextView unidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            codFilial = itemView.findViewById(R.id.codfilialId);
            numSeq = itemView.findViewById(R.id.numSeqId);
            numCupom = itemView.findViewById(R.id.numCupomId);
            numCaixa = itemView.findViewById(R.id.numCaixaId);
            descricaoItem = itemView.findViewById(R.id.descricaoItemId);
            codauxiliar = itemView.findViewById(R.id.codauxiliarId);
            unidade = itemView.findViewById(R.id.unidadeId);

        }
    }


}
