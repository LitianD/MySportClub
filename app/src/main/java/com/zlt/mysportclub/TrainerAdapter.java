package com.zlt.mysportclub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlt.mysportclub.model.Trainer;

import java.util.List;

public class TrainerAdapter extends ArrayAdapter {

    private final int resourceId;

    public TrainerAdapter(Context context, int textViewResourceId, List<Trainer> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trainer trainer = (Trainer) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        //ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);//获取该布局内的图片视图
        TextView name = (TextView) view.findViewById(R.id.name);//获取该布局内的文本视图
        TextView phone = (TextView) view.findViewById(R.id.phone);//获取该布局内的文本视图
        TextView course = (TextView) view.findViewById(R.id.course);
        TextView pos = (TextView) view.findViewById(R.id.position);
        ImageView imageView = view.findViewById(R.id.image);
        //fruitImage.setImageResource(trainer.getImageId());//为图片视图设置图片资源
        name.setText(trainer.name);//为文本视图设置文本内容
        phone.setText(trainer.phone);
        course.setText(trainer.course);
        pos.setText(trainer.position);
        imageView.setImageResource(R.drawable.ic_fifth);


        return view;
    }

}
