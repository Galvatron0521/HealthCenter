package com.shenkangyun.healthcenter.MainPage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenkangyun.healthcenter.BeanFolder.ChatRecord;
import com.shenkangyun.healthcenter.BeanFolder.MessageEvents;
import com.shenkangyun.healthcenter.IMFolder.ChatActivity;
import com.shenkangyun.healthcenter.IMFolder.JMessageActivity;
import com.shenkangyun.healthcenter.MainPage.Adapter.ChatRecordAdapter;
import com.shenkangyun.healthcenter.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ChatRecordFragment extends Fragment {
    @BindView(R.id.chatRecordRecycler)
    RecyclerView chatRecordRecycler;

    private String latestText;
    private ChatRecordAdapter chatRecordAdapter;
    private LinearLayoutManager layoutManager;
    private List<ChatRecord> chatRecords = new ArrayList<>();

    private String phone;
    private String message;
    private String nicknames;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_record, container, false);
        ButterKnife.bind(this, view);
        JMessageClient.registerEventReceiver(this);
        initView();
        initData();
        return view;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getContext());
        chatRecordAdapter = new ChatRecordAdapter();
        chatRecordRecycler.setLayoutManager(layoutManager);
        //分割线
        chatRecordRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        chatRecordRecycler.setAdapter(chatRecordAdapter);
    }


    private void initData() {
        Intent intent = getActivity().getIntent();
        phone = intent.getStringExtra("phone");
        message = intent.getStringExtra("message");
        nicknames = intent.getStringExtra("nickname");
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEventMainThread(MessageEvent event) {
        chatRecords.clear();
        ChatRecord chatRecord = new ChatRecord();
        Message msg = event.getMessage();
        final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
        String targetId = userInfo.getUserName();
        String nickname = userInfo.getNickname();
        Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
        latestText = conv.getLatestText();
        if (!TextUtils.isEmpty(nickname)){
            nicknames = nickname;
        }
        if (!TextUtils.isEmpty(targetId)){
            phone = targetId;
        }
        if (!TextUtils.isEmpty(latestText)){
            message = latestText;
        }
        int unReadMsgCnt = conv.getUnReadMsgCnt();
        chatRecord.setNickName(nicknames);
        chatRecord.setUserName(phone);
        chatRecord.setContent(message);
        chatRecord.setUnRemsgCount(String.valueOf(unReadMsgCnt));
        chatRecords.add(chatRecord);
        chatRecordAdapter.setNewData(chatRecords);
        EventBus.getDefault().post(new MessageEvents(message));
        initClick();
    }

    public void onEventMainThread(OfflineMessageEvent event) {
        Message latestMessage = event.getConversation().getLatestMessage();
        int size = event.getOfflineMessageList().size();
        TextContent offText = (TextContent) latestMessage.getContent();
        chatRecords.clear();
        ChatRecord chatRecord = new ChatRecord();
        UserInfo fromUser = event.getConversation().getLatestMessage().getFromUser();
        chatRecord.setNickName(fromUser.getNickname());
        chatRecord.setUserName(fromUser.getUserName());
        chatRecord.setContent(offText.getText().toString());
        chatRecord.setUnRemsgCount("1");
        chatRecords.add(chatRecord);
        chatRecordAdapter.setNewData(chatRecords);
        EventBus.getDefault().post(new MessageEvents(latestText));
        initClick();
    }

    private void initClick() {
        chatRecordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.content:
                        View childView = chatRecordRecycler.getLayoutManager().findViewByPosition(position);
                        TextView count = childView.findViewById(R.id.tv_count);
                        count.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("phone", chatRecords.get(position).getUserName());
                        intent.putExtra("message", chatRecords.get(position).getContent());
                        startActivity(intent);
                        break;
                    case R.id.right:
                        chatRecordAdapter.remove(position);
                        chatRecordAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
