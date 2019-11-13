package com.gusalnim.myfirstapp.demo.adapter.sqlite;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gusalnim.myfirstapp.demo.net.dao.Person;

import java.util.List;

/**
 * Created by gusalnim on 12/11/2019.
 */
public class PersonListAdapter extends BaseAdapter {

    private List people;
    private Context context;


    /*
    * 생성자
    * @param people : Person List
    * @param context
    * */

    public PersonListAdapter(List people, Context context) {
        this.people = people;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.people.size();
    }

    @Override
    public Object getItem(int position) {
        return this.people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;

        if( convertView == null ) {
            convertView = new LinearLayout(context);
            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);

            TextView tvId = new TextView(context);
            tvId.setPadding(10,0,20,0);
            tvId.setTextColor(Color.rgb(0, 0, 0));

            TextView tvName = new TextView(context);
            tvName.setPadding(20,0,20,0);
            tvName.setTextColor(Color.rgb(0, 0, 0));

            TextView tvAge = new TextView(context);
            tvAge.setPadding(20,0,20,0);
            tvAge.setTextColor(Color.rgb(0, 0, 0));

            TextView tvPhone = new TextView(context);
            tvPhone.setPadding(20,0,20,0);
            tvPhone.setTextColor(Color.rgb(0, 0, 0));


            ((LinearLayout) convertView).addView(tvId);
            ((LinearLayout) convertView).addView(tvName);
            ((LinearLayout) convertView).addView(tvAge);
            ((LinearLayout) convertView).addView(tvPhone);

            holder = new Holder();
            holder.tvId = tvId;
            holder.tvName = tvName;
            holder.tvAge = tvAge;
            holder.tvPhone = tvPhone;

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Person person = (Person) getItem(position);
        holder.tvId.setText(person.get_id()+"");
        holder.tvName.setText(person.getName()+"");
        holder.tvAge.setText(person.getAge()+"");
        holder.tvPhone.setText(person.getPhone());
        return convertView;
    }


    /*
    * 홀더
    * */

    private class Holder {
        public TextView tvId;
        public TextView tvName;
        public TextView tvAge;
        public TextView tvPhone;
    }
}
