package com.example.yzubritskiy.loadersresearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yzubritskiy.loadersresearch.R;
import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yzubritskiy on 5/15/2017.
 */

public class OwnersAdatper extends RecyclerView.Adapter<OwnersAdatper.MyViewHolder>{
    private static final String TAG = "TAG_" + OwnersAdatper.class.getSimpleName();
    private List<Owner> mOwners;

    private SimpleDateFormat mDateFormat;
    public OwnersAdatper(){
        mOwners = new ArrayList<>();
        mDateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.US);
    }

    public void fillData(List<Owner> owners){
        mOwners.clear();
        mOwners.addAll(owners);
        notifyDataSetChanged();
    }

    public List<Owner> getData(){
        return mOwners;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owners_item_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mOwners.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mOwners.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = "TAG_" + MyViewHolder.class.getSimpleName();
        public TextView mOwnersFirstName, mOwnersSecondName, mOwnersBirthDate, mCarsContent;
        private LinearLayout mItemContainer;

        public MyViewHolder(View view) {
            super(view);
            mOwnersFirstName = (TextView) view.findViewById(R.id.owner_first_name);
            mCarsContent = (TextView) view.findViewById(R.id.cars_content);
            mOwnersSecondName = (TextView) view.findViewById(R.id.owner_second_name);
            mOwnersBirthDate = (TextView) view.findViewById(R.id.owners_birth_date);
            mItemContainer = (LinearLayout) view.findViewById(R.id.item_container);
        }

        public void bind(Owner owner, int position){
            mOwnersFirstName.setText(owner.getFirstName());
            mOwnersSecondName.setText((" "+owner.getSecondName()));
            String birthDate = mDateFormat.format(owner.getBirthDate());
            mOwnersBirthDate.setText(birthDate);
            StringBuilder carBuilder;
            if(owner.getCars()!=null){
                carBuilder = new StringBuilder();
                for (Car car:owner.getCars()){
                    Log.d(TAG, "getOwnersCars car.getModel()->"+car.getModel()+", car.getNumber()->"+car.getNumber()+", car.getYear->"+car.getYear());
                    carBuilder.append("model: ");
                    carBuilder.append(car.getModel());
                    carBuilder.append(", number: ");
                    carBuilder.append(car.getNumber());
                    carBuilder.append(", year: ");
                    carBuilder.append(car.getYear()+"\n");
                }
                Log.d(TAG, "carBuilder->"+carBuilder);
                mCarsContent.setText(carBuilder.toString());

            }

            if(position%2 == 0){
                mItemContainer.setBackgroundColor(itemView.getResources().getColor(R.color.colorGreyTransparent));
            }else {
                mItemContainer.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimaryTransparent));
            }

        }
    }
}
