package tech.inscripts.ins_armman.mMitra.completedFormList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.completedFormDetails.CompletedFormDetails;
import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.npdsf.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.ViewHolder> {
    Context mContext;
    String language, formName, participant_id;
    int form_id;

    int status = 0;
    String uniqueId, name;
    int pos = 0;
    private ArrayList<CompleteFormQnA> mDetails;
    private ArrayList<CompleteFormQnA> childIdList;
    private ClickListener clickListener;

    public FormListAdapter(ArrayList<CompleteFormQnA> mDetails, ArrayList<CompleteFormQnA> childIdList, Context mContext, String id, int form_id) {
        this.mDetails = mDetails;
        this.childIdList = childIdList;
        this.mContext = mContext;
        this.participant_id = id;
        this.form_id = form_id;


    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_form_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CompleteFormQnA c = mDetails.get(position);
        form_id = mDetails.get(position).getForm_id();
        try {
            JSONObject obj = new JSONObject(c.getFormName());
            language = utility.getLanguagePreferance(mContext);
            if (language.isEmpty()) {
                utility.setApplicationLocale(mContext, "en");
            } else {
                utility.setApplicationLocale(mContext, language);
            }
            formName = obj.getString(this.language);
            formName = formName.toUpperCase();
            int count = 0;


            if (form_id >= 6 && form_id <= 9) {
                String child = childIdList.get(status).getChildNAme();
                holder.formName.setText(formName + " (" + child + ")");
                status = status + 1;
            }


            if (form_id >= 1 && form_id <= 5 || form_id == 10) {
                holder.formName.setText(formName);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        int a = 0;
        while (a < mDetails.size()) {
            System.out.println("formList" + mDetails);
            a++;
        }
    }

    @Override
    public int getItemCount() {
        return mDetails.size();
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView formName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            formName = itemView.findViewById(R.id.tvFormName);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, CompletedFormDetails.class);
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
                int formId = mDetails.get(getPosition()).getForm_id();
                if (formId >= 6 && formId <= 9) {
                    uniqueId = childIdList.get(getPosition() - 5).getUnique_id();
                    System.out.println("child id :" + formId);
                    System.out.println("child id :" + uniqueId);
                } else if (formId >= 1 && formId <= 9 || formId == 10) {
                    uniqueId = participant_id;
                }
                intent.putExtra("unique_id", uniqueId);
                intent.putExtra("form_id", formId);
                mContext.startActivity(intent);
            }
        }
    }
}
