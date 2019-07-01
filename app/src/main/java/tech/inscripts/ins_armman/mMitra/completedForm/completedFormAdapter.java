package tech.inscripts.ins_armman.mMitra.completedForm;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.completedFormList.CompletedFormsList;
import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.List;


public class completedFormAdapter extends RecyclerView.Adapter<completedFormAdapter.ViewHolder> {

    private Context mContext;
    private List<completeFiledForm> mWomenList;
    private ClickListener clickListener;

    public completedFormAdapter(Context mContext, List<completeFiledForm> womenList) {
        this.mContext = mContext;
        this.mWomenList = womenList;

    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_incomplete_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.bindData(mWomenList.get(i));
    }

    @Override
    public int getItemCount() {
        return mWomenList.size();
    }

    public void swapDataList(List<completeFiledForm> womenList) {
        this.mWomenList = womenList;
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;
        ConstraintLayout constraintLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.textview_name);
            constraintLayout = itemView.findViewById(R.id.constraint_layout_root);
        }

        private void bindData(final completeFiledForm listModel) {
            if (listModel != null) {
                textViewName.setText(listModel.getName());
            }
        }

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(mContext, CompletedFormsList.class);
                if (clickListener != null) {
                    clickListener.itemClicked(v,getPosition());
                    int i = mWomenList.size();
                intent.putExtra("id",mWomenList.get(getPosition()).getUnique_id());
                intent.putExtra("name",mWomenList.get(getPosition()).getName());
                }
                mContext.startActivity(intent);
            }
    }
}
