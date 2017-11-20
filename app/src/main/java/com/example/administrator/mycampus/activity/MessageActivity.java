package com.example.administrator.mycampus.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.adapter.EmotionAdapter;
import com.example.administrator.mycampus.cache.MyCache;
import com.example.administrator.mycampus.info.MsgInfo;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.administrator.mycampus.util.MyUtils.myExpressionString;

public class MessageActivity extends AppCompatActivity {
    private ListView messageList;
    private EditText editMessage;
    private Button emotionButton;
    private Button sendButton;
    private Button backButton;
    private Html.ImageGetter myImageGetter;
    private Html.ImageGetter myReceiveImageGetter;
    private EmotionAdapter myEmotionAdapter;
    private GridView emotionGridView;
    private Spanned mySpanned;
    private String receiverId;
    private String receive_text;
    private MessageAdapter messageAdapter;
    private InputMethodManager myInputMethodManager;
    private TextView userId;
    private Spanned sendSpannedMessage;
    private Spanned receiveSpannedMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent=getIntent();
        receiverId=intent.getStringExtra("receiveId");
        findView();
        emotionButton.setOnClickListener(new ButtonListener());
        sendButton.setOnClickListener(new ButtonListener());           //绑定发送按钮监听器
        backButton.setOnClickListener(new ButtonListener());
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);  //注册消息接收观察者
        messageAdapter= new MessageAdapter(this);
        messageList.setAdapter(messageAdapter);
        //***********************************************

        //通过反射获得图片的id
        myImageGetter =  new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String s) {
                Drawable drawable = null;
                int id = R.drawable.aaa;
                if (s != null) {
                    Class clazz = R.drawable.class;
                    try {
                        Field field = clazz.getDeclaredField(s);
                        id = field.getInt(s);

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                drawable = getResources().getDrawable(id);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        myEmotionAdapter = new EmotionAdapter(getLayoutInflater());
        emotionGridView.setAdapter(myEmotionAdapter);
        //点击表情，将表情添加到输入框中。
        emotionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //通过mImageGetter获得id获得表情图片，然后将其添加到输入框中。
                mySpanned = Html.fromHtml("<img src='" + myExpressionString[position] + "'/>", myImageGetter, null);
                editMessage.getText().insert(editMessage.getSelectionStart(), mySpanned);

            }
        });
        //点击输入框收回表情框
        editMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emotionGridView.getVisibility() == View.VISIBLE) {
                    emotionGridView.setVisibility(View.GONE);
                }
            }
        });
    }


    //***********注销消息接受观察者****************
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, false);
    }

    //******************发送文本消息********************
    private void sendText() {
        String content = filterHtml(Html.toHtml(editMessage.getText()));                                  //获取输入框消息内容
        IMMessage message = MessageBuilder.createTextMessage(receiverId, SessionTypeEnum.P2P, content);  // 创建文本消息
        NIMClient.getService(MsgService.class).sendMessage(message, false);                             //发送消息代码
    }
    //******************创建消息接受观察者对象**********************
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    for (IMMessage message : messages) {
                        receive_text = message.getContent();                    //接受到的文本消息内容
                    }
                    SpannableString spannableString = new SpannableString(receive_text);
                    String regexEmotion = "(([abc]{2})[a-z]){0,}";
                    Pattern patternEmotion = Pattern.compile(regexEmotion);
                    Matcher matcherEmotion = patternEmotion.matcher(spannableString);
                    while (matcherEmotion.find()) {
                        // 获取匹配到的具体字符
                        myReceiveImageGetter = new Html.ImageGetter() {
                            @Override
                            public Drawable getDrawable(String source) {
                                Drawable drawable = null;
                                int id = R.drawable.aaa;
                                if (source != null) {
                                    Class clazz = R.drawable.class;
                                    try {
                                        Field field = clazz.getDeclaredField(source);
                                        id = field.getInt(source);

                                    } catch (NoSuchFieldException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                                drawable = getResources().getDrawable(id);
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                return drawable;
                            }
                        };
                    }
                    messageAdapter.addDataToAdapter(new MsgInfo(receive_text, null));          //将收到的消息发送到适配器中
                    messageAdapter.notifyDataSetChanged();
                    receive_text = null;
                    messageList.smoothScrollToPosition(messageList.getCount() - 1);
                }
            };
    public class MessageAdapter extends BaseAdapter {
        private Context context;
        private List<MsgInfo> datas = new ArrayList<>();
        private ViewHolder viewHolder;
        public void addDataToAdapter(MsgInfo msg_info) {                                  //将消息加入适配器中
            datas.add(msg_info);
        }
        public MessageAdapter(Context context) {                                   //适配器构造方法
            this.context = context;
        }
        @Override
        public int getCount() {
            return datas.size();
        }                           //获取当前消息长度
        @Override
        public Object getItem(int position) {
            return datas.get(position);                                                //获取当前消息在dates中的位置
        }
        @Override
        public long getItemId(int position) {
            return position;
        }                    //获取消息位置下标
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_message, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String send = datas.get(position).getRight_text();
            String receive = datas.get(position).getLeft_text();
            if (send != null) {                                              //如果左边消息为空则判断是右边人发的消息隐藏左边消息气泡
                sendSpannedMessage = Html.fromHtml(datas.get(position).getRight_text(), myImageGetter, null);
                viewHolder.text_right.setText(sendSpannedMessage);
                viewHolder.right.setVisibility(View.VISIBLE);                         //显示右边消息布局
                viewHolder.left.setVisibility(View.INVISIBLE);                        //隐藏左边消息布局
                if (receiverId.equals("654321")) {
                    viewHolder.right_sculpture.setImageResource(R.drawable.girl);
                    userId.setText("654321");
                } else {
                    viewHolder.right_sculpture.setImageResource(R.drawable.boy);
                    userId.setText("123456");
                }
            }
            if (receive != null) {             //如果右边消息为空则判断是左边人发的消息隐藏右边消息气泡
                receiveSpannedMessage = Html.fromHtml(receive, myReceiveImageGetter, null);
                viewHolder.text_left.setText(receiveSpannedMessage);
                viewHolder.left.setVisibility(View.VISIBLE);                           //显示左边消息布局
                viewHolder.right.setVisibility(View.INVISIBLE);                       //隐藏右边消息布局
                if (receiverId.equals("654321")) {
                    viewHolder.left_sculpture.setImageResource(R.drawable.boy);
                    userId.setText("123456");
                } else {
                    viewHolder.left_sculpture.setImageResource(R.drawable.girl);
                    userId.setText("654321");
                }

            }
            return convertView;
        }

        class ViewHolder {
            View rootView;
            TextView text_left;
            LinearLayout left;
            TextView text_right;
            LinearLayout right;
            ImageView left_sculpture;
            ImageView right_sculpture;

            ViewHolder(View rootView) {
                this.rootView = rootView;
                this.text_left =  rootView.findViewById(R.id.text_left);
                this.left =  rootView.findViewById(R.id.left);
                this.text_right =  rootView.findViewById(R.id.text_right);
                this.right =  rootView.findViewById(R.id.right);
                this.left_sculpture =  rootView.findViewById(R.id.left_sculpture);
                this.right_sculpture =  rootView.findViewById(R.id.right_sculpture);

            }

        }
    }


    //*******************判断消息发送者和接收者是谁*****************

    public class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.send_button:
                    sendText();                                                                   //将消息发送出去
                    String send_text = filterHtml(Html.toHtml(editMessage.getText()));               //获取发出的消息
                    editMessage.setText(null);                                               //清空消息输入框
                    messageAdapter.addDataToAdapter(new MsgInfo(null, send_text));                    //将发送的消息传入适配器中
                    messageAdapter.notifyDataSetChanged();
                    send_text = null;
                    messageList.smoothScrollToPosition(messageList.getCount() - 1);  //将listview定位到最后一行
                    break;
                case R.id.emoticon:
                    if (emotionGridView.getVisibility() == View.VISIBLE) {
                        emotionGridView.setVisibility(View.GONE);
                    } else {
                        emotionGridView.setVisibility(View.VISIBLE);
                        myInputMethodManager.hideSoftInputFromWindow(editMessage.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    break;
                case R.id.back_button:
                    finish();
                default:
                    break;
            }
        }
    }


    //**********创建各种控件的实例***********************
    private void findView() {
        messageList=(ListView)findViewById(R.id.listview);
        editMessage=(EditText)findViewById(R.id.edit_message);
        backButton=(Button)findViewById(R.id.back_button);
        sendButton=(Button)findViewById(R.id.send_button);
        emotionButton=(Button)findViewById(R.id.emoticon);
        userId=(TextView)findViewById(R.id.user_id);
        emotionGridView=(GridView)findViewById(R.id.emotion_gridView);
        myInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public String filterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        return str;
    }


}
