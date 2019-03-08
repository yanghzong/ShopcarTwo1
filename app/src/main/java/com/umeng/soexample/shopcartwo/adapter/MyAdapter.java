package com.umeng.soexample.shopcartwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.shopcartwo.R;
import com.umeng.soexample.shopcartwo.bean.ShopCarBean;
import com.umeng.soexample.shopcartwo.self.AddOrJian;

import java.util.List;

public class MyAdapter extends BaseExpandableListAdapter {
    private List<ShopCarBean.DataBean> sellerData;
    private Context context;
    private CheckBox cbFather;
    private TextView tvFather;
    private AddOrJian myAdd;
    private TextView tvSunPrice;
    private TextView tvSunName;
    private ImageView imgShow;
    private CheckBox cbSun;

    public MyAdapter(List<ShopCarBean.DataBean> sellerData, Context context) {
        this.sellerData = sellerData;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return sellerData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sellerData.get(groupPosition).getList().size();
    }



    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder=null;
        if (convertView==null){
            groupViewHolder=new GroupViewHolder();
            convertView= View.inflate(context,R.layout.item_father,null);
            groupViewHolder. cbFather = convertView.findViewById(R.id.cb_father);
            groupViewHolder. tvFather = convertView.findViewById(R.id.tv_father);

            convertView.setTag(groupViewHolder);

        }else{
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }

        //赋值
        ShopCarBean.DataBean dataBean = sellerData.get(groupPosition);
        //商家的姓名
        groupViewHolder.tvFather.setText(dataBean.getSellerName());

        boolean b = isCurrentSellerAllProductSelected(groupPosition);
        //商家的checkbox
        groupViewHolder.cbFather.setChecked(b);

        groupViewHolder.cbFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCartListChangeListener!=null){
                    onCartListChangeListener.SellerSelectedChange(groupPosition);
                }

            }
        });




        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder=null;
        if (convertView==null){
            childViewHolder=new ChildViewHolder();
            convertView=View.inflate(context,R.layout.item_sun,null);
            childViewHolder. cbSun = convertView.findViewById(R.id.cb_sun);
            childViewHolder. imgShow = convertView.findViewById(R.id.img_show);
            childViewHolder. tvSunName = convertView.findViewById(R.id.tv_sun_name);
            childViewHolder.tvSunPrice = convertView.findViewById(R.id.tv_sunprice);
            childViewHolder. myAdd = convertView.findViewById(R.id.myAdd);

            convertView.setTag(childViewHolder);



        }else{
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        //赋值
        ShopCarBean.DataBean.ListBean listBean = sellerData.get(groupPosition).getList().get(childPosition);

        childViewHolder.tvSunName.setText(listBean.getTitle());
        childViewHolder.tvSunPrice.setText("$"+listBean.getPrice());
        //商品图片
        String images = listBean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(childViewHolder.imgShow);


        childViewHolder.cbSun.setChecked(listBean.getSelected()==1);
        childViewHolder.cbSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartListChangeListener.changeCurrentProductSelected(groupPosition,childPosition);
            }
        });

        childViewHolder.myAdd.setOnNumberChangeListener(new AddOrJian.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int number) {
                onCartListChangeListener.ProductNumberChange(groupPosition,childPosition,number);
            }
        });

        return convertView;
    }


    public void changeCurrentProductNumber(int groupPosition, int childPosition, int number) {
        ShopCarBean.DataBean.ListBean listBean = sellerData.get(groupPosition).getList().get(childPosition);
        listBean.setNum(number);
    }

    public void changeCurrentProductSelected(int groupPosition, int childPosition) {
        ShopCarBean.DataBean.ListBean listBean = sellerData.get(groupPosition).getList().get(childPosition);
        listBean.setSelected(listBean.getSelected() == 0 ? 1 : 0);


    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < sellerData.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> list = sellerData.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                //只有选中采取计算
                if (list.get(j).getSelected() == 1) {
                    double price = list.get(j).getPrice();
                    int num = list.get(j).getNum();
                    totalPrice += price * num;
                }

            }
        }
        return totalPrice;
    }

    public int calculateTotalNumber() {
        int totalNumber = 0;
        for (int i = 0; i < sellerData.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> list = sellerData.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                //只有选中采取计算
                if (list.get(j).getSelected() == 1) {
                    int num = list.get(j).getNum();
                    totalNumber += num;
                }

            }
        }
        return totalNumber;
    }



    public boolean isAllProductsSelected() {
        for (int i = 0; i < sellerData.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> list = sellerData.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void changeAllProductsSelected(boolean b) {
        for (int i = 0; i < sellerData.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> list = sellerData.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setSelected(b ? 1 : 0);
            }
        }

    }



    private class GroupViewHolder {
        public CheckBox cbFather;
        public TextView tvFather;
    }



    private class ChildViewHolder {
        public AddOrJian myAdd;
        public TextView tvSunPrice;
        public TextView tvSunName;
        public CheckBox cbSun;
        public ImageView imgShow;
    }


    public boolean isCurrentSellerAllProductSelected(int position) {
        List<ShopCarBean.DataBean.ListBean> list = sellerData.get(position).getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    public void changeCurrentSellerAllProductSelected(int Position, boolean b) {
        List<ShopCarBean.DataBean.ListBean> list = sellerData.get(Position).getList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(b ? 1 : 0);

        }

    }


    //接口回调

    OnCartListChangeListener onCartListChangeListener;

    public void setOnCartListChangeListener(OnCartListChangeListener onCartListChangeListener) {
        this.onCartListChangeListener = onCartListChangeListener;
    }

    public interface OnCartListChangeListener {
        void SellerSelectedChange(int groupPosition);

        void changeCurrentProductSelected(int groupPosition, int childPosition);

        void ProductNumberChange(int groupPosition, int childPosition, int number);
    }



































    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
