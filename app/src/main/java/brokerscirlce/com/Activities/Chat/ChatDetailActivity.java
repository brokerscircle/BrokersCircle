package brokerscirlce.com.Activities.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;

import java.util.List;

import brokerscirlce.com.Activities.InComing_Call_Activity;
import brokerscirlce.com.adapters.ChatDetailRecyclerview_config;
import brokerscirlce.com.util.Helper;
import brokerscirlce.com.api_helpers.MessageDatabaseHelper;
import brokerscirlce.com.model.MessageUtils;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler recordWaitHandler, recordTimerHandler;
    private Runnable recordRunnable, recordTimerRunnable;
    private float displayWidth;
    private MediaRecorder mRecorder = null;

    private LinearLayout rootView, mBackBtn, mSendContainer;
    private TextView mName, mStatus;
    private ImageView mVideoCallBtn, mAudioCallBtn, mEmojiAttachment, mAddAttachmentBtn, mSendMessage;
    private EditText mNewMessageEditText;
    private TableLayout addAttachmentLayout;
    private CircleImageView mProfileImage;
    private RecyclerView mRecyclerView;

    private EmojiPopup emojIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_activity);

        // This line needs to be executed before any usage of EmojiTextView, EmojiEditText or EmojiButton.
        EmojiManager.install(new GoogleEmojiProvider());

        initUi();

        displayWidth = Helper.getDisplayWidth(this);

        new MessageDatabaseHelper().readMessageList(new MessageDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MessageUtils> messageUtils, List<String> keys) {
                new ChatDetailRecyclerview_config().setConfig(mRecyclerView, ChatDetailActivity.this, messageUtils, keys);
            }
        });

        emojIcon = EmojiPopup.Builder.fromRootView(rootView).setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
            @Override
            public void onEmojiPopupShown() {
                if (mAddAttachmentBtn.getVisibility() == View.VISIBLE) {
                    mAddAttachmentBtn.setVisibility(View.GONE);
                    mAddAttachmentBtn.animate().setDuration(400).rotationBy(-45).start();
                }
            }
        }).build(mNewMessageEditText);
    }

    private void initUi() {
        rootView = findViewById(R.id.rootView);
        mBackBtn = findViewById(R.id.btn_back);
        mName = findViewById(R.id.tv_name);
        mStatus = findViewById(R.id.tv_status);
        mAudioCallBtn = findViewById(R.id.btn_audio);
        mVideoCallBtn = findViewById(R.id.btn_video);
        mProfileImage = findViewById(R.id.profile_image);
        mRecyclerView = findViewById(R.id.recyclerview);
        mSendContainer = findViewById(R.id.sendContainer);
        mEmojiAttachment = findViewById(R.id.attachment_emoji);
        mNewMessageEditText = findViewById(R.id.new_message);
        addAttachmentLayout = findViewById(R.id.add_attachment_layout);
        mAddAttachmentBtn = findViewById(R.id.add_attachment);
        mSendMessage = findViewById(R.id.send);

        //OnClicks
        mBackBtn.setOnClickListener(this);
        mAudioCallBtn.setOnClickListener(this);
        mVideoCallBtn.setOnClickListener(this);
        mEmojiAttachment.setOnClickListener(this);
        mAddAttachmentBtn.setOnClickListener(this);
        mSendMessage.setOnClickListener(this);
        findViewById(R.id.attachment_property).setOnClickListener(this);
        findViewById(R.id.attachment_project).setOnClickListener(this);
        findViewById(R.id.attachment_broker).setOnClickListener(this);
        findViewById(R.id.attachment_video).setOnClickListener(this);
        findViewById(R.id.attachment_contact).setOnClickListener(this);
        findViewById(R.id.attachment_gallery).setOnClickListener(this);
        findViewById(R.id.attachment_audio).setOnClickListener(this);
        findViewById(R.id.attachment_location).setOnClickListener(this);
        findViewById(R.id.attachment_document).setOnClickListener(this);
        mNewMessageEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
                    addAttachmentLayout.setVisibility(View.GONE);
                    mAddAttachmentBtn.animate().setDuration(400).rotationBy(-45).start();
                }
                return false;
            }
        });

        mNewMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAddAttachmentBtn.setVisibility(View.VISIBLE);
                mSendMessage.setImageResource(R.drawable.ic_audio_icon_colored);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAddAttachmentBtn.setVisibility(View.GONE);
                mSendMessage.setImageResource(R.drawable.ic_send_black_24dp);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mNewMessageEditText.getText().toString().trim().equals("")){
                    mAddAttachmentBtn.setVisibility(View.VISIBLE);
                    mSendMessage.setImageResource(R.drawable.ic_audio_icon_colored);
                }else {
                    mAddAttachmentBtn.setVisibility(View.GONE);
                    mSendMessage.setImageResource(R.drawable.ic_send_black_24dp);
                }
            }
        });
        mSendMessage.setOnTouchListener(voiceMessageListener);

    }

    private View.OnTouchListener voiceMessageListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    if (mNewMessageEditText.getText().toString().trim().isEmpty()) {
                        if (recordWaitHandler == null)
                            recordWaitHandler = new Handler();
//                        recordRunnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                recordingStart();
//                            }
//                        };
                        Toast.makeText(ChatDetailActivity.this, "Recording Start", Toast.LENGTH_SHORT).show();
                        recordWaitHandler.postDelayed(recordRunnable, 600);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + displayWidth + ", " + x + ")");
                    if (mRecorder != null && mNewMessageEditText.getText().toString().trim().isEmpty()) {
                        if (Math.abs(event.getX()) / displayWidth > 0.35f) {
                            //recordingStop(false);
                            Toast.makeText(ChatDetailActivity.this, "Recording Canceled", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    if (recordWaitHandler != null && mNewMessageEditText.getText().toString().trim().isEmpty())
                        recordWaitHandler.removeCallbacks(recordRunnable);
                    if (mRecorder != null && mNewMessageEditText.getText().toString().trim().isEmpty()) {
                        //recordingStop(true);
                        Toast.makeText(ChatDetailActivity.this, "Recording Stop", Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
            return false;
        }
    };


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_audio:
                startActivity(new Intent(ChatDetailActivity.this, InComing_Call_Activity.class));
                break;
            case R.id.btn_video:
                startActivity(new Intent(ChatDetailActivity.this, Call_Screen_Activity.class));
                break;
            case R.id.attachment_emoji:
                emojIcon.toggle();
                if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
                    addAttachmentLayout.setVisibility(View.GONE);
                    mAddAttachmentBtn.animate().setDuration(400).rotationBy(-45).start();
                }
                break;
            case R.id.add_attachment:
                Helper.closeKeyboard(this, view);
                if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
                    addAttachmentLayout.setVisibility(View.GONE);
                    mAddAttachmentBtn.animate().setDuration(400).rotationBy(-45).start();
                } else {
                    addAttachmentLayout.setVisibility(View.VISIBLE);
                    mAddAttachmentBtn.animate().setDuration(400).rotationBy(45).start();
                    emojIcon.dismiss();
                }
                break;
            case R.id.send:
                if (!TextUtils.isEmpty(mNewMessageEditText.getText().toString().trim())) {
                    //sendMessage(mNewMessageEditText.getText().toString(), AttachmentTypes.NONE_TEXT, null);
                    mNewMessageEditText.setText("");
                }
                break;
        }
    }
}
