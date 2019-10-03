package com.bellatrix.aditi.documentorganizer;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;

/**
 * Created by Aditi on 13-03-2019.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    FolderAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        this.clickLister = listItemClickLister;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_holder, parent, false);

        FolderViewHolder caseViewHolder = new FolderViewHolder(view);
        return caseViewHolder;
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title;
        LinearLayout holder;
        ImageView imageView;
        FolderViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.folder_name);
            holder = (LinearLayout)view.findViewById(R.id.folder_holder);
            imageView= (ImageView)view.findViewById(R.id.icon);
            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null*/

            String splitedName[] =  mCursor.getString(mCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)).split("_");
            String folderName = "";
            for(String s: splitedName) {
                folderName = folderName + " " + s;
            }
            String color = mCursor.getString(mCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_COLOR));
            Log.d("tag", "***********************"+ folderName + "****");
            this.holder.setBackgroundColor(Color.parseColor(color));
            this.title.setText(folderName);
            if(folderName.equals(" Bills and Receipts")){
                imageView.setImageResource(R.drawable.ic_receipt);
            }
            else if(folderName.equals(" Medical records")){
                imageView.setImageResource(R.drawable.ic_health);
            }
            else if(folderName.equals(" Handwritten")){
                imageView.setImageResource(R.drawable.ic_handwritten);
            }
            else if(folderName.equals(" Certificates and Marksheets")){
                imageView.setImageResource(R.drawable.ic_certificates);

            }
            else if(folderName.equals(" Government issued documents")){
                imageView.setImageResource(R.drawable.ic_govt_issued);
            }
            else{
                imageView.setImageResource((R.drawable.ic_custom));
            }

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickLister.onListItemClick(position);
        }
    }
    public void swapCursor(Cursor cursor)
    {
        if(mCursor!=null)
            mCursor.close();

        mCursor = cursor;
        if(mCursor!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}
