package tech.inscripts.ins_armman.mMitra.completedFormDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.npdsf.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    Context mContext;
    String question, answer, language;
    int formId = 0;
    private ArrayList<CompleteFormQnA> mDetails;

    public DetailsAdapter(ArrayList<CompleteFormQnA> mDetails, Context mContext, int form_id) {
        this.mDetails = mDetails;
        this.mContext = mContext;
        this.formId = form_id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompleteFormQnA c = mDetails.get(position);
        answer = c.getAnswer();
        question=c.getQuestion();
        if ((answer.contains("{") && question.contains("{")) ) {
            try {
                JSONObject obj = new JSONObject(c.getQuestion());
                JSONObject obj1 = new JSONObject(c.getAnswer());
                language = utility.getLanguagePreferance(mContext);
        if (language.isEmpty()) {
                    utility.setApplicationLocale(mContext, "en");
                } else {
                    utility.setApplicationLocale(mContext, language);
                }
                question = obj.getString(this.language);
            answer = obj1.getString(this.language);
                holder.txtQuestion.setText(question);
                holder.txtAnswer.setText(answer);
                System.out.println("settext question :" + question);
                System.out.println("settext answer :" + answer);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(question.contains("{") && !answer.contains("{") ){
            try {
                JSONObject objQue = new JSONObject(c.getQuestion());
                language = utility.getLanguagePreferance(mContext);
                if (language.isEmpty()) {
                    utility.setApplicationLocale(mContext, "en");
                } else {
                    utility.setApplicationLocale(mContext, language);
                }
                question = objQue.getString(this.language);
                holder.txtQuestion.setText(question);
                holder.txtAnswer.setText(answer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(!question.contains("{") && answer.contains("{") ) {
            try {
                JSONObject objAns = new JSONObject(c.getAnswer());
                language = utility.getLanguagePreferance(mContext);
                if (language.isEmpty()) {
                    utility.setApplicationLocale(mContext, "en");
                } else {
                    utility.setApplicationLocale(mContext, language);
                }
                answer = objAns.getString(this.language);
                holder.txtQuestion.setText(question);
                holder.txtAnswer.setText(answer);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {
            holder.txtQuestion.setText(question);
            holder.txtAnswer.setText(answer);
        }
    }

    @Override
    public int getItemCount() {
        return mDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.tvQuestion);
            txtAnswer = itemView.findViewById(R.id.tvAnswer);
        }
    }
}
