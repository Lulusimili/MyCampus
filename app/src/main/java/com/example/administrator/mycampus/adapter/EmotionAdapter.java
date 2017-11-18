package com.example.administrator.mycampus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.mycampus.R;

/**
 * Created by luguanchen on 2017/6/1.
 */
public class EmotionAdapter extends BaseAdapter {

      private int[] myExpression = {
      R.drawable.aaa, R.drawable.aab,R.drawable.aac,R.drawable.aad,
      R.drawable.aae, R.drawable.aaf, R.drawable.aag,R.drawable.aah, R.drawable.aai,R.drawable.aaj,
      R.drawable.aak,R.drawable.aal,R.drawable.aam, R.drawable.aan,R.drawable.aao, R.drawable.aap,
     R.drawable.aaq, R.drawable.aar, R.drawable.aas, R.drawable.aat, R.drawable.aau,R.drawable.aav,
     R.drawable.aaw,R.drawable.aax,R.drawable.aay, R.drawable.aaz, R.drawable.aba,R.drawable.abb,
     R.drawable.abc, R.drawable.abd, R.drawable.abe, R.drawable.abf,R.drawable.abg,R.drawable.abh,
     R.drawable.abi, R.drawable.abj,R.drawable.abk, R.drawable.abl, R.drawable.abm, R.drawable.abn,  R.drawable.abo,
     R.drawable.abq,R.drawable.abr,R.drawable.abs, R.drawable.abt, R.drawable.abu, R.drawable.abv,//没有abp
     R.drawable.abw, R.drawable.abx,R.drawable.aby, R.drawable.abz, R.drawable.baa, R.drawable.bab,
    R.drawable.bac,R.drawable.bad,R.drawable.bae, R.drawable.baf, R.drawable.bag,R.drawable.bah,
    R.drawable.bai, R.drawable.baj, R.drawable.bak, R.drawable.bal,R.drawable.bam,R.drawable.ban,
    R.drawable.bao, R.drawable.bap, R.drawable.baq, R.drawable.bar, R.drawable.bas,  R.drawable.bat,
    R.drawable.bau,R.drawable.bav,R.drawable.baw,R.drawable.bax, R.drawable.bay, R.drawable.baz,
    R.drawable.bba, R.drawable.bbc, R.drawable.bbd,R.drawable.bbe, R.drawable.bbf, R.drawable.bbg,
     R.drawable.bbh, R.drawable.bbi,R.drawable.bbj, R.drawable.bbk, R.drawable.bbl, R.drawable.bbm,
    R.drawable.bbn,R.drawable.bbo, R.drawable.bbp, R.drawable.bbq, R.drawable.bbr, R.drawable.bbs,
    R.drawable.bbt, R.drawable.bbu, R.drawable.bbv, R.drawable.bbw, R.drawable.bbx,R.drawable.bby,
    R.drawable.bbz, R.drawable.caa, R.drawable.cab, R.drawable.cac };
        private LayoutInflater myInflater;
        public EmotionAdapter(LayoutInflater myInflater) {
            this.myInflater = myInflater;
        }

        @Override
        public int getCount() {
            return myExpression.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = myInflater.inflate(R.layout.item_expression, null);
                viewHolder = new ViewHolder();
                viewHolder.imageview =  convertView.findViewById(R.id.imageview_expression);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.imageview.setImageResource(myExpression[position]);
            return convertView;
        }
        class ViewHolder{
            ImageView imageview;
        }
}

